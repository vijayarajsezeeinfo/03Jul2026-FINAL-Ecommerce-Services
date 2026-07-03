package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.BrandDTO;

public interface BrandService {

	List<BrandDTO> getAllBrands(AuthDTO authDTO, String namespaceCode);

	BrandDTO getBrandByCode(AuthDTO authDTO, String code);

	BrandDTO update(AuthDTO authDTO, BrandDTO brandDTO);
}
