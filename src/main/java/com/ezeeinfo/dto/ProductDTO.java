package com.ezeeinfo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDTO extends BaseDTO<ProductDTO> {

	private String description;
	private Double price;
	private BrandDTO brand;
	private CategoryDTO category;
	private NamespaceDTO namespace;
}
