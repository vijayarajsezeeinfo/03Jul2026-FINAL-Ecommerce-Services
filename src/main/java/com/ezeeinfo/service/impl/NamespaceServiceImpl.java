package com.ezeeinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.NamespaceDAO;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.service.NamespaceService;
import com.ezeeinfo.util.SecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NamespaceServiceImpl implements NamespaceService {
	
	@Autowired
	NamespaceDAO namespaceDAO;

	@Override
	public List<NamespaceDTO> getAllNamespaces() {
		// TODO Auto-generated method stub
		return namespaceDAO.getAllNamespaces();
	}

	@Override
	public NamespaceDTO getNamespaceByCode(String code) {
		// TODO Auto-generated method stub
		return namespaceDAO.getNamespaceByCode(code);
	}

	@Override
	public NamespaceDTO update(NamespaceDTO namespaceDTO) {
		// TODO Auto-generated method stub
	    log.info("SecurityUtil User Id : {}", SecurityUtil.getUserId());
		namespaceDTO.setUpdatedBy(SecurityUtil.getUserId());
	    log.info("Namespace UpdatedBy : {}", namespaceDTO.getUpdatedBy());
		return namespaceDAO.update(namespaceDTO);
	}

}
