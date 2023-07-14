package com.example.hello_there.device.kimchi_refrigerator;

import com.example.hello_there.device.Device;
import com.example.hello_there.device.air_conditioner.AirConditioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KimchiRefrigeratorRepository extends JpaRepository<KimchiRefrigerator, Long> {
    @Query("select k from KimchiRefrigerator k where k.modelName = :modelName")
    Optional<KimchiRefrigerator> findKimchiRefrigeratorByModelName(@Param("modelName") String modelName);
}
