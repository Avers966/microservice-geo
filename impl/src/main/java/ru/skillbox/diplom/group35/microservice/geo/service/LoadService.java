package ru.skillbox.diplom.group35.microservice.geo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
@Service
@RequiredArgsConstructor
public class LoadService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final Config config;
    private final HeadHunterClient hhClient;

    public ResponseEntity<String> loadGeoData() {
        long start = System.currentTimeMillis();
        for (CountryConfig country : config.getCountries()) {
            HeadHunterCountryDto countryDto = hhClient.getCountry(country.getId());
            List<String> cityListForCountry = getCityListFromObject(countryDto);
            UUID country_id = writeToTableCountry(country.getCountryName());
            List<String> CityListOutOfDataBase = createCityListOutOfDataBase(cityListForCountry, country_id);
            writeToTableCity(CityListOutOfDataBase, country_id);
        }
        long duration = System.currentTimeMillis() - start;
        String status = "Feign client - the data for the microservice-geo is loaded in " + duration + " ms";
        return ResponseEntity.ok(status);
    }

    public List<String> getCityListFromObject(HeadHunterCountryDto countryDto) {
        ArrayList<String> cityList = new ArrayList<>();
        for (HeadHunterRegionDto regionDto : countryDto.getAreas()) {
            if (regionDto.getAreas().isEmpty()) cityList.add(regionDto.getName());
            for (HeadHunterCityDto cityDto : regionDto.getAreas()) {
                cityList.add(cityDto.getName());
            }
        }
        Collections.sort(cityList);
        return cityList;
    }

    public UUID writeToTableCountry(String nameCountry) {
        Country country = new Country();
        List<Country> countryList = countryRepository.findCountriesByNameCountry(nameCountry);
        if (countryList.isEmpty()) {
            country.setNameCountry(nameCountry);
            country.setIsDeleted(false);
            countryRepository.save(country);
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

