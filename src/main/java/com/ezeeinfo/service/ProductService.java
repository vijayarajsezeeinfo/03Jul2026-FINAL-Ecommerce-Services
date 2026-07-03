package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.ProductDTO;

public interface ProductService {

	List<ProductDTO> getAllProducts(AuthDTO authDTO, String namespaceCode);

	ProductDTO getProductByCode(AuthDTO authDTO, String code);

	ProductDTO update(AuthDTO authDTO, ProductDTO productDTO);

	List<ProductDTO> getProductsByNamePriceAndNamespace(AuthDTO authDTO, String name, Double price, String namespaceCode);
}
