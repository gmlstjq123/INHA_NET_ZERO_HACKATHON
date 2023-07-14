package com.example.hello_there.device.rice_cooker;

import com.example.hello_there.device.Device;
import com.example.hello_there.device.air_conditioner.AirConditioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RiceCookerRepository extends JpaRepository<RiceCooker, Long> {
    @Query("select r from RiceCooker r where r.modelName = :modelName")
    Optional<RiceCooker> findRiceCookerByModelName(@Param("modelName") String modelName);
}
