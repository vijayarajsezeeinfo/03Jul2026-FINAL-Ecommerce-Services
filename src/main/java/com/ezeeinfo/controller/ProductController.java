package com.ezeeinfo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.controller.io.BrandIO;
import com.ezeeinfo.controller.io.CategoryIO;
import com.ezeeinfo.controller.io.NamespaceIO;
import com.ezeeinfo.controller.io.ProductIO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.BrandDTO;
import com.ezeeinfo.dto.CategoryDTO;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.dto.ProductDTO;
import com.ezeeinfo.service.ProductService;
import com.ezeeinfo.util.TokenUtil;

@RestController
@RequestMapping("{authtoken}/product")
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	NamespaceController namespaceController;
	@Autowired
	CategoryController categoryController;
	@Autowired
	BrandController brandController;
	@Autowired
	TokenUtil tokenUtil;

	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@RequestMapping(value = "/{namespaceCode}", method = RequestMethod.GET)
	public List<ProductIO> getAllProducts(@PathVariable("authtoken") String authToken, @PathVariable("namespaceCode") String namespaceCode) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return productService.getAllProducts(authDTO, namespaceCode).stream().map(dto -> productDTOToIO(dto)).toList();
	}

	@RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
	public ProductIO getProductById(@PathVariable("authtoken") String authToken, @PathVariable("code") String code) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return productDTOToIO(productService.getProductByCode(authDTO, code));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ProductIO update(@PathVariable("authtoken") String authToken, @RequestBody ProductIO productIO) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		LOG.info("Input Product for Save or Update : {}", productIO);
		return productDTOToIO(productService.update(authDTO, productIOToDTO(productIO)));
	}

	@GetMapping("/filter")
	public List<ProductIO> getProductsByNamePriceAndNamespace(@PathVariable("authtoken") String authToken, @RequestParam String name, @RequestParam Double price, @RequestParam String namespaceCode) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		LOG.info("Getting products by NAME, PRICE and NAMESPACE");
		List<ProductDTO> productDTOs = productService.getProductsByNamePriceAndNamespace(authDTO, name, price, namespaceCode);
		List<ProductIO> productIOs = productDTOs.stream().map(dto -> productDTOToIO(dto)).toList();
		return productIOs;
	}

	public ProductIO productDTOToIO(ProductDTO productDTO) {
		BrandIO brandIO = brandController.brandDTOToIO(productDTO.getBrand());
		CategoryIO categoryIO = categoryController.categoryDTOToIO(productDTO.getCategory());
		NamespaceIO namespaceIO = namespaceController.namespaceDTOToIO(productDTO.getNamespace());
		ProductIO productIO = new ProductIO();
		productIO.setCode(productDTO.getCode());
		productIO.setName(productDTO.getName());
		productIO.setDescription(productDTO.getDescription());
		productIO.setPrice(productDTO.getPrice());
		productIO.setBrand(brandIO);
		productIO.setCategory(categoryIO);
		productIO.setNamespace(namespaceIO);
		productIO.setActiveFlag(productDTO.getActiveFlag());
		return productIO;
	}

	public ProductDTO productIOToDTO(ProductIO productIO) {
		BrandDTO brandDTO = brandController.brandIOToDTO(productIO.getBrand());
		CategoryDTO categoryDTO = categoryController.categoryIOToDTO(productIO.getCategory());
		NamespaceDTO namespaceDTO = namespaceController.namespaceIOToDTO(productIO.getNamespace());
		ProductDTO productDTO = new ProductDTO();
		productDTO.setCode(productIO.getCode());
		productDTO.setName(productIO.getName());
		productDTO.setDescription(productIO.getDescription());
		productDTO.setPrice(productIO.getPrice());
		productDTO.setBrand(brandDTO);
		productDTO.setCategory(categoryDTO);
		productDTO.setNamespace(namespaceDTO);
		productDTO.setActiveFlag(productIO.getActiveFlag());
		return productDTO;
	}
}
