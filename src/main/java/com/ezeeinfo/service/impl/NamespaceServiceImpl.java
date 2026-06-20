package com.ezeeinfo.service.impl;

import java.util.List;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
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
	private NamespaceDAO namespaceDAO;

	@Autowired
	private CacheManager cacheManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<NamespaceDTO> getAllNamespaces() {

		Cache<String, List> cache = cacheManager.getCache("namespaceListCache", String.class, List.class);
		List<NamespaceDTO> namespaces = (List<NamespaceDTO>) cache.get("ALL_NAMESPACES");
		if (namespaces != null)
			log.info("getAllNamespaces retrieved from cache");
		if (namespaces == null) {
			log.info("Hitting DB to getAllNamespaces");
			namespaces = namespaceDAO.getAllNamespaces();
			if (namespaces != null) {
				cache.put("ALL_NAMESPACES", namespaces);
			}
		}
		return namespaces;
	}

	@Override
	public NamespaceDTO getNamespaceByCode(String code) {

		Cache<String, NamespaceDTO> cache = cacheManager.getCache("namespaceCache", String.class, NamespaceDTO.class);
		NamespaceDTO namespace = cache.get(code);
		if (namespace != null)
			log.info("getNamespaceByCode for code {} retrieved from cache", code);
		if (namespace == null) {
			log.info("Hitting DB to getNamespaceByCode for code {}", code);
			namespace = namespaceDAO.getNamespaceByCode(code);
			if (namespace != null) {
				cache.put(code, namespace);
			}
		}
		return namespace;
	}

	@Override
	public NamespaceDTO update(NamespaceDTO namespaceDTO) {

		log.info("SecurityUtil User Id : {}", SecurityUtil.getUserId());
		namespaceDTO.setUpdatedBy(SecurityUtil.getUserId());
		log.info("Namespace UpdatedBy : {}", namespaceDTO.getUpdatedBy());
		NamespaceDTO updatedNamespace = namespaceDAO.update(namespaceDTO);
		if (updatedNamespace != null) {
			Cache<String, NamespaceDTO> namespaceCache = cacheManager.getCache("namespaceCache", String.class, NamespaceDTO.class);
			namespaceCache.put(updatedNamespace.getCode(), updatedNamespace);
			Cache<String, List> namespaceListCache = cacheManager.getCache("namespaceListCache", String.class, List.class);
			namespaceListCache.remove("ALL_NAMESPACES");
			log.info("Namespace cache updated and namespace list cache cleared");
		}
		return updatedNamespace;
	}
}