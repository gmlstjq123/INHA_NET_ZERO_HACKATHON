package com.example.hello_there.device.washing_machine;

import com.example.hello_there.device.Device;
import com.example.hello_there.device.air_conditioner.AirConditioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WashingMachineRepository extends JpaRepository<WashingMachine, Long> {
    @Query("select w from WashingMachine w where w.modelName = :modelName")
    Optional<WashingMachine> findWashingMachineByModelName(@Param("modelName") String modelName);
}
