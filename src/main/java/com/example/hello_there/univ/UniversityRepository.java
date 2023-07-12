package com.example.hello_there.univ;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {
    @Query("select u from University u where u.univName = :univName")
    Optional<University> findUnivByName(@Param("univName") String univName);

    @Query("select u.univName from University u where u.univName like %:univName%")
    List<String> findUnivBySimilarName(@Param("univName") String univName);

    @Query("select u from University u")
    List<University> findUnivs();
}
