package cn.cjf.http;

public class ApiMethodProperty {
	
	public static String LOGI_HOST;
	public static String LOGI_PORT;
	public static String LOGI_PROJECT;
	
	public static String ERP_APP_ID;

	/**
	 * 根据班级编码查询班级相关信息
	 */
	public static String ERP_CLASS_INFO;
	
	/**
	 * 锁定班级库存
	 */
	public static String ERP_CLASS_STOCK_LOCK;
	
	/**
	 * 报名时检查班级可报状态
	 */
	public static String ERP_CLASS_STOCK_CHECK;
	
	/**
	 * 根据班级编码获取优惠金额
	 */
	public static String ERP_DISCOUNT_MONEY;
	
	/**
	 * 清空学生购物车
	 */
	public static String CART_CLEAN;
	
	/**
	 * 用户操作日志
	 */
	public static String LOG_USER_OPERATE;
	
	public static String LOG_USER_BATCH_OPERATE;
	
	/**
	 * 短信下发日志
	 */
	public static String LOG_SMS_SEND;
	
	/**
	 * 融云发送消息
	 */
	public static String PUBLISH_SYSTEM;
	
	public static String getERP_APP_ID() {
		return ERP_APP_ID;
	}

	public static void setERP_APP_ID(String eRP_APP_ID) {
		ERP_APP_ID = eRP_APP_ID;
	}

	public static String getLOGI_HOST() {
		return LOGI_HOST;
	}

	public static void setLOGI_HOST(String lOGI_HOST) {
		LOGI_HOST = lOGI_HOST;
	}

	public static String getLOGI_PORT() {
		return LOGI_PORT;
	}

	public static void setLOGI_PORT(String lOGI_PORT) {
		LOGI_PORT = lOGI_PORT;
	}

	public static String getLOGI_PROJECT() {
		return LOGI_PROJECT;
	}

	public static void setLOGI_PROJECT(String lOGI_PROJECT) {
		LOGI_PROJECT = lOGI_PROJECT;
	}

	public static String getERP_CLASS_INFO() {
		return ERP_CLASS_INFO;
	}

	public static void setERP_CLASS_INFO(String eRP_CLASS_INFO) {
		ERP_CLASS_INFO = eRP_CLASS_INFO;
	}

	public static String getERP_CLASS_STOCK_LOCK() {
		return ERP_CLASS_STOCK_LOCK;
	}

	public static void setERP_CLASS_STOCK_LOCK(String eRP_CLASS_STOCK_LOCK) {
		ERP_CLASS_STOCK_LOCK = eRP_CLASS_STOCK_LOCK;
	}

	public static String getERP_CLASS_STOCK_CHECK() {
		return ERP_CLASS_STOCK_CHECK;
	}

	public static void setERP_CLASS_STOCK_CHECK(String eRP_CLASS_STOCK_CHECK) {
		ERP_CLASS_STOCK_CHECK = eRP_CLASS_STOCK_CHECK;
	}

	public static String getERP_DISCOUNT_MONEY() {
		return ERP_DISCOUNT_MONEY;
	}

	public static void setERP_DISCOUNT_MONEY(String eRP_DISCOUNT_MONEY) {
		ERP_DISCOUNT_MONEY = eRP_DISCOUNT_MONEY;
	}

	public static String getCART_CLEAN() {
		return CART_CLEAN;
	}

	public static void setCART_CLEAN(String cART_CLEAN) {
		CART_CLEAN = cART_CLEAN;
	}

	public String getLOG_USER_OPERATE() {
		return LOG_USER_OPERATE;
	}

	public void setLOG_USER_OPERATE(String lOG_USER_OPERATE) {
		LOG_USER_OPERATE = lOG_USER_OPERATE;
	}

	public static String getLOG_USER_BATCH_OPERATE() {
		return LOG_USER_BATCH_OPERATE;
	}

	public static void setLOG_USER_BATCH_OPERATE(String lOG_USER_BATCH_OPERATE) {
		LOG_USER_BATCH_OPERATE = lOG_USER_BATCH_OPERATE;
	}

	public static String getLOG_SMS_SEND() {
		return LOG_SMS_SEND;
	}

	public static void setLOG_SMS_SEND(String lOG_SMS_SEND) {
		LOG_SMS_SEND = lOG_SMS_SEND;
	}

	public static String getPUBLISH_SYSTEM() {
		return PUBLISH_SYSTEM;
	}

	public static void setPUBLISH_SYSTEM(String pUBLISH_SYSTEM) {
		PUBLISH_SYSTEM = pUBLISH_SYSTEM;
	}
}
