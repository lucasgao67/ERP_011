package com.erp.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.erp.Entry.DepartEntry;
import com.erp.Entry.TaskEntry;
import com.erp.utils.DBUtils;


public class TaskDao {

	private static final String TAG="TaskDao";
	private static final String TABLE_NAME="Task";
	
	public static List<TaskEntry> getAllTaskByDepartId() {
		List<TaskEntry> entries = new ArrayList<>();
		return entries;
	}
	
	public static void getAllTaskByDepartId(String departId,List<TaskEntry> taskEntries) {
		
	}
	
	public static void getAllTaskByDepartId(Connection conn,DepartEntry departEntry,boolean deep) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select * from " + TABLE_NAME + " where department_id = ?" );
			stmt.setString(1, departEntry.getDepartId());
			rs = stmt.executeQuery();
			while(rs.next()){
				TaskEntry entry = fill(rs);
				entry.setDepart(departEntry);
				departEntry.getTasks().add(entry);
			}
			if(deep){
				for(TaskEntry entry:departEntry.getTasks()){
					entry.setAdvise1Num(AdviceDao.getAdviceNum(conn, entry.getTaskId(), 0)); //�ල��
					entry.setAdvise2Num(AdviceDao.getAdviceNum(conn, entry.getTaskId(), 1)); //������
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.close(rs, stmt);
		}
	}
	
	public static TaskEntry fill(ResultSet rs) throws SQLException {
		TaskEntry entry = new TaskEntry();
		entry.setTaskId(rs.getString("task_id"));
		entry.setTaskName(rs.getString("task_name"));
		entry.setStartTime(rs.getString("startTime"));
		entry.setEndTime(rs.getString("endTime"));
		entry.setChairMan(rs.getString("chairMan"));
		entry.setType(rs.getString("type"));
		entry.setPlace(rs.getString("place"));
		entry.setFinancing(rs.getString("financing"));
		entry.setGoal(rs.getString("goal"));
		entry.setReportType(rs.getString("report_type"));
		return entry;
	}
	
}