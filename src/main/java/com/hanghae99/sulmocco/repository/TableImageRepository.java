package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.TableImage;
import com.hanghae99.sulmocco.model.Tables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableImageRepository extends JpaRepository<TableImage, Long> {

    Long deleteByTables(Tables tables);
}
