package com.ezeeinfo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezeeinfo.controller.ProductController;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.dto.OrderDTO;
import com.ezeeinfo.dto.OrderItemDTO;
import com.ezeeinfo.dto.OrderRequestDTO;
import com.ezeeinfo.dto.PaymentDTO;
import com.ezeeinfo.dto.ProductDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.dto.enumeration.OrderStatusEM;
import com.ezeeinfo.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class OrderDAO {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	ProductController productController;
	@Autowired
	UserDAO userDAO;
	@Autowired
	DataSource dataSource;
	@Autowired
	NamespaceDAO namespaceDAO;
	@Autowired
	ProductInventoryDAO productInventoryDAO;

	// public OrderRequestDTO getOrderByCode(String code) {
	// String sql="";
	// }
	
	
	
	
	//when we insert in order table, same time it will reflect in order_items and payments table

	public OrderRequestDTO update(OrderRequestDTO orderRequestDTO) {
		log.info("OrderRequest DTO : {}", orderRequestDTO);
		String namespaceCode = userDAO.getUserByCode(orderRequestDTO.getOrder().getUser().getCode()).getNamespace().getCode();
		NamespaceDTO namespaceDTO = namespaceDAO.getNamespaceByCode(namespaceCode);

		// if ordered product is exists or not================
		for (OrderItemDTO item : orderRequestDTO.getOrderItems()) {
			List<ProductDTO> availableProducts = productDAO.getAllProducts(namespaceCode);
			boolean exists = availableProducts.stream().anyMatch(product -> product.getCode().equals(item.getProduct().getCode()));
			if (!exists) {
				throw new ServiceException("Product Not Found");
			}
			
			ProductDTO productDTO =
		            productDAO.getProductByCode(
		                    item.getProduct().getCode());

		    if (productDTO == null) {
		        throw new ServiceException("Product Not Found");
		    }

		    Integer currentQty =
		            productInventoryDAO.getAvailableQuantityByProductId(
		                    productDTO.getId());

		    if (currentQty == null) {
		        throw new ServiceException(
		                "Inventory not found for Product : "
		                + productDTO.getCode());
		    }

		    if (currentQty < item.getQuantity()) {
		        throw new ServiceException(
		                "Less Stock. Available : " + currentQty);
		    }
		}

		double actualAmount = 0.0;

		for (OrderItemDTO item : orderRequestDTO.getOrderItems()) {
			double price = productDAO.getProductByCode(item.getProduct().getCode()).getPrice();
			actualAmount += price * item.getQuantity();
		}

		orderRequestDTO.getOrder().setTotalAmount(actualAmount);
		orderRequestDTO.getPayment().setTotalAmountToPay(actualAmount);

		// paying amount check=========================================
		if (actualAmount > orderRequestDTO.getPayment().getPaidAmount()) {
			throw new ServiceException("Insufficient Amount. Your Order worths " + actualAmount);
		}

		// balance
		if (actualAmount < orderRequestDTO.getPayment().getPaidAmount()) {
			orderRequestDTO.getPayment().setBalanceAmount(orderRequestDTO.getPayment().getPaidAmount() - actualAmount);
		}

		// insert in orders table====================================
		String sql = "{CALL EZEE_SP_ORDER_IUD( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )}";

		String orderCode = null;
		try (Connection connection = dataSource.getConnection(); CallableStatement statement = connection.prepareCall(sql);) {

			statement.setString(1, orderRequestDTO.getOrder().getCode());
			statement.setString(2, orderRequestDTO.getOrder().getUser().getCode());
			statement.setInt(3, orderRequestDTO.getOrder().getOrderStatus().getId());
			statement.setDouble(4, orderRequestDTO.getOrder().getTotalAmount());
			statement.setTimestamp(5, Timestamp.valueOf(orderRequestDTO.getOrder().getOrderDate()));
			statement.setString(6, orderRequestDTO.getOrder().getNamespace().getCode());
			statement.setInt(7, orderRequestDTO.getOrder().getActiveFlag());
			statement.setInt(8, orderRequestDTO.getOrder().getUpdatedBy());
			statement.setInt(9, 0);
			statement.registerOutParameter(1, Types.VARCHAR);
			statement.registerOutParameter(10, Types.INTEGER);
			statement.execute();
			orderCode = statement.getString(1);
		}
		catch (SQLException e) {
			log.info("SQLException while EZEE_SP_ORDER_IUD. {}", e);
		}

		String sql2 = "SELECT id, code, user_id, order_status, total_amount, order_date, namespace_id, active_flag, updated_by FROM orders WHERE code = ?";

		if (orderCode == null) {
			throw new ServiceException("Order code is not generated.");
		}

		OrderDTO orderDTO = null;

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql2);) {

			statement.setString(1, orderCode);

			try (ResultSet rs = statement.executeQuery();) {
				if (!rs.next()) {
					throw new ServiceException("EXCEPTION 404: Order is not Found");
				}

				UserDTO userDTO = userDAO.getUser(rs.getInt("user_id"));
				orderDTO = new OrderDTO();
				orderDTO.setId(rs.getInt("id"));
				orderDTO.setCode(rs.getString("code"));
				orderDTO.setUser(userDTO);
				orderDTO.setOrderStatus(OrderStatusEM.getOrderStatusEM(rs.getInt("order_status")));
				orderDTO.setTotalAmount(rs.getDouble("total_amount"));
				orderDTO.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
				orderDTO.setNamespace(namespaceDTO);
				orderDTO.setActiveFlag(rs.getInt("active_flag"));
				orderDTO.setUpdatedBy(rs.getInt("updated_by"));

			}
			catch (SQLException e) {
				log.info("SQLException while getting order. {}", e);
			}

		}
		catch (SQLException e) {
			log.info("SQLException while getting order. {}", e);
		}

		orderRequestDTO.getPayment().setOrder(orderDTO);
		PaymentDTO paymentDTO = orderRequestDTO.getPayment();
		paymentDTO.setNamespace(namespaceDTO);
		paymentDTO.setActiveFlag(orderRequestDTO.getOrder().getActiveFlag());
		log.info("orderDTO.getCode() : ", orderDTO.getCode());

		// ONCE ORDERED, we cannot modify/delete PAYMENTS AND ORDER ITEMS TABLE.
		// ONLY INSERT ALLOWED IN PAYMENTS AND ORDER ITEMS TABLE

		if (orderDTO.getActiveFlag() == 1 && orderDTO.getCode() != null) {

			// insert in payments table
			String sql3 = "{CALL EZEE_SP_PAYMENT_IUD( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )}";
			try (Connection connection = dataSource.getConnection(); CallableStatement statement = connection.prepareCall(sql3);) {
				statement.setString(1, paymentDTO.getCode());
				statement.setInt(2, paymentDTO.getOrder().getId());
				statement.setInt(3, paymentDTO.getPaymentMode().getId());
				statement.setDouble(4, paymentDTO.getTotalAmountToPay());
				statement.setDouble(5, paymentDTO.getPaidAmount());
				statement.setDouble(6, paymentDTO.getBalanceAmount());
				statement.setInt(7, paymentDTO.getBillingStatus().getId());
				statement.setString(8, paymentDTO.getTransactionId());
				statement.setString(9, paymentDTO.getRemarks());
				statement.setInt(10, paymentDTO.getNamespace().getId());
				statement.setInt(11, paymentDTO.getActiveFlag());
				statement.setInt(12, paymentDTO.getUpdatedBy());

				statement.registerOutParameter(1, Types.VARCHAR);
				statement.registerOutParameter(13, Types.INTEGER);

				statement.execute();
				log.info(" EZEE_SP_PAYMENT_IUD is executed.");
				paymentDTO.setCode(statement.getString(1));
			}
			catch (SQLException e) {
				log.info("SQLException while executing EZEE_SP_PAYMENT_IUD. {}", e);
			}

			// insert in order items table
			String sql4 = "{CALL EZEE_SP_ORDER_ITEMS_IUD( ?, ?, ?, ?, ?, ?, ?, ?, ? )}";
			try (Connection connection = dataSource.getConnection(); CallableStatement statement = connection.prepareCall(sql4);) {

				for (OrderItemDTO item : orderRequestDTO.getOrderItems()) {

					item.setOrder(orderDTO);

					ProductDTO productDTO = productDAO.getProductByCode(item.getProduct().getCode());

					item.setProduct(productDTO);

					item.setNamespace(namespaceDTO);

					item.setPrice(productDTO.getPrice());

					item.setActiveFlag(orderDTO.getActiveFlag());

					item.setUpdatedBy(orderDTO.getUpdatedBy());

					statement.setString(1, item.getCode());
					statement.setInt(2, item.getOrder().getId());
					statement.setInt(3, item.getProduct().getId());
					statement.setInt(4, item.getQuantity());
					statement.setDouble(5, item.getPrice());
					statement.setInt(6, item.getNamespace().getId());
					statement.setInt(7, item.getActiveFlag());
					statement.setInt(8, item.getUpdatedBy());

					statement.registerOutParameter(1, Types.VARCHAR);
					statement.registerOutParameter(9, Types.INTEGER);

					statement.execute();
					log.info(" EZEE_SP_ORDER_ITEMS_IUD is executed.");

					item.setCode(statement.getString(1));
					
					log.info("Product Code from Request : {}", item.getProduct().getCode());
					log.info("Product Id from Request   : {}", item.getProduct().getId());

					Integer currentQty = productInventoryDAO.getAvailableQuantityByProductId(item.getProduct().getId());

					if (currentQty == null) {
						throw new ServiceException("Inventory not found for product : " + item.getProduct().getCode());
					}

					int remainingQty = currentQty - item.getQuantity();

					String sql5 = "UPDATE product_inventory " + "SET available_quantity = ? " + "WHERE product_id = ?";

					try (Connection connection2 = dataSource.getConnection(); PreparedStatement statement2 = connection2.prepareStatement(sql5)) {

						statement2.setInt(1, remainingQty);
						statement2.setInt(2, item.getProduct().getId());

						statement2.executeUpdate();
					}

				}

			}
			catch (SQLException e) {
				log.info("SQLException while executing EZEE_SP_ORDER_ITEMS_IUD. {}", e);
			}

		}

		orderRequestDTO.setOrder(orderDTO);
		orderRequestDTO.setPayment(paymentDTO);
		return orderRequestDTO;
	}

}
