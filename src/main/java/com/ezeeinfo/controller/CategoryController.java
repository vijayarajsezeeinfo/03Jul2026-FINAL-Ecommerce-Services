package com.ezeeinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.controller.io.CategoryIO;
import com.ezeeinfo.controller.io.NamespaceIO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.CategoryDTO;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.service.CategoryService;
import com.ezeeinfo.util.TokenUtil;

@RestController
@RequestMapping("{authtoken}/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	NamespaceController namespaceController;
	@Autowired
	TokenUtil tokenUtil;

	@RequestMapping(value = "/{namespaceCode}", method = RequestMethod.GET)
	public List<CategoryIO> getAllCategories(@PathVariable("authtoken") String authToken, @PathVariable("namespaceCode") String namespaceCode) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return categoryService.getAllCategories(authDTO, namespaceCode).stream().map(dto -> categoryDTOToIO(dto)).toList();
	}

	@RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
	public CategoryIO getCategoryByCode(@PathVariable("authtoken") String authToken, @PathVariable("code") String code) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return categoryDTOToIO(categoryService.getCategoryByCode(authDTO, code));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public CategoryIO update(@PathVariable("authtoken") String authToken, @RequestBody CategoryIO categoryIO) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return categoryDTOToIO(categoryService.update(authDTO, categoryIOToDTO(categoryIO)));
	}

	public CategoryIO categoryDTOToIO(CategoryDTO categoryDTO) {
		NamespaceIO namespaceIO = namespaceController.namespaceDTOToIO(categoryDTO.getNamespace());
		CategoryIO categoryIO = new CategoryIO();
		categoryIO.setCode(categoryDTO.getCode());
		categoryIO.setName(categoryDTO.getName());
		categoryIO.setNamespace(namespaceIO);
		categoryIO.setActiveFlag(categoryDTO.getActiveFlag());
		return categoryIO;
	}

	public CategoryDTO categoryIOToDTO(CategoryIO categoryIO) {
		NamespaceDTO namespaceDTO = namespaceController.namespaceIOToDTO(categoryIO.getNamespace());
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setCode(categoryIO.getCode());
		categoryDTO.setName(categoryIO.getName());
		categoryDTO.setNamespace(namespaceDTO);
		categoryDTO.setActiveFlag(categoryIO.getActiveFlag());
		return categoryDTO;
	}
}
