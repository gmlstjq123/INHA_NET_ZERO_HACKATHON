package com.example.hello_there.refrigerator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
    @Query("select r from Refrigerator r where r.modelName = :modelName")
    Optional<Refrigerator> findRefrigeratorByModelName(@Param("modelName") String modelName);

    @Query("select r from Refrigerator r where r.modelName like %:modelName%")
    List<Refrigerator> findRefrigeratorBySimilarModelName(@Param("modelName") String modelName);
}
