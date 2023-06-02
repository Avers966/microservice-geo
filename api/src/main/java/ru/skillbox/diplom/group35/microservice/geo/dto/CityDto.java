package ru.skillbox.diplom.group35.microservice.geo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Dto получения города")
public class CityDto extends BaseDto {

    @Schema(description = "Название населенного пункта")
    private String title;

    @Schema(description = "Идентификатор страны")
    private UUID countryId;

}
