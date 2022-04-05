package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetSoftware;

@Repository
public interface AssetSoftwareRepository extends JpaRepository<AssetSoftware, String> {

}
