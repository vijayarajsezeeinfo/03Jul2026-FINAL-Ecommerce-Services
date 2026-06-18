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

import com.ezeeinfo.dto.NamespaceDTO;
import com.ezeeinfo.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class NamespaceDAO {

	@Autowired
	DataSource dataSource;

	public List<NamespaceDTO> getAllNamespaces() {
		String sql = "SELECT id, code, name, active_flag, updated_by FROM namespace where active_flag < 2 ORDER BY id";

		List<NamespaceDTO> namespaces = new ArrayList<NamespaceDTO>();

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery();) {

			while (rs.next()) {
				NamespaceDTO namespaceDTO = new NamespaceDTO();
				namespaceDTO.setId(rs.getInt("id"));
				namespaceDTO.setCode(rs.getString("code"));
				namespaceDTO.setName(rs.getString("name"));
				namespaceDTO.setActiveFlag(rs.getInt("active_flag"));
				namespaceDTO.setUpdatedBy(rs.getInt("updated_by"));

				namespaces.add(namespaceDTO);
			}

		}
		catch (SQLException e) {
			log.info("SQLException in getAllNamespaces : {}", e);
		}
		return namespaces;
	}

	public NamespaceDTO getNamespaceByCode(String code) {
		String sql = "SELECT id, code, name, active_flag, updated_by FROM namespace where active_flag<2 AND code=?";
		NamespaceDTO namespaceDTO = null;

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, code);

			try (ResultSet rs = statement.executeQuery();) {
				if (!rs.next()) {
					throw new ServiceException("EXCEPTION 404: Namespace Not Found");
				}
				namespaceDTO = new NamespaceDTO();
				namespaceDTO.setId(rs.getInt("id"));
				namespaceDTO.setCode(rs.getString("code"));
				namespaceDTO.setName(rs.getString("name"));
				namespaceDTO.setActiveFlag(rs.getInt("active_flag"));
				namespaceDTO.setUpdatedBy(rs.getInt("updated_by"));
			}
		}
		catch (SQLException e) {
			log.info("SQLException in getNamespaceByCode : {}", e);
		}
		return namespaceDTO;
	}

	public NamespaceDTO update(NamespaceDTO namespaceDTO) {

		try (Connection connection = dataSource.getConnection(); CallableStatement stmt = connection.prepareCall("{CALL EZEE_SP_NAMESPACE_IUD(?, ?, ?, ?, ?, ?)}");) {
			stmt.setString(1, namespaceDTO.getCode());
			stmt.setString(2, namespaceDTO.getName());
			stmt.setInt(3, namespaceDTO.getActiveFlag());
			log.info("DAO UpdatedBy : {}", namespaceDTO.getUpdatedBy());
			stmt.setInt(4, namespaceDTO.getUpdatedBy());
			stmt.setInt(5, 0);

			stmt.registerOutParameter(1, Types.VARCHAR);
			stmt.registerOutParameter(6, Types.INTEGER);

			stmt.execute();
			log.info("EZEE_SP_NAMESPACE_IUD Successfully executed");
			namespaceDTO.setCode(stmt.getString(1));
		}
		catch (SQLException e) {
			log.info("SQLEXception while executing EZEE_SP_NAMESPACE_IUD");
		}
		return namespaceDTO;
	}

	public Integer getNamespaceId(String code) {
		String sql = "SELECT id FROM namespace WHERE code=?";
		Integer id = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, code);
			try (ResultSet rs = statement.executeQuery();) {
				if (!rs.next()) {
					throw new ServiceException("EXCEPTION 404: Namespace Not Found");
				}
				id = rs.getInt("id");
			}
			catch (SQLException e) {
				log.info("SQLException while getNamespaceId(namespaceCode). {}", e);
			}
		}
		catch (SQLException e) {
			log.info("SQLException while getNamespaceId(namespaceCode). {}", e);
		}
		return id;
	}

	public NamespaceDTO getNamespace(Integer id) {
		String sql = "SELECT id, code, name, active_flag, updated_by FROM namespace WHERE id=?";
		NamespaceDTO namespaceDTO = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			try (ResultSet rs = statement.executeQuery();) {
				if (!rs.next()) {
					throw new ServiceException("EXCEPTION 404: Namespace Not Found");
				}
				namespaceDTO = new NamespaceDTO();
				namespaceDTO.setId(rs.getInt("id"));
				namespaceDTO.setCode(rs.getString("code"));
				namespaceDTO.setName(rs.getString("name"));
				namespaceDTO.setActiveFlag(rs.getInt("active_flag"));
				namespaceDTO.setUpdatedBy(rs.getInt("updated_by"));
			}
			catch (SQLException e) {
				log.info("SQLException while getNamespace. {}", e);
			}
		}
		catch (SQLException e) {
			log.info("SQLException while getNamespace. {}", e);
		}
		return namespaceDTO;
	}
}
