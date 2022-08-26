package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.TableImage;
import com.hanghae99.sulmocco.model.Tables;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableImageRepository extends JpaRepository<TableImage, Long> {

    Long deleteByTables(Tables tables);

    List<TableImage> findByTables(Tables findTable);

//    TableImage findByTablesAndIsThumbnailImage(Tables tables, boolean isRepImage);
}
