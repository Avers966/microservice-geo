package ru.skillbox.diplom.group35.microservice.geo.repository;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.geo.model.Country;

import java.util.List;

/**
 * CountryRepository
 *
 * @author Tretyakov Alexandr
 */

@Repository
public interface CountryRepository extends BaseRepository<Country> {
    List<Country> findCountriesByNameCountry(String nameCountry);

}
