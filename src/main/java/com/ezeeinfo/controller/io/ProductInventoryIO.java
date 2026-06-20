package com.ezeeinfo.controller.io;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductInventoryIO extends BaseIO {

	private ProductIO product;
	private Integer availableQuantity;
	private NamespaceIO namespace;
}
