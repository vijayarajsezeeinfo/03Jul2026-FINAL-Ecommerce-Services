package com.ezeeinfo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezeeinfo.dto.BrandDTO;
import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class BrandDAO {
	@Autowired
	DataSource dataSource;
	@Autowired
	NamespaceDAO namespaceDAO;

	public List<BrandDTO> getAllBrands(String namespaceCode) {
		String sql = "SELECT b.id AS brand_id, b.code AS brand_code, b.name AS brand_name, b.namespace_id AS brand_namespace_id, b.active_flag AS brand_active_flag, b.updated_by AS brand_updated_by, n.code AS namespace_code FROM brands b LEFT JOIN namespace n ON b.namespace_id = n.id WHERE b.active_flag < 2 AND n.code = ?";
		List<BrandDTO> brandDTOs = new ArrayList<BrandDTO>();
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, namespaceCode);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					NamespaceDTO namespaceDTO = namespaceDAO.getNamespace(rs.getInt("brand_namespace_id"));
					BrandDTO brandDTO = new BrandDTO();
					brandDTO.setId(rs.getInt("brand_id"));
					brandDTO.setCode(rs.getString("brand_code"));
					brandDTO.setName(rs.getString("brand_name"));
					brandDTO.setNamespace(namespaceDTO);
					brandDTO.setActiveFlag(rs.getInt("brand_active_flag"));
					brandDTO.setUpdatedBy(rs.getInt("brand_updated_by"));
					brandDTOs.add(brandDTO);
				}
			}
			catch (SQLException e) {
				log.info("SQLException while getAllBrands. {}", e);
			}
		}
		catch (SQLException e) {
			log.info("SQLException while getAllBrands. {}", e);
		}
		return brandDTOs;
	}

	public BrandDTO getBrandByCode(String code) {
		String sql = "SELECT b.id AS brand_id, b.code AS brand_code, b.name AS brand_name, b.namespace_id AS brand_namespace_id, b.active_flag AS brand_active_flag, b.updated_by AS brand_updated_by, n.code AS namespace_code FROM brands b LEFT JOIN namespace n ON b.namespace_id = n.id WHERE b.active_flag < 2 AND b.code = ?";
		BrandDTO brandDTO = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, code);
			try (ResultSet rs = statement.executeQuery();) {
				if (!rs.next()) {
					throw new ServiceException("Brand Not Found");
				}
				NamespaceDTO namespaceDTO = namespaceDAO.getNamespace(rs.getInt("brand_namespace_id"));
				brandDTO = new BrandDTO();
				brandDTO.setId(rs.getInt("brand_id"));
				brandDTO.setCode(rs.getString("brand_code"));
				brandDTO.setName(rs.getString("brand_name"));
				brandDTO.setNamespace(namespaceDTO);
				brandDTO.setActiveFlag(rs.getInt("brand_active_flag"));
				brandDTO.setUpdatedBy(rs.getInt("brand_updated_by"));
			}
			catch (SQLException e) {
				log.info("SQLException while getBrandByCode. {}", e);
			}
		}
		catch (SQLException e) {
			log.info("SQLException while getBrandByCode. {}", e);
		}
		return brandDTO;
	}

	public BrandDTO update(BrandDTO brandDTO) {
		String sql = "{CALL EZEE_SP_BRAND_IUD( ?, ?, ?, ?, ?, ?, ? )}";
		try (Connection connection = dataSource.getConnection(); CallableStatement statement = connection.prepareCall(sql);) {
			statement.setString(1, brandDTO.getCode());
			statement.setString(2, brandDTO.getName());
			statement.setString(3, brandDTO.getNamespace().getCode());
			statement.setInt(4, brandDTO.getActiveFlag());
			statement.setInt(5, brandDTO.getUpdatedBy());
			statement.setInt(6, 0);

			statement.registerOutParameter(1, Types.VARCHAR);
			statement.registerOutParameter(7, Types.INTEGER);

			statement.execute();
			log.info("EZEE_SP_BRAND_IUD successfully executed");

			brandDTO.setCode(statement.getString(1));
		}
		catch (SQLException e) {
			log.info("SQLException while executing EZEE_SP_BRAND_IUD. {}", e);
		}
		return brandDTO;
	}

	public BrandDTO getBrandById(Integer id) {
		String sql = "SELECT `id`, `code`, `name`, `namespace_id`, `active_flag`, `updated_by` FROM `brands` WHERE `id`= ? ";
		BrandDTO brandDTO = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			try (ResultSet rs = statement.executeQuery()) {
				if (!rs.next()) {
					throw new ServiceException("EXCEPTION 404: Brand Not Found");
				}
				NamespaceDTO namespaceDTO = namespaceDAO.getNamespace(rs.getInt("namespace_id"));
				brandDTO = new BrandDTO();
				brandDTO.setId(rs.getInt("id"));
				brandDTO.setCode(rs.getString("code"));
				brandDTO.setName(rs.getString("name"));
				brandDTO.setNamespace(namespaceDTO);
				brandDTO.setActiveFlag(rs.getInt("active_flag"));
				brandDTO.setUpdatedBy(rs.getInt("updated_by"));

			}
			catch (SQLException e) {
				log.info("SQLException while getBrandById. {}", e);
			}
		}
		catch (SQLException e) {
			log.info("SQLException while getBrandById. {}", e);
		}
		return brandDTO;
	}
}
