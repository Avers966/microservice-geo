package ru.skillbox.diplom.group35.microservice.geo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.geo.config.Config;
import ru.skillbox.diplom.group35.microservice.geo.config.CountryConfig;
import ru.skillbox.diplom.group35.microservice.geo.dto.HeadHunterCityDto;
import ru.skillbox.diplom.group35.microservice.geo.dto.HeadHunterCountryDto;
import ru.skillbox.diplom.group35.microservice.geo.dto.HeadHunterRegionDto;
import ru.skillbox.diplom.group35.microservice.geo.feign.HeadHunterClient;
import ru.skillbox.diplom.group35.microservice.geo.model.City;
import ru.skillbox.diplom.group35.microservice.geo.model.Country;
import ru.skillbox.diplom.group35.microservice.geo.repository.CityRepository;
import ru.skillbox.diplom.group35.microservice.geo.repository.CountryRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ImportGeoData
 *
 * @Author Tretyakov Alexandr
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final Config config;
    private final HeadHunterClient hhClient;


    @Caching(evict = {
            @CacheEvict(value="country", allEntries=true),
            @CacheEvict(value="city", allEntries=true) })
    public String loadGeoData() {
        long start = System.currentTimeMillis();
        deleteExcessCountry();
        for (CountryConfig country : config.getCountries()) {
            log.info("Загрузка страны " + country.getCountryName());
            HeadHunterCountryDto countryDto = hhClient.getCountry(country.getId());
            List<String> cityListForCountry = getCityListFromObject(countryDto);
            log.info("Обнаружено всего городов " + cityListForCountry.size());
            UUID country_id = writeToTableCountry(country.getCountryName());
            List<String> cityListOutOfDataBase = createCityListOutOfDataBase(cityListForCountry, country_id);
            log.info("Найдено и записано в БД новых городов " + cityListOutOfDataBase.size());
            writeToTableCity(cityListOutOfDataBase, country_id);
        }
        long duration = System.currentTimeMillis() - start;
        String status = "Feign client - the data for the microservice-geo is loaded in " + duration + " ms";
        log.info(status);
        log.info("Обновлено всего стран " + config.getCountries().size());
        return status;
    }

    public void deleteExcessCountry() {
        List<Country> countriesDBList = countryRepository.findAll();
        List<String> countriesConfigList = config.getCountries().stream()
                                .map(CountryConfig::getCountryName)
                                .collect(Collectors.toList());
        List<Country> countriesDeleteList = countriesDBList.stream()
                .filter(c -> !countriesConfigList.contains(c.getNameCountry()))
                .collect(Collectors.toList());
        for (Country countryInstance : countriesDeleteList) {
            cityRepository.findByCountryId(countryInstance.getId()).forEach(cityRepository::hardDelete);
        }
        log.info("Удалено стран, не заданных в файле конфигурации " + countriesDeleteList.size());
        Iterator<Country> countryIterator =  countriesDeleteList.iterator();
        while (countryIterator.hasNext()) countryRepository.hardDelete(countryIterator.next());
    }

    public List<String> getCityListFromObject(HeadHunterCountryDto countryDto) {
        Set<String> cityList = new TreeSet<>();
        for (HeadHunterRegionDto regionDto : countryDto.getAreas()) {
            if (regionDto.getAreas().isEmpty()) cityList.add(regionDto.getName());
            for (HeadHunterCityDto cityDto : regionDto.getAreas()) {
                cityList.add(cityDto.getName());
            }
        }
//        Collections.sort(cityList);
        List<String> returnCityList = new ArrayList<>(cityList);
        return returnCityList;
    }

    public UUID writeToTableCountry(String nameCountry) {
        Country country = new Country();
        List<Country> countryList = countryRepository.findCountriesByNameCountry(nameCountry);
        if (countryList.isEmpty()) {
            country.setNameCountry(nameCountry);
            country.setIsDeleted(false);
            countryRepository.save(country);
            log.info(nameCountry + " добавлена в БД");
            return country.getId();
        }
        return countryList.get(0).getId();
    }

    List<String> createCityListOutOfDataBase(List<String> cityListForCountry, UUID country_id) {
        List<String> cityListFromDataBase = cityRepository.findByCountryId(country_id).stream()
                                            .map(c -> c.getNameCity())
                                            .collect(Collectors.toList());
        cityListForCountry = cityListForCountry.stream()
                                            .filter(c -> !cityListFromDataBase.contains(c))
                                            .collect(Collectors.toList());
        return cityListForCountry;
    }


    public void writeToTableCity(List<String> countryList, UUID country_id) {
        List<City> citiesInstancesList = new ArrayList<>();
        for (String cityName : countryList) {
            City city = new City();
            city.setNameCity(cityName);
            city.setIsDeleted(false);
            city.setCountryId(country_id);
            citiesInstancesList.add(city);
        }
        cityRepository.saveAll(citiesInstancesList);
    }
}

