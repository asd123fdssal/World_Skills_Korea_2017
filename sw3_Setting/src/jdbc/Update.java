package jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

	private PreparedStatement pstmt;

	public Update(String sql) {
		System.out.println(sql);
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
