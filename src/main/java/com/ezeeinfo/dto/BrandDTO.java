package com.ezeeinfo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BrandDTO extends BaseDTO<BrandDTO> {

	private NamespaceDTO namespace;
}
