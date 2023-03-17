package ru.skillbox.diplom.group35.microservice.geo.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.microservice.geo.dto.CityDto;
import ru.skillbox.diplom.group35.microservice.geo.dto.CountryDto;

import java.util.List;
import java.util.UUID;

/**
 * GeoDto
 *
 * @author Tretyakov Alexandr
 */
@RequestMapping("api/v1/geo")
public interface GeoResource {

    @GetMapping("/country")
    ResponseEntity<List<CountryDto>> getCountry();

    @GetMapping("/country/{countryId}/city")
    ResponseEntity<List<CityDto>> getCityByCountryId(@PathVariable(name = "countryId") UUID countryId);

    @PutMapping("/load")
    ResponseEntity<String> geoLoad();




}
