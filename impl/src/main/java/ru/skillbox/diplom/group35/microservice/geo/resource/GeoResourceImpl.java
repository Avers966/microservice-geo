package ru.skillbox.diplom.group35.microservice.geo.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.microservice.geo.dto.CityDto;
import ru.skillbox.diplom.group35.microservice.geo.dto.CountryDto;
import ru.skillbox.diplom.group35.microservice.geo.service.GeoService;

import javax.annotation.security.PermitAll;
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

    @Override
    public ResponseEntity<List<CountryDto>> getCountry() {
        return geoService.getCountry();
    }

    @Override
    public ResponseEntity<List<CityDto>> getCityByCountryId(UUID countryId) {
        return geoService.getCityByCountryId(countryId);
    }

    @Override
    public void geoLoad(List<CountryDto> countryDto) {
        geoService.geoLoad(countryDto);
    }
}
