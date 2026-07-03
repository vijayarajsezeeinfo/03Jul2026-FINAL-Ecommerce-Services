package com.ezeeinfo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryDTO extends BaseDTO<CategoryDTO> {

	private NamespaceDTO namespace;
}
