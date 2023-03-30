package ru.skillbox.diplom.group35.microservice.geo.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.microservice.geo.dto.CityDto;
import ru.skillbox.diplom.group35.microservice.geo.dto.CountryDto;
import ru.skillbox.diplom.group35.microservice.geo.service.GeoService;
import ru.skillbox.diplom.group35.microservice.geo.service.LoadService;

import java.util.List;
import java.util.UUID;

/**
 * GeoResourceImpl
 *
 * @author Tretyakov Alexandr
 */

@RestController
@RequiredArgsConstructor
public class GeoResourceImpl implements GeoResource{
    private final GeoService geoService;
    private final LoadService loadService;

    @Override
    public ResponseEntity<List<CountryDto>> getCountry() {
        return ResponseEntity.ok(geoService.getCountry());
    }

    @Override
    public ResponseEntity<List<CityDto>> getCityByCountryId(UUID countryId) {
        return ResponseEntity.ok(geoService.getCityByCountryId(countryId));
    }

    @Override
    public ResponseEntity<String> geoLoad() {
        return ResponseEntity.ok(loadService.loadGeoData());
    }
}
