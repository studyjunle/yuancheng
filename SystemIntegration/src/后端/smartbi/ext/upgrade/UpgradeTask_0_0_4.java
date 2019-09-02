package smartbi.ext.upgrade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import smartbi.repository.UpgradeTask;
import smartbi.util.DBType;

public class UpgradeTask_0_0_4 extends UpgradeTask {
	private static final Logger LOG = Logger.getLogger(UpgradeTask_0_0_4.class);

	@Override
	public boolean doUpgrade(Connection conn, DBType dbType) {
		PreparedStatement pre = null;
		String sql = "insert into t_systemconfig (c_key, c_value) values (?,?)";
		try {
			pre = conn.prepareStatement(sql);
			pre.setString(1, "LOGIN_TIME_OUT");
			pre.setString(2, "30");
			pre.executeQuery();
			pre.close();
			pre = null;
			LOG.info("insert LOGIN_TIME_OUT == 30 success");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObject(null, pre);
		}
		
		return false;
	}

	@Override
	public String getNewVersion() {
		return "0.0.5";
	}

}
