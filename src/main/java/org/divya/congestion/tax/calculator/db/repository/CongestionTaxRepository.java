package org.divya.congestion.tax.calculator.db.repository;

import java.util.List;
import java.util.Optional;
import org.divya.congestion.tax.calculator.db.model.CongestionTaxModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongestionTaxRepository extends CrudRepository<CongestionTaxModel, Integer> {

  Optional<List<CongestionTaxModel>> findAllByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(String startTime, String endTime);

}
