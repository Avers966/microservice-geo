package ru.skillbox.diplom.group35.microservice.geo.dto;

import lombok.Data;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;

import java.util.List;
import java.util.UUID;
@Data
public class CityDto extends BaseDto {
    private String title;
    private UUID countryId;

}
