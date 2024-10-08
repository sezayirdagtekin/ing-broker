package com.ing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ing.entity.Asset;
import com.ing.enums.Side;
import com.ing.exception.AssetNotFounException;
import com.ing.repository.AssetRepository;
import com.ing.request.AssetRequest;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class AssetServiceTest {

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetRepository assetRepository;

    @BeforeEach
    void setUp() {
        assetRepository.deleteAll();
    }

    private Asset createAndSaveAsset(String assetCode, String assetName, int size, int usableSize) {
        AssetRequest request = new AssetRequest(assetCode, assetName, size, usableSize);
        return assetService.save(request);
    }

    @Test
    void testSaveAsset() {
        // Given
        String assetCode = "YKB";
        String assetName = "Yapi Kredi";
        createAndSaveAsset(assetCode, assetName, 2000, 2000);

        // When
        Asset savedAsset = assetRepository.findByAssetCode(assetCode).orElse(null);

        // Then
        assertThat(savedAsset).isNotNull();
        assertThat(savedAsset.getAssetCode()).isEqualTo(assetCode);
    }

    @Test
    void testUpdateAssetWithInvalidAssetCodeThrowsAssetNotFoundException() {
        // Given
        String unknownAssetCode = "Invalid";

        // When & Then
        assertThrows(AssetNotFounException.class, () -> {
            assetService.updateAsset(unknownAssetCode, Side.BUY, 100);
        });
    }

    @Test
    void testInvalidAssetCodeShouldThrowAssetNotFoundException() {
        // Given
        String unknownAssetCode = "UNKNOWN";

        // When & Then
        assertThrows(AssetNotFounException.class, () -> {
            assetService.findAssetByAssetCode(unknownAssetCode);
        });
    }

    @Test
    void testFindAllAssets() {
        // Given
        createAndSaveAsset("ARCK", "Yapi Kredi", 1000, 1000);

        // When
        List<Asset> result = assetService.findAllAssets();

        // Then
        assertThat(result).hasSize(1);
    }

    @Test
    void testFindAssetByAssetCode() throws AssetNotFounException {
        // Given
        Asset savedAsset = createAndSaveAsset("EREGL", "Eregli Demis Celik", 1000, 1000);

        // When
        Asset foundAsset = assetService.findAssetByAssetCode(savedAsset.getAssetCode());

        // Then
        assertThat(foundAsset).isNotNull();
        assertThat(foundAsset.getAssetCode()).isEqualTo(savedAsset.getAssetCode());
    }
}
