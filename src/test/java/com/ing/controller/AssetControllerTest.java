package com.ing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ing.entity.Asset;
import com.ing.request.AssetRequest;
import com.ing.security.UserDetailsServiceImpl;
import com.ing.service.AssetService;
import com.ing.util.JwtTokenUtil;

@WebMvcTest(controllers = AssetController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AssetService assetService;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    private String createAssetRequestJson(String assetCode, String assetName, int size, int usableSize) {
        return """
            {
                "assetCode": "%s",
                "assetName": "%s",
                "size": %d,
                "usableSize": %d
            }
        """.formatted(assetCode, assetName, size, usableSize);
    }

    private Asset createAsset(Long id, String assetCode, String assetName, int size, int usableSize) {
        return Asset.builder()
            .id(id)
            .assetCode(assetCode)
            .assetName(assetName)
            .size(size)
            .usableSize(usableSize)
            .build();
    }

    @Test
    void testCreateAsset() throws Exception {
        // Given
        String assetRequestJson = createAssetRequestJson("AKBNK", "Akbank A.S", 5000, 5000);

        Asset asset = createAsset(1L, "AKBNK", "Akbank A.S", 5000, 5000);
        given(assetService.save(any(AssetRequest.class))).willReturn(asset);

        // When and  Then
        mockMvc.perform(post("/asset/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(assetRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.assetCode").value("AKBNK"))
                .andExpect(jsonPath("$.assetName").value("Akbank A.S"))
                .andExpect(jsonPath("$.size").value(5000))
                .andExpect(jsonPath("$.usableSize").value(5000));
    }

    @Test
    void testFindAssetByAssetCode() throws Exception {
        // Given
        String assetCode = "AKBNK";
        Asset asset = createAsset(1L, assetCode, "Akbank A.S", 5000, 5000);
        given(assetService.findAssetByAssetCode(assetCode)).willReturn(asset);

        // When and Then
        mockMvc.perform(get("/asset/{assetCode}", assetCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.assetCode").value(assetCode))
                .andExpect(jsonPath("$.assetName").value("Akbank A.S"))
                .andExpect(jsonPath("$.size").value(5000))
                .andExpect(jsonPath("$.usableSize").value(5000));
    }

    @Test
    void testGetAllAssets() throws Exception {
        // Given
        Asset akbank = createAsset(1L, "AKBNK", "Akbank A.S", 5000, 5000);
		Asset garanti = createAsset(2L, "GARAN", "Garanti Bank", 10000, 10000);
		List<Asset> assets = Arrays.asList(akbank, garanti );

        given(assetService.findAllAssets()).willReturn(assets);

        // When and Then
        mockMvc.perform(get("/asset/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].assetCode").value("AKBNK"))
                .andExpect(jsonPath("$[0].size").value(5000))
                .andExpect(jsonPath("$[0].usableSize").value(5000))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].assetCode").value("GARAN"))
                .andExpect(jsonPath("$[1].assetName").value("Garanti Bank"))
                .andExpect(jsonPath("$[1].size").value(10000))
                .andExpect(jsonPath("$[1].usableSize").value(10000));
    }
}
