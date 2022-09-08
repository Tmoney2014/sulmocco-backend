package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Dish;
import com.hanghae99.sulmocco.model.TableImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableImageRepository extends JpaRepository<TableImage, Long> {

    Long deleteByDish(Dish dish);

    List<TableImage> findByDish(Dish findTable);

}
