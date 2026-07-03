package com.ezeeinfo.controller.io;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryIO extends BaseIO<CategoryIO> {

	private NamespaceIO namespace;
}
