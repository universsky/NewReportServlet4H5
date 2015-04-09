/**
 * 
 */
package qjd.uitest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenguangjian
 *
 */
public class TCResult {

	private String id;
	private String tcKey;
	private String timeStamp;
	private String imgName;
	private String tcResult;
	private String expected;
	private String actual;
	private String log;

	@Override
	public String toString() {
		return "Result [id=" + id + ", tcKey=" + tcKey + ", timeStamp="
				+ timeStamp + ", imgName=" + imgName + ", tcResult=" + tcResult
				+ ", expected=" + expected + ", actual=" + actual + " , log="
				+ log + "]";
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTcKey() {
		return tcKey;
	}

	public void setTcKey(String tcKey) {
		this.tcKey = tcKey;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getTcResult() {
		return tcResult;
	}

	public void setTcResult(String tcResult) {
		this.tcResult = tcResult;
	}

}
