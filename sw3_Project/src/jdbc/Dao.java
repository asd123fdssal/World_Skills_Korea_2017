package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.spi.DirStateFactory.Result;

public class Dao {
	private static Dao instance = new Dao();
	private PreparedStatement pstmt;
	private ResultSet rs;

	public static Dao getInstance() {
		return instance;
	}

	public Vector<Vector<Object>> getDoubleVector(ResultSet rs) {
		Vector<Vector<Object>> vv = new Vector<>();
		try {
			while (rs.next()) {
				Vector<Object> v = new Vector<>();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					v.add(rs.getString(i + 1));
				}
				vv.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vv;
	}

	public ResultSet getRs(String sql, Object... objects) {
		try {
			rs = getPstmt(sql, objects).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public PreparedStatement getPstmt(String sql, Object... objects) {
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			for (int i = 0; i < objects.length; i++) {
				pstmt.setObject(i + 1, objects[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(pstmt);
		return pstmt;
	}

	public void update(String sql, Object... objects) {
		try {
			getPstmt(sql, objects).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
