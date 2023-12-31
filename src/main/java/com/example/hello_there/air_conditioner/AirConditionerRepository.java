package com.example.hello_there.air_conditioner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AirConditionerRepository extends JpaRepository<AirConditioner, Long> {
    @Query("select a from AirConditioner a where a.modelName = :modelName")
    Optional<AirConditioner> findAirConditionerByModelName(@Param("modelName") String modelName);

    @Query("select a from AirConditioner a where a.modelName like %:modelName%")
    List<AirConditioner> findAirConditionerBySimilarModelName(@Param("modelName") String modelName);
}
