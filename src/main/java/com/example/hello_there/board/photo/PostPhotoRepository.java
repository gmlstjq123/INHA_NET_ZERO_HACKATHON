package com.example.hello_there.board.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostPhotoRepository  extends JpaRepository<PostPhoto, Long> {

    @Modifying
    @Query("delete from PostPhoto pp where pp.photoId in :ids")
    void deleteAllByBoard(@Param("ids") List<Long> ids);
}

