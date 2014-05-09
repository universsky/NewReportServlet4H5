/**
 * ReportDao.java ct.dao Report ����9:32:16 2014��2��28�� 2014
 */
package universsky.ct.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import universsky.ct.report.CTPics;
import universsky.ct.report.CTCpuUsage;
import universsky.ct.result.QueryCpuUsage;
import universsky.ct.result.QueryPics;

/**
 * @author �����¹⽣ 2014��2��28�� ����9:32:16
 */
public class CpuUsageDao {
	private String run_stamp = (new CTCpuUsage()).getRun_stamp();
	List<HashMap<String, ArrayList<String>>> result = (new QueryCpuUsage())
			.query(run_stamp);

	// public static void main(String[] args) {
	// String run_stamp = "20140428052333";
	// QueryCpuUsage qr = new QueryCpuUsage();
	// List<HashMap<String, ArrayList<String>>> result = qr.query(run_stamp);
	// Gson gson = new Gson();
	// System.out.println(gson.toJson(result));// String json =
	// // gson.toJson(obj)
	// }

}
