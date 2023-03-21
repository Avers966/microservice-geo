package ru.skillbox.diplom.group35.microservice.geo.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.skillbox.diplom.group35.microservice.geo.dto.HeadHunterCountryDto;
import ru.skillbox.diplom.group35.microservice.geo.dto.HeadHunterRegionDto;

import java.util.List;

/**
 * HeadHunterClient
 *
 * @Author Tretyakov Alexandr
 */

@FeignClient(value = "headhunter", url = "https://api.hh.ru/areas")
public interface HeadHunterClient {

        //@RequestLine("GET")
        @RequestMapping(method = RequestMethod.GET, value = "/")
        List<HeadHunterCountryDto> getCountryList();


        //@RequestLine("GET /{id}")
        @RequestMapping(method = RequestMethod.GET, value = "/{id}")
        HeadHunterCountryDto getCountry(@PathVariable("id") int id);

}
