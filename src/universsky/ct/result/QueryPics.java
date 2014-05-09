/**
 * QueryResult.java ct.dao Report ����10:14:57 2014��2��28�� 2014
 */
package universsky.ct.result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author �����¹⽣ 2014��2��28�� ����10:14:57
 */
public class QueryPics {

	/**
	 * @param args
	 *            void main
	 */
	public static void main(String[] args) {
		String run_stamp = "20140426114928";
		QueryPics qr = new QueryPics();
		// Map<String, ArrayList<String>> result = qr.query(run_stamp,
		// device_id);
		List<HashMap<String, ArrayList<String>>> result = qr.query(run_stamp);
		System.out.println(result);
	}

	public List<HashMap<String, ArrayList<String>>> query(String run_stamp) {
		ArrayList<String> deviceList = getDeviceList(run_stamp);
		List<HashMap<String, ArrayList<String>>> result = new ArrayList<HashMap<String, ArrayList<String>>>();
		// Map<String, ArrayList<String>> imgName = new HashMap<String,
		// ArrayList<String>>();
		// Map<String, ArrayList<String>> imgTimeStamp = new HashMap<String,
		// ArrayList<String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver"); // ����MYSQL JDBC��������
			System.out.println("Success loading mysql driver!");
		} catch (Exception e) {
			System.out.print("Error loading mysql driver!");
			e.printStackTrace();
		}
		try {
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://10.125.1.58:3306/test", "root", "isword");
			// ����URLΪ jdbc:mysql//��������ַ/���ݿ��� �������2�������ֱ��ǵ�½�û���������

			System.out.println("Success connect mysql server!");
			Statement stmt = connect.createStatement();

			for (String device_id : deviceList) {

				String queryCmd = "SELECT * FROM ct_pic where run_stamp='"
						+ run_stamp + "' and device_id='" + device_id
						+ "' ORDER BY img_timestamp";
				System.out.println(queryCmd);
				ResultSet rs = stmt.executeQuery(queryCmd);

				ArrayList<String> urlList = new ArrayList<String>();
				ArrayList<String> imgNameList = new ArrayList<String>();
				ArrayList<String> imgTimeStampList = new ArrayList<String>();
				HashMap<String, ArrayList<String>> deviceMap = new HashMap<String, ArrayList<String>>();
				while (rs.next()) {

					System.out.println(rs.getString("url"));
					urlList.add(rs.getString("url"));

					System.out.println(rs.getString("img_name"));
					imgNameList.add(rs.getString("img_name"));

					System.out.println(rs.getString("img_timestamp"));
					imgTimeStampList.add(rs.getString("img_timestamp"));

				}
				ArrayList<String> d = new ArrayList<String>(1);
				d.add(device_id);
				deviceMap.put("device_id", d);
				deviceMap.put("url", urlList);
				deviceMap.put("img_name", imgNameList);
				deviceMap.put("img_timestamp", imgTimeStampList);
				result.add(deviceMap);
			}// end for

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Map<String, ArrayList<String>> result = new HashMap<String,
		// ArrayList<String>>();
		// result.put("device_list", deviceList);
		// result.put("url", urlList);
		// result.put("img_name", imgNameList);
		// result.put("img_timestamp", imgTimeStampList);

		return result;
	}

	Map<String, ArrayList<String>> query(String run_stamp, String device_id) {
		ArrayList<String> deviceList = getDeviceList(run_stamp);
		ArrayList<String> urlList = new ArrayList<String>();
		ArrayList<String> imgNameList = new ArrayList<String>();
		ArrayList<String> imgTimeStampList = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver"); // ����MYSQL JDBC��������
			System.out.println("Success loading mysql driver!");
		} catch (Exception e) {
			System.out.print("Error loading mysql driver!");
			e.printStackTrace();
		}
		try {
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/test", "root", "isword");
			// ����URLΪ jdbc:mysql//��������ַ/���ݿ��� �������2�������ֱ��ǵ�½�û���������

			System.out.println("Success connect mysql server!");
			Statement stmt = connect.createStatement();
			String queryCmd = "SELECT * FROM ct_pic where run_stamp='"
					+ run_stamp + "' and device_id='" + device_id
					+ "' ORDER BY img_timestamp";
			System.out.println(queryCmd);
			ResultSet rs = stmt.executeQuery(queryCmd);

			while (rs.next()) {
				System.out.println(rs.getString("img_name"));
				imgNameList.add(rs.getString("img_name"));

				System.out.println(rs.getString("url"));
				urlList.add(rs.getString("url"));

				System.out.println(rs.getString("img_timestamp"));
				imgTimeStampList.add(rs.getString("img_timestamp"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		result.put("device_list", deviceList);
		result.put("url", urlList);
		result.put("img_name", imgNameList);
		result.put("img_timestamp", imgTimeStampList);

		return result;
	}

	public ArrayList<String> getDeviceList(String run_stamp) {
		ArrayList<String> deviceList = new ArrayList<String>();
		try {
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://10.125.1.58:3306/test", "root", "isword");
			// ����URLΪ jdbc:mysql//��������ַ/���ݿ��� �������2�������ֱ��ǵ�½�û���������

			System.out.println("Success connect mysql server!");
			Statement stmt = connect.createStatement();
			String queryCmd = "SELECT device_id FROM ct_pic"
					+ " where run_stamp='" + run_stamp
					+ "' GROUP BY device_id;";
			System.out.println(queryCmd);
			ResultSet rs = stmt.executeQuery(queryCmd);

			while (rs.next()) {
				System.out.println(rs.getString("device_id"));
				deviceList.add(rs.getString("device_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceList;
	}

}
