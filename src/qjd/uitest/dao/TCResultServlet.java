/**
 * 
 */
package qjd.uitest.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * @author chenguangjian
 * 
 */
public class TCResultServlet extends HttpServlet {

	private static final long serialVersionUID = -1503312618542301232L;
	private String timestamp;
	private String callback;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		timestamp = req.getParameter("timestamp");
		callback = req.getParameter("callback");

		System.out.println("timestamp=" + timestamp);
		System.out.println("callback=" + callback);
		List<TCResult> tcResult = new TCResultDao()
				.getTCResultByTimeStamp(timestamp);

		Gson gson = new Gson();
		String json = gson.toJson(tcResult);
		System.out.println(callback + "(" + json + ")");

		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(callback + "(" + json + ")");
		out.flush();
	}

}
