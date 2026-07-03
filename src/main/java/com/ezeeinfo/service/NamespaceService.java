package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.NamespaceDTO;

public interface NamespaceService {

	List<NamespaceDTO> getAllNamespaces(AuthDTO authDTO);

	NamespaceDTO getNamespaceByCode(AuthDTO authDTO, String code);

	NamespaceDTO update(AuthDTO authDTO, NamespaceDTO namespaceDTO);

}
