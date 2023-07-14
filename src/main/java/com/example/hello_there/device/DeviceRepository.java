package com.example.hello_there.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("select d from Device d where d.modelName = :modelName")
    Optional<Device> findDeviceByModelName(@Param("modelName") String modelName);

    @Query("SELECT AVG(d.annualCost / d.volume) FROM Device d WHERE d.grade = '1'")
    Double getAvgFirstAnnualCostDivByVol();

    @Query("SELECT AVG(d.emissionsPerHour / d.volume) FROM Device d WHERE d.grade = '1'")
    Double getAvgFirstemissionPerHourDivByVol();

    @Query("SELECT AVG(d.maxPowerConsumption / d.volume) FROM Device d WHERE d.grade = '1'")
    Double getAvgMaxPowerConsumptionDivByVol();
}
