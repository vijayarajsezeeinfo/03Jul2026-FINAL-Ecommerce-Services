package com.ezeeinfo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.controller.io.BrandIO;
import com.ezeeinfo.controller.io.NamespaceIO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.BrandDTO;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.service.BrandService;
import com.ezeeinfo.util.TokenUtil;

@RestController
@RequestMapping("{authtoken}/brand")
public class BrandController {

	@Autowired
	BrandService brandService;
	@Autowired
	NamespaceController namespaceController;
	@Autowired
	TokenUtil tokenUtil;

	private static final Logger LOG = LoggerFactory.getLogger(BrandController.class);

	@RequestMapping(value = "/{namespaceCode}", method = RequestMethod.GET)
	public List<BrandIO> getAllBrands(@PathVariable("authtoken") String authToken, @PathVariable("namespaceCode") String namespaceCode) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		List<BrandIO> allBrands = brandService.getAllBrands(authDTO, namespaceCode).stream().map(dto -> brandDTOToIO(dto)).toList();
		LOG.info("Getting all Brands in Namespace {} : {}", namespaceCode, allBrands);
		return allBrands;
	}

	@RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
	public BrandIO getBrandByCode(@PathVariable("authtoken") String authToken, @PathVariable("code") String code) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		BrandIO brand = brandDTOToIO(brandService.getBrandByCode(authDTO, code));
		LOG.info("Getting address for Code {} : {}", code, brand);
		return brand;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public BrandIO update(@PathVariable("authtoken") String authToken, @RequestBody BrandIO brandIO) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		LOG.info("Input Brand for Save or Update : {}", brandIO);
		return brandDTOToIO(brandService.update(authDTO, brandIOToDTO(brandIO)));
	}

	public BrandIO brandDTOToIO(BrandDTO brandDTO) {
		LOG.info("Input for brand DTO to IO : {}", brandDTO);
		NamespaceIO namespaceIO = namespaceController.namespaceDTOToIO(brandDTO.getNamespace());
		BrandIO brandIO = new BrandIO();
		brandIO.setCode(brandDTO.getCode());
		brandIO.setName(brandDTO.getName());
		brandIO.setNamespace(namespaceIO);
		brandIO.setActiveFlag(brandDTO.getActiveFlag());
		LOG.info("Output for brand DTO to IO : {}", brandIO);
		return brandIO;
	}

	public BrandDTO brandIOToDTO(BrandIO brandIO) {
		LOG.info("Input for brand IO to DTO : {}", brandIO);
		NamespaceDTO namespaceDTO = namespaceController.namespaceIOToDTO(brandIO.getNamespace());
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setCode(brandIO.getCode());
		brandDTO.setName(brandIO.getName());
		brandDTO.setNamespace(namespaceDTO);
		brandDTO.setActiveFlag(brandIO.getActiveFlag());
		LOG.info("Output for brand IO to DTO : {}", brandDTO);
		return brandDTO;
	}
}
