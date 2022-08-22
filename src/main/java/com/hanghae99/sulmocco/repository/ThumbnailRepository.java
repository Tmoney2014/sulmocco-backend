package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {


    Thumbnail findByTables(Tables tables);

    Long deleteByTables(Tables findTable);

}
