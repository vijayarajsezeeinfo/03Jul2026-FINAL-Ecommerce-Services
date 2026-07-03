package com.ezeeinfo.util;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.exception.ServiceException;

@Service
public class TokenUtil {

	private static final Logger LOG = LoggerFactory.getLogger(TokenUtil.class);

	@Autowired
	CacheManager cacheManager;

	public AuthDTO getAuthDTO(String token) {
		// VALIDATING IF IT IS EXPIRED OR NOT
		JwtUtil.getUserDTO(token);

		// GETTING AuthDTO from tokenCache
		Cache<String, AuthDTO> tokenCache = cacheManager.getCache("tokenCache", String.class, AuthDTO.class);

		AuthDTO authDTO = tokenCache.get(token);
		if (authDTO == null) {
			throw new ServiceException("Please Login First");
		}

		LOG.info("TOKEN VALIDATION DONE");
		LOG.info("TOKEN IS VALID", token);
		return authDTO;
	}

}
