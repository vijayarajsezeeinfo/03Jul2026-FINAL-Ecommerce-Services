package com.ezeeinfo.service.impl;

import java.util.List;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.NamespaceDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.UserService;
import com.ezeeinfo.util.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	NamespaceDAO namespaceDAO;

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	public List<UserDTO> getAllUsers(AuthDTO authDTO, String namespaceCode) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());

		// Checking if given namespace exists or not
		if (namespaceDAO.getNamespaceByCode(namespaceCode) == null) {
			LOG.info("Namespace {} not found to fetch all users", namespaceCode);
			throw new ServiceException("EXCEPTION 404: Namespace Not Found");
		}

		// ONLY SAMENAMESPACE USER CAN VIEW
		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(namespaceCode)) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW THE USERS IN {}", namespaceCode);
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW THE USERS IN " + namespaceCode);
		}

		if (loggedInUser.getRole().getId() != 1) {
			LOG.info("EXCEPTION 403: ONLY ADMIN CAN VIEW ALL USERS IN {}", namespaceCode);
			throw new ServiceException("EXCEPTION 403: ONLY ADMIN CAN VIEW ALL USERS IN " + namespaceCode);
		}

		Cache<String, List> cache = cacheManager.getCache("userListCache", String.class, List.class);
		List<UserDTO> users = (List<UserDTO>) cache.get(namespaceCode);
		if (users != null) {
			LOG.info("getAllUsers retrieved from cache");
		}
		if (users == null) {
			LOG.info("Hitting DB to getAllUsers");
			users = userDAO.getAllUsers(namespaceCode);
			if (users != null) {
				cache.put(namespaceCode, users);
			}
		}

		return users;
	}

	@Override
	public UserDTO getUserByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());

		// ONLY ADMIN CAN VIEW ANY ADDRESS.
		// NORMAL USER CAN VIEW ONLY THEIR OWN ADDRESS.
		if (!loggedInUser.getCode().equalsIgnoreCase(code)) {
			if (loggedInUser.getRole().getId() != 1) {
				LOG.info("EXCEPTION 403: ONLY ADMIN / RESPECTIVE USER CAN VIEW");
				throw new ServiceException("EXCEPTION 403: ONLY ADMIN / RESPECTIVE USER CAN VIEW");
			}
		}

		Cache<String, UserDTO> cache = cacheManager.getCache("userCache", String.class, UserDTO.class);
		UserDTO user = cache.get(code);
		if (user != null) {
			LOG.info("getUserByCode retrieved from cache");
		}
		if (user == null) {
			LOG.info("Hitting DB to getUserByCode");
			user = userDAO.getUserByCode(code);
			if (user != null) {
				cache.put(code, user);
			}
		}

		// ONLY SAMENAMESPACE USER CAN VIEW
		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(user.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW THE ADDRESS {}", code);
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW THE ADDRESS " + code);
		}
		return user;
	}

	@Override
	public UserDTO update(AuthDTO authDTO, UserDTO userDTO) {

		// HASHING PASSWORD
		userDTO.setPassword(PasswordUtil.hashPassword(userDTO.getPassword()));

		// SETTING LOGGED IN USER
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		userDTO.setUpdatedBy(loggedInUser);

		LOG.info("Input USER in USER SERVICE IMPL after Setting updated by : {}", userDTO);
		if (!userDTO.getCode().equalsIgnoreCase("null")) {
			UserDTO dbUser = userDAO.getUserByCodeInternal(userDTO.getCode());
			LOG.info("DB User {}", dbUser);

			// NOBODY CAN CHANGE NAMESPACE FOR EXISTING USER
			if (!dbUser.getNamespace().getCode().equalsIgnoreCase(userDTO.getNamespace().getCode())) {
				throw new ServiceException("EXCEPTION 400: CANNOT CHANGE NAMESPACE FOR EXISTING USER");
			}

			// ONLY ADMIN CAN CHANGE THE USER ROLE
			if (userDTO.getRole().getId() != dbUser.getRole().getId()) {
				if (userDTO.getUpdatedBy().getRole().getId() != 1) {
					throw new ServiceException("EXCEPTION 403: ONLY ADMIN CAN CHANGE THE ROLE");
				}
			}

			// ONLY ADMIN CAN UPDATE ANY USER.
			// NORMAL USER CAN UPDATE ONLY THEIR OWN PROFILE.
			if (loggedInUser.getRole().getId() != 1 && !loggedInUser.getCode().equalsIgnoreCase(dbUser.getCode())) {
				throw new ServiceException("EXCEPTION 403: YOU CAN UPDATE ONLY YOUR OWN PROFILE");
			}
		}

		// OTHER NAMESPACE USER CANNOT MAKE CHANGE
		if (!userDTO.getNamespace().getCode().equals(loggedInUser.getNamespace().getCode())) {
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN MAKE CHANGES");
		}

		// UPDATING
		UserDTO updatedUser = userDAO.update(userDTO);
		if (updatedUser != null) {

			// STORING IN userCache
			Cache<String, UserDTO> userCache = cacheManager.getCache("userCache", String.class, UserDTO.class);
			if (updatedUser.getActiveFlag() == 9 || updatedUser.getActiveFlag() < 2) {
				if (updatedUser.getActiveFlag() == 9) {
					updatedUser.setActiveFlag(1);
				}
				userCache.put(updatedUser.getCode(), updatedUser);
			}
			// if the user is deleted, removing from userCache
			if (updatedUser.getActiveFlag() == 2) {
				userCache.remove(updatedUser.getCode());
			}
			// REMOVING userListCache AFTER UPDATING USER
			Cache<String, List> userListCache = cacheManager.getCache("userListCache", String.class, List.class);
			userListCache.remove(updatedUser.getNamespace().getCode());
			LOG.info("User cache updated and user list cache cleared");
		}

		return updatedUser;
	}
}