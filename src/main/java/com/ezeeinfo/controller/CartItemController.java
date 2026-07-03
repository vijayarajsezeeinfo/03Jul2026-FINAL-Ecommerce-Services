package com.ezeeinfo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.controller.io.CartIO;
import com.ezeeinfo.controller.io.CartItemIO;
import com.ezeeinfo.controller.io.NamespaceIO;
import com.ezeeinfo.controller.io.ProductIO;
import com.ezeeinfo.controller.io.UserIOResponse;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.CartDTO;
import com.ezeeinfo.dto.CartItemDTO;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.dto.ProductDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.service.CartItemService;
import com.ezeeinfo.util.TokenUtil;

@RestController
@RequestMapping("{authtoken}/cart-item")
public class CartItemController {

	@Autowired
	CartItemService cartItemService;
	@Autowired
	UserController userController;
	@Autowired
	UserDAO userDAO;
	@Autowired
	NamespaceController namespaceController;
	@Autowired
	ProductController productController;
	@Autowired
	TokenUtil tokenUtil;

	private static final Logger LOG = LoggerFactory.getLogger(CartItemController.class);

	@RequestMapping(value = "/{namespaceCode}", method = RequestMethod.GET)
	public List<CartItemIO> getAllCartItems(@PathVariable("authtoken") String authToken, @PathVariable("namespaceCode") String namespaceCode) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return cartItemService.getAllCartItems(authDTO, namespaceCode).stream().map(dto -> cartItemDTOToIO(dto)).toList();
	}

	@RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
	public CartItemIO getCartItemByCode(@PathVariable("authtoken") String authToken, @PathVariable String code) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return cartItemDTOToIO(cartItemService.getCartItemByCode(authDTO, code));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public CartItemIO update(@PathVariable("authtoken") String authToken, @RequestBody CartItemIO io) throws Exception {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return cartItemDTOToIO(cartItemService.update(authDTO, cartItemIOToDTO(io)));
	}

	public CartItemIO cartItemDTOToIO(CartItemDTO dto) {

		LOG.info("CartItem dto : {} ", dto);

		CartIO cartIO = cartDTOToIO(dto.getCart());
		ProductIO productIO = productController.productDTOToIO(dto.getProduct());
		NamespaceIO namespaceIO = namespaceController.namespaceDTOToIO(dto.getNamespace());

		CartItemIO cartItemIO = new CartItemIO();
		cartItemIO.setCode(dto.getCode());
		cartItemIO.setCart(cartIO);
		cartItemIO.setProduct(productIO);
		cartItemIO.setQuantity(dto.getQuantity());
		cartItemIO.setNamespace(namespaceIO);
		cartItemIO.setActiveFlag(dto.getActiveFlag());
		LOG.info("CartItem io : {} ", cartItemIO);

		return cartItemIO;
	}

	public CartItemDTO cartItemIOToDTO(CartItemIO io) throws Exception {
		LOG.info("CartItem io : {} ", io);

		CartDTO cartDTO = cartIOToDTO(io.getCart());
		ProductDTO productDTO = productController.productIOToDTO(io.getProduct());
		NamespaceDTO namespaceDTO = namespaceController.namespaceIOToDTO(io.getNamespace());

		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setCode(io.getCode());
		cartItemDTO.setCart(cartDTO);
		cartItemDTO.setProduct(productDTO);
		cartItemDTO.setQuantity(io.getQuantity());
		cartItemDTO.setNamespace(namespaceDTO);
		cartItemDTO.setActiveFlag(io.getActiveFlag());
		LOG.info("CartItem dto : {} ", cartItemDTO);

		return cartItemDTO;
	}

	public CartIO cartDTOToIO(CartDTO dto) {
		CartIO io = new CartIO();
		io.setCode(dto.getCode());
		io.setActiveFlag(dto.getActiveFlag());
		UserIOResponse userIO = userController.userDTOToIO(dto.getUser());
		io.setUser(userIO);
		NamespaceIO namespaceIO = namespaceController.namespaceDTOToIO(dto.getNamespace());
		io.setNamespace(namespaceIO);
		return io;
	}

	public CartDTO cartIOToDTO(CartIO io) throws Exception {
		CartDTO dto = new CartDTO();
		dto.setCode(io.getCode());
		dto.setActiveFlag(io.getActiveFlag());
		UserDTO userDTO = userDAO.getUserByCode(io.getUser().getCode());
		dto.setUser(userDTO);
		NamespaceDTO namespaceDTO = namespaceController.namespaceIOToDTO(io.getNamespace());
		dto.setNamespace(namespaceDTO);
		return dto;
	}

}