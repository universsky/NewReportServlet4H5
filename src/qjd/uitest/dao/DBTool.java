/**
 * 
 */
package qjd.uitest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * @author chenguangjian 2015年4月1日 下午9:02:34
 */

public class DBTool {

	private JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil
			.getBean("jdbcTemplate");

	// @Autowired
	// private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {

		String tcKey = "LoginTest.jslogin_UserNameIsEmptyTest";
		String timestamp = "20150406233421";
		String imgName = "20150401203951_LoginTest.jslogin_UserNameNotExistTest.jpeg";
		String tcResult = "PASS";

		DBTool DBTool = new DBTool();

		// DBTool.insertImgName(tcKey, timestamp, imgName, tcResult);
		// String v = DBTool.getTcValueBytcKey(tcKey);
		// System.out.println(v);

		System.out.println(DBTool.getTCResultByTimeStamp(timestamp));

	}

	public List<TCResult> getTCResultByTimeStamp(String timestamp) {
		List<TCResult> resultList = new ArrayList<TCResult>();

		// String sql =
		// " select id,tc_key,timestamp,img_name,tc_result from t_uitest_result where timestamp=? ";
		String sql = " select * from t_uitest_result where timestamp=? ";

		Object params[] = new Object[] { timestamp };

		System.out.println(jdbcTemplate);

		List rows = jdbcTemplate.queryForList(sql, params);

		for (int i = 0; i < rows.size(); i++) {

			Map resultMap = (Map) rows.get(i);
			TCResult tcResult = new TCResult();
			tcResult.setId(resultMap.get("id") + "");
			tcResult.setTcKey(resultMap.get("tc_key") + "");
			tcResult.setTimeStamp(resultMap.get("timestamp") + "");
			tcResult.setImgName(resultMap.get("img_name") + "");
			tcResult.setTcResult(resultMap.get("tc_result") + "");
			tcResult.setExpected(resultMap.get("expected") + "");
			tcResult.setActual(resultMap.get("actual") + "");
			tcResult.setLog(resultMap.get("log") + "");
			resultList.add(tcResult);

		}

		// jdbcTemplate.query(sql, params, new RowCallbackHandler() {
		//
		// @Override
		// public void processRow(ResultSet rs) throws SQLException {
		//
		// }
		// });

		return resultList;

	}

	/**
	 * 
	 * @param tcKey
	 * @return
	 */
	public String getTcValueBytcKey(String tcKey) {
		String sql = " select tc_value from t_uitest_cases where tc_key=? ";
		Object params[] = new Object[] { tcKey };
		final String[] tcValue = { "" };
		System.out.println(jdbcTemplate);
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {

				tcValue[0] = rs.getString("tc_value");
			}
		});

		return tcValue[0];

	}

	/**
	 * 
	 * @param timestamp
	 * @param imgName
	 * @return
	 */
	public int insertImgName(String tcKey, String timestamp, String imgName,
			String tcResult) {

		String sql = " INSERT INTO t_uitest_result(tc_key,timestamp,img_name,tc_result) VALUES (?,?,?,?) ";
		Object params[] = new Object[] { tcKey, timestamp, imgName, tcResult };

		System.out.println(jdbcTemplate);

		return jdbcTemplate.update(sql, params);

	}

}
