package com.ezeeinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.ProductDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.ProductDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.ProductService;
import com.ezeeinfo.util.SecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	UserDAO userDAO;

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
		log.info("product dto: {}", productDTO);
		productDTO.setUpdatedBy(SecurityUtil.getUserId());
		if (!productDTO.getNamespace().getCode().equals(userDAO.getUser(SecurityUtil.getUserId()).getNamespace().getCode())) {
			throw new ServiceException("Product's namespace and Users namespace does not macth");
		}
		return productDAO.update(productDTO);
	}

}
