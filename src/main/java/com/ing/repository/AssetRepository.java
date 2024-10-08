package com.ing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

	Optional<Asset> findByAssetCode(String assetCode);

}
