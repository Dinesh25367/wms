package com.newage.wms.repository;

import com.newage.wms.entity.WareHouseContactDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseContactRepository extends JpaRepository<WareHouseContactDetail,Long> {

}
