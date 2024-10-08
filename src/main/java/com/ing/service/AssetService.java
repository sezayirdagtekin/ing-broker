package com.ing.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ing.entity.Asset;
import com.ing.enums.Side;
import com.ing.exception.AssetNotFounException;
import com.ing.repository.AssetRepository;
import com.ing.request.AssetRequest;

import jakarta.transaction.Transactional;

@Service
public class AssetService {

	static final Logger log = LoggerFactory.getLogger(AssetService.class);

	private final AssetRepository assetRepository;

	public AssetService(AssetRepository assetRepository) {
		this.assetRepository = assetRepository;
	}

	@Transactional
	public Asset save(AssetRequest request) {

		Asset asset = new Asset();
		asset.setAssetName(request.getAssetName());
		asset.setAssetCode(request.getAssetCode());
		asset.setSize(request.getSize());
		asset.setUsableSize(request.getUsableSize());

		return assetRepository.save(asset);
	}

	@Transactional
	public void updateAsset(String assetCode, Side side, int amount) throws AssetNotFounException {
		Asset asset = findAssetByAssetCode(assetCode);
		int usableSize = 0;

		if (side.equals(Side.BUY)) {
			usableSize = asset.getUsableSize() - amount;
		} else if (side.equals(Side.SELL)) {
			usableSize = asset.getUsableSize() + amount;
		}

		asset.setUsableSize(usableSize);
		assetRepository.save(asset);
		log.info("Asset table updated for {}  asset last usablesize: {}", assetCode, usableSize);
	}

	public List<Asset> findAllAssets() {

		return assetRepository.findAll();
	}

	public Asset findAssetByAssetCode(String assetCode) throws AssetNotFounException {
		return assetRepository.findByAssetCode(assetCode)
				.orElseThrow(() -> new AssetNotFounException("Asset not fount with asset code:" + assetCode));
	}

}
