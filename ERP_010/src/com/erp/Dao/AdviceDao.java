package com.erp.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.erp.utils.DBUtils;

public class AdviceDao {
	
	private static final String TAG="AdviceDao";
	private static final String TABLE_NAME="Advice";
	
	public static int getAdviceNum(Connection conn,String taskId,int type){
		int ans = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select count(*) from " + TABLE_NAME +" where task_id = ? and type = ?");
			stmt.setString(1, taskId);
			stmt.setInt(2, type);
			rs = stmt.executeQuery();
			if(rs.first()){
				ans = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.close(rs, stmt);
		}
		
		return ans;
	}
	
	public static boolean checkByTaskId(String taskId){
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			return checkByTaskId(taskId,conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.close(conn);
		}
		return false;
	}
	
	public static boolean checkByTaskId(String taskId,Connection conn) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("");
			stmt.setString(1, taskId);
			rs = stmt.executeQuery();
			return rs.first();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.close(rs, stmt);
		}
		return false;
	}
}