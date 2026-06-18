package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.ProductDTO;

public interface ProductService {

	List<ProductDTO> getAllProducts(String namespaceCode);

	ProductDTO getProductByCode(String code);

	ProductDTO update(ProductDTO productDTO);
}
