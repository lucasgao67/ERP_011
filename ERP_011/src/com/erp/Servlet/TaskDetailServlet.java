package com.erp.Servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.erp.Dao.DepartClassDao;
import com.erp.Dao.DepartDao;
import com.erp.Dao.TaskDao;
import com.erp.Entry.AdviceEntry;
import com.erp.Entry.ReportEntry;
import com.erp.Entry.TaskEntry;
import com.erp.utils.StringUtils;

@WebServlet("/TaskDetailServlet")
public class TaskDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TaskDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter("taskId");
		String departName = request.getParameter("departName");
		String departClassName = request.getParameter("departClassName");
		
		if(StringUtils.isSpace(departName)){
			String departId = TaskDao.getDepartIdByTaskId(taskId);
			departName = DepartDao.getDepartNameById(departId);
			departClassName = DepartClassDao.getDepartClassNameByDepartId(departId);
		}
		request.setAttribute("departName", departName);
		if(!StringUtils.isSpace(departClassName)){
			request.setAttribute("departClassName", "（"+departClassName +"）");
		}
		
		TaskEntry taskEntry = TaskDao.getTaskById(taskId);
		Map<Integer, ReportEntry> reports = taskEntry.getReports();
		Map<Integer, AdviceEntry> advices1 = taskEntry.getAdvices1();
		Map<Integer, AdviceEntry> advices2 = taskEntry.getAdvices2();
		
		request.setAttribute("task", taskEntry);
		request.setAttribute("reports", reports);
		request.setAttribute("advices1", advices1);
		request.setAttribute("advices2", advices2);
		
		request.getRequestDispatcher("/WEB-INF/jsp/taskDatils.jsp").forward(request, response);
	}

	/**
	 * 处理 督查以及点评信息
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
