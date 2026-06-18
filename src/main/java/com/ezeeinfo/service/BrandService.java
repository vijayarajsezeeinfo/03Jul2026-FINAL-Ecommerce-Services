package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.BrandDTO;

public interface BrandService {

	List<BrandDTO> getAllBrands(String namespaceCode);

	BrandDTO getBrandByCode(String code);

	BrandDTO update(BrandDTO brandDTO);
}
