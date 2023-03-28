package ru.skillbox.diplom.group35.microservice.geo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.geo.model.City;

import java.util.List;
import java.util.UUID;

/**
 * CityRepository
 *
 * @author Tretyakov Alexandr
 */
@Repository
public interface CityRepository extends BaseRepository<City> {

    List<City> findByCountryId(UUID countryId);

    void deleteByCountryId(UUID countryId);
}
