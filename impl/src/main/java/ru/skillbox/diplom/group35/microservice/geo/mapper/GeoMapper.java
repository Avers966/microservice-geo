package ru.skillbox.diplom.group35.microservice.geo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group35.microservice.geo.dto.CityDto;
import ru.skillbox.diplom.group35.microservice.geo.dto.CountryDto;
import ru.skillbox.diplom.group35.microservice.geo.model.City;
import ru.skillbox.diplom.group35.microservice.geo.model.Country;

/**
 * GeoMapper
 *
 * @Author Tretyakov Alexandr
 */

@Mapper(componentModel = "spring")
public interface GeoMapper {

    @Mapping(target = "title", source = "nameCountry")
    CountryDto countryToCountryDto(Country country);

    @Mapping(target = "title", source = "nameCity")
    CityDto cityToCityDto(City city);
}


