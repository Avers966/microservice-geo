package ru.skillbox.diplom.group35.microservice.geo.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Geo service", description = "Выбор места жительства")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
@RequestMapping("api/v1/geo")
public interface GeoResource {

    @Operation(description = "Получение списка стран")
    @GetMapping("/country")
    ResponseEntity<List<CountryDto>> getCountry();

    @Operation(description = "Получение списка городов")
    @GetMapping("/country/{countryId}/city")
    ResponseEntity<List<CityDto>> getCityByCountryId(@PathVariable(name = "countryId") UUID countryId);

    @Operation(description = "Загрузка списка стран и городов с внешнего интернет-ресурса")
    @PutMapping("/load")
    ResponseEntity<String> geoLoad();




}
