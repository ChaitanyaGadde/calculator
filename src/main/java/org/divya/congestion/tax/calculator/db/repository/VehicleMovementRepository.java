package org.divya.congestion.tax.calculator.db.repository;

import java.util.List;
import java.util.Optional;
import org.divya.congestion.tax.calculator.db.model.VehicleMovementModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleMovementRepository extends CrudRepository<VehicleMovementModel, Integer> {

  Optional<List<VehicleMovementModel>> findAllByRegistrationNumber(String regNum);

}
