package ru.skillbox.diplom.group35.microservice.geo.dto;

import lombok.Data;

import java.util.List;

/**
 * HeadHunterRegionDto
 *
 * @Author Tretyakov Alexandr
 */
@Data
public class HeadHunterRegionDto {
    private int id;
    private int parent_id;
    private String name;
    private List<HeadHunterCityDto> areas;
}
