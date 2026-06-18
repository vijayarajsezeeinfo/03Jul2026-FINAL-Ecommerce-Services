package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> getAllCategories(String namespaceCode);

	CategoryDTO getCategoryByCode(String code);

	CategoryDTO update(CategoryDTO categoryDTO);

}
