package org.foxbat.opswise.ducttape;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.foxbat.opswise.AppConfig;
import org.foxbat.opswise.util.JsonX;

public class DBConnectionManager {
	private static Connection conn;

	private static final String SQL = "SELECT sys_id FROM ops_task where name = '%s'; ";

	static {
		try {
			JsonX config = AppConfig.getInstance().config.getJSONObject(
					"server").getJSONObject("database");
			conn = DriverManager
					.getConnection(String.format(
							"jdbc:mysql://%s/%s?user=%s&password=%s",
							config.getString("host"),
							config.getString("database"),
							config.getString("username"),
							config.getString("password")));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String querySysID(String name) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareCall(String.format(SQL, name));
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next())
				return rs.getString(1);
			else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() {

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void finalize() {
		try {
			if (!conn.isClosed())
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
