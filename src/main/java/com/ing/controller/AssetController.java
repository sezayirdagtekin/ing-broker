package com.ing.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.entity.Asset;
import com.ing.exception.AssetNotFounException;
import com.ing.request.AssetRequest;
import com.ing.service.AssetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/asset")
@Tag(name = "Asset Controller", description = "Controller for Asset operations")
public class AssetController {

	private AssetService service;
 
	public AssetController(AssetService service) {
		this.service = service;
	}

	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@Operation(summary ="Create  new Asset" ,description = "Creates a new Asset for the application")
	public ResponseEntity<Asset> create(@RequestBody AssetRequest request) {

		return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
	}

	@GetMapping("/{assetCode}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary ="Get asset " ,description = "Get Asset by asset code")
	public ResponseEntity<Asset> findAssetByAssetCode(@PathVariable String assetCode) throws AssetNotFounException {
		
		return new ResponseEntity<>(service.findAssetByAssetCode(assetCode), HttpStatus.OK);
	}
	
	@GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary ="Asset list" ,description = "Get all Assets")
	public ResponseEntity<List<Asset>> getAllAssets() {
		
		return  new ResponseEntity<>(service.findAllAssets(), HttpStatus.OK);
	}


}
