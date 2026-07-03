package com.ezeeinfo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductInventoryDTO extends BaseDTO<ProductInventoryDTO> {

	private ProductDTO product;
	private Integer availableQuantity;
	private NamespaceDTO namespace;

}
