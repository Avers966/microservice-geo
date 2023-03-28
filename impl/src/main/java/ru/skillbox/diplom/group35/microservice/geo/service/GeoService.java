package ru.skillbox.diplom.group35.microservice.geo.service;

import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
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
import java.util.concurrent.TimeUnit;

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

    @Caching(cacheable = {@Cacheable("country")},
            put = {@CachePut(value = "country")},
            evict = {@CacheEvict(value = "country")})
    public ResponseEntity<List<CountryDto>> getCountry() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDto> countryDtoList = new ArrayList<CountryDto>();
        for (Country country : countries) {
            List<String> citiesListDto = new ArrayList<>();
            CountryDto countryDto = mapper.countryToCountryDto(country);
            countryDto.setCities(citiesListDto);
            countryDtoList.add(countryDto);
        }
        return ResponseEntity.ok(countryDtoList);
    }


    @Caching(cacheable = {@Cacheable("city")},
            put = {@CachePut(value = "city")},
            evict = {@CacheEvict(value = "city")})
    public ResponseEntity<List<CityDto>> getCityByCountryId(UUID countryId) {
        List<City> cities = cityRepository.findByCountryId(countryId);
        List<CityDto> cityDtoList = new ArrayList<>();
        for (City city : cities) {
            CityDto cityDto = mapper.cityToCityDto(city);
            cityDtoList.add(cityDto);
        }
        return ResponseEntity.ok(cityDtoList);
    }
}
