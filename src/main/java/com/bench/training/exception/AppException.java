package com.bench.training.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppException extends Exception {
	private static final long serialVersionUID = 1060583112626047093L;
	
	protected List<ErrorInfo> errorInfoList = new ArrayList<>();
	
	public AppException() {
	}
	
	public ErrorInfo addInfo(ErrorInfo info) {
		this.errorInfoList.add(info);
		return info;
	}
	
	public ErrorInfo addInfo() {
		ErrorInfo info = new ErrorInfo();
		this.errorInfoList.add(info);
		return info;
	}
	
	public String getUserLog() {
		StringBuilder sb = new StringBuilder();
		for(ErrorInfo ei : errorInfoList) {
			if( !"".equals(ei.getUserLogString())) {
				sb.append(String.format("%s%n", ei.getUserLogString()));
			}
		}
		return sb.toString();
	}
	
	public String getDevLog() {
		StringBuilder sb = new StringBuilder();
		sb.append("DEVELOPMENT LOG");
		int i = 0;
		for(ErrorInfo ei : errorInfoList) {
			sb.append(String.format("%n------------ %d. ---------------%n", i));
			sb.append(String.format("%s%n",ei.getDevLogString()));
		}
		sb.append(String.format("------------ STACKTRACE ------------%n"));
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return getDevLog();
	}
	
	public List<ErrorInfo> getErrorInfoList() {
		return Collections.unmodifiableList(this.errorInfoList);
	}
	
}
