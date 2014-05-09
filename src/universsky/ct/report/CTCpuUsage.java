/**
 * Report.java ct.report Report ����7:38:18 2014��2��28�� 2014
 */
package universsky.ct.report;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import universsky.ct.dao.CpuUsageDao;
import universsky.ct.dao.ReportDao;

import com.google.gson.Gson;

/**
 * @author �����¹⽣ 2014��2��28�� ����7:38:18
 */
public class CTCpuUsage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8142830838963500670L;

	/**
	 * serialVersionUID long
	 */

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		run_stamp = req.getParameter("run_stamp");
		callback = req.getParameter("callback");

		CpuUsageDao dao = new CpuUsageDao();
		Gson gson = new Gson();
		String json = gson.toJson(dao);
		// ��console�ϴ�ӡ��¼
		System.out.println(callback + "(" + json + ")");
		PrintWriter out = resp.getWriter();
		out.print(callback + "(" + json + ")");
		out.flush();
	}

	public String getRun_stamp() {
		return run_stamp;
	}

	public void setRun_stamp(String run_stamp) {
		CTCpuUsage.run_stamp = run_stamp;
	}

	static String run_stamp;
	static String callback;

}
