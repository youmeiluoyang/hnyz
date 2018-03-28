package com.dg11185.hnyz.dao;

import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLInsertStatementCreator implements PreparedStatementCreator {

	private String sql;
	private Object[] args;

	public MySQLInsertStatementCreator(String sql, Object... args) {
		this.sql = sql;
		this.args = args;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn)
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);

		PreparedStatementSetter setter = new ArgumentPreparedStatementSetter(
				args);
		setter.setValues(ps);

		return ps;
	}

}
