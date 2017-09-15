package com.se.working.controller;

public interface ControllerMapping {
	String ERROR = "error";
	String USER = "user";
	String REDIRECT = "redirect:";

	/**
	 * 用户基本操作请求
	 * 
	 * @author BO
	 *
	 */
	public interface UserRequestMapping {
		String LOGIN = "/login";
		String ILOGIN = "ilogin";
		String MAIN = "/main";
		String UPDATE_PASSWORD = "/updatepassword";
		String UPDATE_USERSETTING = "/updateusersetting";
		String LOGOUT = "/logout";
	}

	/**
	 * 用户基本操作JSP文件映射
	 * 
	 * @author BO
	 *
	 */
	public interface UserViewMapping {
		// user jsp文件夹名称
		String BASEPATH = "/user";
		String LOGIN = "/login";
		String MAIN = BASEPATH + UserRequestMapping.MAIN;
		String UPDATE_USERSETTING = BASEPATH + UserRequestMapping.UPDATE_USERSETTING;
	}

	/**
	 * 用户监考请求
	 * 
	 * @author BO
	 *
	 */
	public interface UserInviRequestMapping {
		//
		String BASEPATH = "/invi";
		String LIST_MY_INVIINFO = BASEPATH + "/listmyinviinfo/{invitype}";
		String LIST_INVIINFO_INVITYPE = BASEPATH + "/listinviinfo/{invitype}";
		String LIST_INVIINFO_INVITYPE_MAX = BASEPATH + "/listinviinfo/{invitype}/{max}";
		String INVIINFO_DETAIL_ID = BASEPATH + "/invinfodetail/{inviinfoid}";
		String DOWNLOAD_INVIINFO_EXCEL = BASEPATH + "/downloadinviinfoexcel";
		String LIST_INVIINFO_UNASSINVI = BASEPATH + "/listinviinfo/unassinvi";
	}

	/**
	 * 用户监考JSP文件映射
	 * 
	 * @author BO
	 *
	 */
	public interface UserInviViewMapping {
		String BASEPATH = "/user/invi";
		String LIST_MY_INVIINFO = BASEPATH + "/listmyinviinfo";
		String LIST_INVIINFO = BASEPATH + "/listinviinfo";
		String LIST_INVIINFO_DETAIL = BASEPATH + "/listinviinfodetail";
	}

	public interface AdminInviRequestMapping {
		String BASEPATH = "/admin/invi";
		String IMPORT_TIMETABLE = BASEPATH + "/importtimetable";
		String IMPORT_INVIINFOS = BASEPATH + "/importinviinfos";
		String SAVE_INVIINFOS = BASEPATH + "/saveinviinfos";
		String ASSIGN_INVI_INVIID = BASEPATH + "/assigninvi/{inviid}";
		String ASSIGN_INVI = BASEPATH + "/assigninvi";
		String SEND_INVI_MESSAGE_INVIID = BASEPATH + "/sendinvimessage/{inviinfoid}";
		String SEND_INVI_MESSAGE = BASEPATH + "/sendinvimessage";
		String UPDATE_INVI_INFO_INVIID = BASEPATH + "/updateinviinfo/{infoid}";
		String UPDATE_INVI_INFO = BASEPATH + "/updateinviinfo";
		String SPLIT_INVIINFO = BASEPATH + "/splitinviinfo";
		String DEL_INVIINFO = BASEPATH + "/delinviinfo";
		String ADD_INVIINFO = BASEPATH + "/addinviinfo";
		String SET_INVIINFO_DONE = BASEPATH + "/setinviinfodone";
		String INVI_MANAGEMENT = BASEPATH + "/invimanagement";
		
		
		String ADD_SPECINVIINFO = BASEPATH + "/addspecinviinfo";

	}

	public interface AdminInviViewMapping {
		String BASEPATH = "/admin/invi";
		String ASSIGN_INVI = BASEPATH + "/assigninvi";
		String SEND_INVI_MESSAGE = BASEPATH + "/sendinvimessage";
		String UPDATE_INVI_INFO = BASEPATH + "/updateinviinfo";
		String INVI_MANAGEMENT = BASEPATH + "/invimanagement";
	}

	public interface AdminSettingRequestMapping {
		String BASEPATH = "/admin/setting";
		String ADD_USER = BASEPATH + "/adduser";
		String UPDATE_USER = BASEPATH + "/updateuser";
		String SET_DEFAULTPWD = BASEPATH + "/setdefaultpwd";
		String UPDATE_USER_AJAX = BASEPATH + "/updateuserajax";
		String UPDATE_AUTH = BASEPATH + "/updateauth";
		String UPDATE_INVI = BASEPATH + "/updateinvi";
		String UPDATE_NOTIF = BASEPATH + "/updatenotif";
		String USER_MANAGERMENT = BASEPATH + "/usermanagement";
	}

	public interface AdminSettingViewMapping {
		// String BASEPATH = "/admin/setting";
		// String ADD_USER = BASEPATH + "/adduser";
	   // String UPDATE_USER = BASEPATH + "/updateuser";	
	}

	public interface SuperAdminRequestMapping {
		String BASEPATH = "/superadmin";
		String UPDATE_BASEDATE = BASEPATH + "/updatebasedate";
		String INIT_USERS = BASEPATH + "/initusers";
		
		String SYS_MANAGEMENT = BASEPATH + "/sysmanagement";
	}

}
