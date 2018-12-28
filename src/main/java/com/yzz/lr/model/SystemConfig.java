package com.yzz.lr.model;

public class SystemConfig {
	private String key;//
	private String value;//
	private String description;//说明

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key=key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value=value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description=description;
	}


	public static final String MAX_SMS_COUNT_FOR_DAY = "MAX_SMS_COUNT_FOR_DAY";
	public static final String MIN_RESEND_SMS_TIME = "MIN_RESEND_SMS_TIME";
	public static final String MESSAGE_OVER_TIME = "MESSAGE_OVER_TIME";
	public static final String REDURECT_URI = "REDURECT_URI";
	public static final String USER_PERCENT = "USER_PERCENT";
	public static final String USER_PERCENT_NOT_AGENT = "USER_PERCENT_NOT_AGENT";
	public static final String AGENT_PERCENT = "AGENT_PERCENT";
	public static final String BASE_PRICE = "BASE_PRICE";


}