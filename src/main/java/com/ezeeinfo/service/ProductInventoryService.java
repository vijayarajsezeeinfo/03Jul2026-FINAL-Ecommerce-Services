package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.ProductInventoryDTO;

public interface ProductInventoryService {

	List<ProductInventoryDTO> getAllProductInventories(AuthDTO authDTO, String namespaceCode);

	ProductInventoryDTO getProductInventoryByCode(AuthDTO authDTO, String code);

	ProductInventoryDTO update(AuthDTO authDTO, ProductInventoryDTO productInventoryDTO);

}
