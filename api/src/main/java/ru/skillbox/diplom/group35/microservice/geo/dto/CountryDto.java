package ru.skillbox.diplom.group35.microservice.geo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;

import java.util.List;

/**
 * GeoDto
 *
 * @author Tretyakov Alexandr
 */

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CountryDto extends BaseDto {

    private  String title;
    private List<String> cities;



}
