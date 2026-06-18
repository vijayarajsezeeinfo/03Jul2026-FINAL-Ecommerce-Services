package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.NamespaceDTO;

public interface NamespaceService {
	
	List<NamespaceDTO> getAllNamespaces();
	
	NamespaceDTO getNamespaceByCode(String code);
	
	NamespaceDTO update(NamespaceDTO namespaceDTO);

}
