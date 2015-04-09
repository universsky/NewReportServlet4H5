/**
 * 
 */
package qjd.uitest.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenguangjian
 *
 */
public class TCResultDao {

	/**
	 * 
	 * @param timestamp
	 * @return
	 */
	public List<TCResult> getTCResultByTimeStamp(String timestamp) {
		DBTool DBTool = new DBTool();
		List<TCResult> result = DBTool.getTCResultByTimeStamp(timestamp);
		System.out.println(result);
		return result;
	}

}
