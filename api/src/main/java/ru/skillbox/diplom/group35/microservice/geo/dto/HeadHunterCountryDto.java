package ru.skillbox.diplom.group35.microservice.geo.dto;

import lombok.Data;

import java.util.List;

/**
 * HeadHunterCountryDto
 *
 * @Author Tretyakov Alexandr
 */
@Data
public class HeadHunterCountryDto {
    private int id;
    private int parent_id;
    private String name;
    private List<HeadHunterRegionDto> areas;
}
