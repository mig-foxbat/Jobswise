package org.foxbat.opswise.util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectionManager {
	private Connection conn;
	private static final String SYSID_FOR_TASK = "SELECT sys_id FROM ops_task where name = '%s'; ";
    private static final String SYSID_FOR_AGENT = "SELECT sys_id FROM ops_agent where name = '%s'; ";
    private static final String SYSID_FOR_CALENDAR = "SELECT sys_id FROM ops_calendar where name = '%s'; ";

    private JsonX ops_config;

	private void initializeConnection() {
		try {
			JsonX config = ops_config.getJSONObject(
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

    public DBConnectionManager(JsonX ops_config)
    {
        this.ops_config = ops_config;
        this.initializeConnection();
    }



    public String getAgentSysID(String agentname) {
        return this.executeQuery(String.format(SYSID_FOR_AGENT,agentname));
    }


	public String getTaskSysID(String name) {
	    return this.executeQuery(String.format(SYSID_FOR_TASK,name));
	}

    public String getCalendarSysID(String name) {
        return this.executeQuery(String.format(SYSID_FOR_CALENDAR,name));
    }



    public String executeQuery(String sql) {

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareCall(sql);
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
			this.conn.close();
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
