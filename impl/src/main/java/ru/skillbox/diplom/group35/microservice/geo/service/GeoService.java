package ru.skillbox.diplom.group35.microservice.geo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.geo.dto.CityDto;
import ru.skillbox.diplom.group35.microservice.geo.dto.CountryDto;
import ru.skillbox.diplom.group35.microservice.geo.mapper.GeoMapper;
import ru.skillbox.diplom.group35.microservice.geo.model.City;
import ru.skillbox.diplom.group35.microservice.geo.model.Country;
import ru.skillbox.diplom.group35.microservice.geo.repository.CityRepository;
import ru.skillbox.diplom.group35.microservice.geo.repository.CountryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * GeoService
 *
 * @author Tretyakov Alexandr
 */

@Service
@RequiredArgsConstructor
public class GeoService {
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final GeoMapper mapper;


    @Cacheable("country")
    public List<CountryDto> getCountry() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDto> countryDtoList = new ArrayList<CountryDto>();
        for (Country country : countries) {
            List<String> citiesListDto = new ArrayList<>();
            CountryDto countryDto = mapper.countryToCountryDto(country);
            countryDto.setCities(citiesListDto);
            countryDtoList.add(countryDto);
        }
        return countryDtoList;
    }


    @Cacheable("city")
    public List<CityDto> getCityByCountryId(UUID countryId) {
        List<City> cities = cityRepository.findByCountryId(countryId);
        List<CityDto> cityDtoList = new ArrayList<>();
        for (City city : cities) {
            CityDto cityDto = mapper.cityToCityDto(city);
            cityDtoList.add(cityDto);
        }
        return cityDtoList;
    }
}
