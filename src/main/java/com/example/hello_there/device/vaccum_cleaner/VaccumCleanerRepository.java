package com.example.hello_there.device.vaccum_cleaner;

import com.example.hello_there.device.Device;
import com.example.hello_there.device.air_conditioner.AirConditioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VaccumCleanerRepository extends JpaRepository<VaccumCleaner, Long> {
    @Query("select v from VaccumCleaner v where v.modelName = :modelName")
    Optional<VaccumCleaner> findVaccumCleanerByModelName(@Param("modelName") String modelName);
}
