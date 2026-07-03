package com.ezeeinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.controller.io.NamespaceIO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.service.NamespaceService;
import com.ezeeinfo.util.TokenUtil;

@RestController
@RequestMapping("{authtoken}/namespace")
public class NamespaceController {

	@Autowired
	NamespaceService namespaceService;
	@Autowired
	TokenUtil tokenUtil;

	@RequestMapping(method = RequestMethod.GET)
	public List<NamespaceIO> getAllNamespaces(@PathVariable("authtoken") String authToken) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return namespaceService.getAllNamespaces(authDTO).stream().map(dto -> namespaceDTOToIO(dto)).toList();
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public NamespaceIO getNamespaceByCode(@PathVariable("authtoken") String authToken, @PathVariable("code") String code) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return namespaceDTOToIO(namespaceService.getNamespaceByCode(authDTO, code));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public NamespaceIO update(@PathVariable("authtoken") String authToken, @RequestBody NamespaceIO namespaceIO) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		NamespaceDTO namespaceDTO = namespaceService.update(authDTO, namespaceIOToDTO(namespaceIO));
		return namespaceDTOToIO(namespaceDTO);
	}

	public NamespaceIO namespaceDTOToIO(NamespaceDTO namespaceDTO) {
		NamespaceIO namespaceIO = new NamespaceIO();
		namespaceIO.setCode(namespaceDTO.getCode());
		namespaceIO.setName(namespaceDTO.getName());
		namespaceIO.setActiveFlag(namespaceDTO.getActiveFlag());

		return namespaceIO;
	}

	public NamespaceDTO namespaceIOToDTO(NamespaceIO namespaceIO) {
		NamespaceDTO namespaceDTO = new NamespaceDTO();
		namespaceDTO.setCode(namespaceIO.getCode());
		namespaceDTO.setName(namespaceIO.getName());
		namespaceDTO.setActiveFlag(namespaceIO.getActiveFlag());

		return namespaceDTO;
	}
}
