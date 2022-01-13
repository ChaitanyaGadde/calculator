package org.divya.congestion.tax.calculator.db.repository;

import java.util.Optional;
import org.divya.congestion.tax.calculator.db.model.CityModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<CityModel, Integer> {

  Optional<CityModel> findCityModelByCityCode(String cityCode);

}
