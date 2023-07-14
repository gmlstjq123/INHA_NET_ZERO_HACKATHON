package com.example.hello_there.device.refrigerator;

import com.example.hello_there.device.Device;
import com.example.hello_there.device.air_conditioner.AirConditioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
    @Query("select r from Refrigerator r where r.modelName = :modelName")
    Optional<Refrigerator> findRefrigeratorByModelName(@Param("modelName") String modelName);
}
