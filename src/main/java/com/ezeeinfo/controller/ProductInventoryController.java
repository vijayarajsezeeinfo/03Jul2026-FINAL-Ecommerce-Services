package com.ezeeinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.controller.io.NamespaceIO;
import com.ezeeinfo.controller.io.ProductIO;
import com.ezeeinfo.controller.io.ProductInventoryIO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.dto.ProductDTO;
import com.ezeeinfo.dto.ProductInventoryDTO;
import com.ezeeinfo.service.ProductInventoryService;
import com.ezeeinfo.util.TokenUtil;

@RestController
@RequestMapping("{authtoken}/pi")
public class ProductInventoryController {

	@Autowired
	ProductInventoryService productInventoryService;
	@Autowired
	NamespaceController namespaceController;
	@Autowired
	ProductController productController;
	@Autowired
	TokenUtil tokenUtil;

	@RequestMapping(value = "/{namespaceCode}", method = RequestMethod.GET)
	public List<ProductInventoryIO> getAllProductInventories(@PathVariable("authtoken") String authToken, @PathVariable("namespaceCode") String namespaceCode) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return productInventoryService.getAllProductInventories(authDTO, namespaceCode).stream().map(dto -> piDTOToIO(dto)).toList();
	}

	@RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
	public ProductInventoryIO getProductInventoryByCode(@PathVariable("authtoken") String authToken, @PathVariable("code") String code) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return piDTOToIO(productInventoryService.getProductInventoryByCode(authDTO, code));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ProductInventoryIO update(@PathVariable("authtoken") String authToken, @RequestBody ProductInventoryIO productInventoryIO) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return piDTOToIO(productInventoryService.update(authDTO, piIOToDTO(productInventoryIO)));
	}

	public ProductInventoryIO piDTOToIO(ProductInventoryDTO productInventoryDTO) {
		NamespaceIO namespaceIO = namespaceController.namespaceDTOToIO(productInventoryDTO.getNamespace());
		ProductIO productIO = productController.productDTOToIO(productInventoryDTO.getProduct());
		ProductInventoryIO productInventoryIO = new ProductInventoryIO();
		productInventoryIO.setCode(productInventoryDTO.getCode());
		productInventoryIO.setAvailableQuantity(productInventoryDTO.getAvailableQuantity());
		productInventoryIO.setProduct(productIO);
		productInventoryIO.setNamespace(namespaceIO);
		productInventoryIO.setActiveFlag(productInventoryDTO.getActiveFlag());
		return productInventoryIO;
	}

	public ProductInventoryDTO piIOToDTO(ProductInventoryIO productInventoryIO) {
		NamespaceDTO namespaceDTO = namespaceController.namespaceIOToDTO(productInventoryIO.getNamespace());
		ProductDTO productDTO = productController.productIOToDTO(productInventoryIO.getProduct());
		ProductInventoryDTO productInventoryDTO = new ProductInventoryDTO();
		productInventoryDTO.setCode(productInventoryIO.getCode());
		productInventoryDTO.setAvailableQuantity(productInventoryIO.getAvailableQuantity());
		productInventoryDTO.setProduct(productDTO);
		productInventoryDTO.setNamespace(namespaceDTO);
		productInventoryDTO.setActiveFlag(productInventoryIO.getActiveFlag());
		return productInventoryDTO;
	}

}
