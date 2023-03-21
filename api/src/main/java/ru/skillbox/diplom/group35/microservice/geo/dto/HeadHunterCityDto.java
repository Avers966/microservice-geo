package ru.skillbox.diplom.group35.microservice.geo.dto;

import lombok.Data;

import java.util.List;

/**
 * HeadHunterCityDto
 *
 * @Author Tretyakov Alexandr
 */
@Data
public class HeadHunterCityDto {
    private int id;
    private int parent_id;
    private String name;
    private List<String> areas;
}
