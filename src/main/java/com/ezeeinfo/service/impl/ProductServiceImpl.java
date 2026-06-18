package com.ezeeinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.ProductDAO;
import com.ezeeinfo.dto.ProductDTO;
import com.ezeeinfo.service.ProductService;
import com.ezeeinfo.util.SecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDAO productDAO;

	@Override
	public List<ProductDTO> getAllProducts(String namespaceCode) {
		// TODO Auto-generated method stub
		return productDAO.getAllProducts(namespaceCode);
	}

	@Override
	public ProductDTO getProductByCode(String code) {
		// TODO Auto-generated method stub
		return productDAO.getProductByCode(code);
	}

	@Override
	public ProductDTO update(ProductDTO productDTO) {
		
		// TODO Auto-generated method stub
		log.info("product dto: {}",productDTO);
		productDTO.setUpdatedBy(SecurityUtil.getUserId());
		return productDAO.update(productDTO);
	}

}
