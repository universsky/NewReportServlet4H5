/**
 * QueryResult.java ct.dao Report 下午10:14:57 2014年2月28日 2014
 */
package universsky.ct.result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * @author 东海陈光剑 2014年2月28日 下午10:14:57
 */
public class QueryCpuUsage {

	/**
	 * @param args
	 *            void main
	 */
	public static void main(String[] args) {
		String run_stamp = "20140428052333";
		QueryCpuUsage qr = new QueryCpuUsage();
		List<HashMap<String, ArrayList<String>>> result = qr.query(run_stamp);
		Gson gson = new Gson();
		System.out.println(gson.toJson(result));// String json =
												// gson.toJson(obj)
	}

	/**
	 * 
	 * @param run_stamp
	 * @return
	 */
	public List<HashMap<String, ArrayList<String>>> query(String run_stamp) {
		List<HashMap<String, ArrayList<String>>> result = new ArrayList<HashMap<String, ArrayList<String>>>();

		ArrayList<String> msgList = new ArrayList<String>();
		ArrayList<String> deviceList = new ArrayList<String>();
		ArrayList<String> cpuUsageList = new ArrayList<String>();
		HashMap<String, ArrayList<String>> cpuUsageMap = new HashMap<String, ArrayList<String>>();

		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			System.out.println("Success loading mysql driver!");
		} catch (Exception e) {
			System.out.print("Error loading mysql driver!");
			e.printStackTrace();
		}
		try {
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://10.125.1.58:3306/test", "root", "isword");
			// 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码
			System.out.println("Success connect mysql server!");

			Statement stmt = connect.createStatement();
			String queryCmd = "SELECT * FROM ct_perf where message like \"%"
					+ run_stamp + "%\"  ORDER BY pro_time";
			System.out.println(queryCmd);
			ResultSet rs = stmt.executeQuery(queryCmd);

			while (rs.next()) {
				String msg = rs.getString("message");
				// System.out.println(msg);
				if (msg.startsWith("cpu_usage")
						&& msg.endsWith("com.taobao.etao")) {
					msgList.add(msg);
					String[] msgSplit = msg.split("[$]");
					/**
					 * 0 | cpu_usage 1 | 20140428073427 2 | 096b3760 3 | 25252 0
					 * 24% S 36 895448K 65300K bg u0_a128 com.taobao.etao
					 */
					deviceList.add(msgSplit[2]);
					cpuUsageList.add(msgSplit[3]);
					String cpuUsage = msgSplit[3];

					String[] cpuUsageSplit = cpuUsage
							.split("[ \\s\\t\n\\x0B\\f\\r]");
					int ii = 0;

					for (String cs : cpuUsageSplit) {
						if (!cs.isEmpty()) {
							/**
							 * 0 | 25252 1 | 0 2 | 56% 3 | S 4 | 37 5 | 896488K
							 * 6 | 65320K 7 | bg 8 | u0_a128 9 | com.taobao.etao
							 */
							// System.out.println((ii++) + " | " + cs);
						}
					}
					// System.out.println(msgSplit[3]);
					// int col_index = 0;
					// for (String col : msgSplit) {
					// System.out.println((col_index++) + " | " + col);
					// }
				}
			}
			cpuUsageMap.put(run_stamp, msgList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// //////////////////////////////////////////////////////////////

		for (String deviceId : deviceList) {
			// System.out.println(deviceId);
		}

		for (String msg : msgList) {
			// System.out.println(msg);
		}
		for (String e : cpuUsageList) {
			// System.out.println(e);
		}

		List<String> deviceIdCpuList = new ArrayList<String>();
		for (int i = 0; i < deviceList.size(); i++) {
			deviceIdCpuList.add(i + " " + deviceList.get(i) + " "
					+ cpuUsageList.get(i));
		}
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

		for (int i = 0; i < deviceIdCpuList.size(); i++) {
			// System.out.println(deviceIdCpuList.get(i));
			String element = "";
			String[] item = deviceIdCpuList.get(i).split("[\\s]");
			int c = 0;
			for (String e : item) {
				if (!e.isEmpty()) {
					// System.out.println((c++) + " | " + e);
					element += e + "$";
				}
			}
			// 规范化后的deviceIdCpuList
			deviceIdCpuList.set(i, element);
		}
		// /////////////////////////
		// for (int i = 0; i < deviceIdCpuList.size(); i++) {
		// // System.out.println(deviceIdCpuList.get(i));
		// }

		Set<String> deviceSet = new HashSet<String>();

		for (String e : deviceList) {
			deviceSet.add(e);
		}

		for (String e : deviceSet) {
			System.out.println(e);
		}

		List<String[]> dollorList = new ArrayList<String[]>();
		for (String e : deviceIdCpuList) {
			String[] ss = e.split("[$]");
			// printStringArray(ss);
			dollorList.add(ss);
		}
		// ////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////
		for (String deviceId : deviceSet) {

			ArrayList<String> cpu_perctgList = new ArrayList<String>();
			ArrayList<String> cpu_vssList = new ArrayList<String>();
			ArrayList<String> cpu_rssList = new ArrayList<String>();
			ArrayList<String> cpu_thrList = new ArrayList<String>();

			for (String[] sa : dollorList) {

				// for (String e : sa)
				// System.out.println(e);

				for (int i = 0; i < sa.length; i++) {
					System.out.print(sa[i] + " ");
					if (deviceId.equals(sa[1])) {
						String cpu_thr = sa[3];
						cpu_thrList.add(cpu_thr);

						String cpu_per = sa[4].replace("%", "");
						cpu_perctgList.add(cpu_per);

						String vss = sa[7].replace("K", "");
						cpu_vssList.add(vss);

						String rss = sa[8].replace("K", "");
						cpu_rssList.add(rss);

					}
				}
				System.out.println();
			}
			//
			// for (String e : cpu_perctgList) {
			// System.out.println(deviceId + " | " + e);
			// }

			ArrayList<String> d = new ArrayList<String>(1);
			d.add(deviceId);
			HashMap<String, ArrayList<String>> DCMap = new HashMap<String, ArrayList<String>>();
			DCMap.put("device_id", d);
			DCMap.put("cpu_perctg", cpu_perctgList);
			DCMap.put("cpu_vss", cpu_vssList);
			DCMap.put("cpu_rss", cpu_rssList);
			DCMap.put("cpu_thr", cpu_thrList);

			result.add(DCMap);

			System.out.println(DCMap.get("device_id"));
			System.out.println(DCMap.get("cpu_perctg"));
			System.out.println(DCMap.get("cpu_vss"));
			System.out.println(DCMap.get("cpu_rss"));
			System.out.println(DCMap.get("cpu_thr"));

		}
		return result;
	}
}
