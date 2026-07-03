package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.CartItemDTO;

public interface CartItemService {

	List<CartItemDTO> getAllCartItems(AuthDTO authDTO, String namespaceCode);

	CartItemDTO getCartItemByCode(AuthDTO authDTO, String code);

	CartItemDTO update(AuthDTO authDTO, CartItemDTO cartItemDTO);

}