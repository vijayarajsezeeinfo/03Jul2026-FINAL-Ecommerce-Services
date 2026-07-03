package com.ezeeinfo.controller.io;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BrandIO extends BaseIO<BrandIO> {

	private NamespaceIO namespace;
}
