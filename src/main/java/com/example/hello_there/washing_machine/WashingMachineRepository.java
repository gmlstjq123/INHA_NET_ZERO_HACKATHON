package com.example.hello_there.washing_machine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WashingMachineRepository extends JpaRepository<WashingMachine, Long> {
    @Query("select w from WashingMachine w where w.modelName = :modelName")
    Optional<WashingMachine> findWashingMachineByModelName(@Param("modelName") String modelName);

    @Query("select w from WashingMachine w where w.modelName like %:modelName%")
    List<WashingMachine> findWashingMachineBySimilarModelName(@Param("modelName") String modelName);
}
