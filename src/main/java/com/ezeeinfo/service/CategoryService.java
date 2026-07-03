package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> getAllCategories(AuthDTO authDTO, String namespaceCode);

	CategoryDTO getCategoryByCode(AuthDTO authDTO, String code);

	CategoryDTO update(AuthDTO authDTO, CategoryDTO categoryDTO);

}
