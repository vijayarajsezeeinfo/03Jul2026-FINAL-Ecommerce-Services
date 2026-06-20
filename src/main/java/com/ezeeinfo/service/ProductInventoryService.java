package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.ProductInventoryDTO;

public interface ProductInventoryService {

	List<ProductInventoryDTO> getAllProductInventories(String namespaceCode);

	ProductInventoryDTO getProductInventoryByCode(String code);

	ProductInventoryDTO update(ProductInventoryDTO productInventoryDTO);

}
