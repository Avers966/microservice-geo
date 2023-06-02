package ru.skillbox.diplom.group35.microservice.geo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dto получения страны")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CountryDto extends BaseDto {

    @Schema(description = "Название страны")
    private  String title;

    @Schema(description = "Список городов в данной стране")
    private List<String> cities;



}
