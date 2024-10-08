package com.ing.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ing.entity.Asset;

import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
public class AssetRepositoryTest {

    private static final String INVALID_ASSET_CODE = "INVALID_CODE";
    
    @Autowired
    private AssetRepository assetRepository;

    private Asset saveAsset(String assetCode, String assetName, int size, int usableSize) {
        Asset asset = Asset.builder()
                .assetCode(assetCode)
                .assetName(assetName)
                .size(size)
                .usableSize(usableSize)
                .build();
        return assetRepository.save(asset);
    }

    @Test
    void testFindByAssetCodeShouldReturnAsset() {
        // Given: 
        saveAsset("ARCLK", "ARCELIK AS", 10000, 10000);

        // When:
        Optional<Asset> asset = assetRepository.findByAssetCode("ARCLK");

        // Then: 
        assertThat(asset).isPresent();
        assertThat(asset.get().getAssetCode()).isEqualTo("ARCLK");
        assertThat(asset.get().getAssetName()).isEqualTo("ARCELIK AS");
    }

    @Test
    void testFindByAssetCodeAssetNotFoundReturnEmpty() {
        // When: 
        Optional<Asset> asset = assetRepository.findByAssetCode(INVALID_ASSET_CODE);

        // Then: 
        assertThat(asset).isEmpty();
    }

    @Test
    void testSaveAsset() {
        // Given
        Asset asset = saveAsset("MGROS", "MGROS AS", 10000, 10000);

        // Then: 
        assertThat(asset.getId()).isNotNull();
        assertThat(asset.getAssetCode()).isEqualTo("MGROS");
        assertThat(asset.getAssetName()).isEqualTo("MGROS AS");
    }
    
    @AfterEach
    void cleanUp() {
        assetRepository.deleteAll(); 
    }
}
