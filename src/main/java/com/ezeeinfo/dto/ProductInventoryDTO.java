package com.ezeeinfo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductInventoryDTO extends BaseDTO {

	private ProductDTO product;
	private Integer availableQuantity;
	private NamespaceDTO namespace;

}
