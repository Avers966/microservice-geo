package ru.skillbox.diplom.group35.microservice.geo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.skillbox.diplom.group35.microservice.geo.config.Config;
import ru.skillbox.diplom.group35.microservice.geo.config.CountryConfig;
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

    public ResponseEntity<String> loadGeoData() {
        RestTemplate restTemplate = new RestTemplate();
        String headHunterUrl = "https://api.hh.ru/areas";
        ResponseEntity<String> response = restTemplate.getForEntity(headHunterUrl, String.class);
        for (CountryConfig country : config.getCountries()) {
            List<String> cityListForCountry = getCityListFromJson(response.getBody(),country.getCountryName());
            UUID country_id = writeToTableCountry(country.getCountryName());
            List<String> CityListOutOfDataBase = createCityListOutOfDataBase(cityListForCountry, country_id);
            writeToTableCity(CityListOutOfDataBase, country_id);
        }
        String status = "Данные для microservice-geo загружены";
        return ResponseEntity.ok(status);
    }

    public List<String> getCityListFromJson(String jsonString, String countryName) {
        ArrayList<String> cityList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(jsonString);
        }
        catch (JsonProcessingException ex) {ex.printStackTrace();}
        Iterator<JsonNode> countryIterator = rootNode.elements();
        JsonNode countryNode = null;
        while (countryIterator.hasNext()) {
            countryNode = countryIterator.next();
            if (countryNode.get("name").asText().equals(countryName)) break;
        }
        JsonNode regionsListNode = countryNode.get("areas");
        Iterator<JsonNode> regionsIterator = regionsListNode.elements();
        while (regionsIterator.hasNext()) {
            JsonNode regionNode = regionsIterator.next();
            if (regionNode.get("areas").isEmpty()) cityList.add(regionNode.get("name").asText());
            JsonNode citiesListNode = regionNode.get("areas");
            Iterator<JsonNode>  citiesIterator = citiesListNode.elements();
            while (citiesIterator.hasNext()) {
                JsonNode cityNode = citiesIterator.next();
                cityList.add(cityNode.get("name").asText().replaceAll("[Ё]", "Е"));
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

