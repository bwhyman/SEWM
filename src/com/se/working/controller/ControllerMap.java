package com.se.working.controller;

public interface ControllerMap {
	String ERROR = "error";
	String USER = "user";
	String REDIRECT =  "redirect:";
	
	/**
	 * 用户基本操作请求
	 * @author BO
	 *
	 */
	public interface UserRequestMap {
		String LOGIN = "/login";
		String ILOGIN = "ilogin";
		String MAIN = "/main";
		String UPDATE_PASSWORD = "/updatepassword";
		String UPDATE_USERSETTING = "/updateusersetting";
		String LOGOUT = "/logout";
	}
	/**
	 * 用户基本操作JSP文件映射
	 * @author BO
	 *
	 */
	public interface UserResponseMap {
		// user jsp文件夹名称
		String BASEPATH = "/user";
		String LOGIN = "/login";
		String MAIN = BASEPATH + UserRequestMap.MAIN; 
		String UPDATE_USERSETTING = BASEPATH + UserRequestMap.UPDATE_USERSETTING;
	}
	
	/**
	 * 用户监考请求
	 * @author BO
	 *
	 */
	public interface UserInviRequestMap {
		// 
		String BASEPATH= "/invi";
		String LIST_MY_INVIINFO = BASEPATH + "/listmyinviinfo/{invitype}";
		String LIST_INVIINFO_INVITYPE = BASEPATH + "/listinviinfo/{invitype}";
		String LIST_INVIINFO_INVITYPE_MAX = BASEPATH + "/listinviinfo/{invitype}/{max}";
		String INVIINFO_DETAIL_ID = BASEPATH + "/invinfodetail/{inviinfoid}";
		String DOWNLOAD_INVIINFO_EXCEL = BASEPATH + "/downloadinviinfoexcel";
		String LIST_INVIINFO_UNASSINVI = BASEPATH + "/listinviinfo/unassinvi";
	}
	/**
	 * 用户监考JSP文件映射
	 * @author BO
	 *
	 */
	public interface UserInviReponseMap {
		String BASEPATH = "/user/invi";
		String LIST_MY_INVIINFO = BASEPATH + "/listmyinviinfo";
		String LIST_INVIINFO = BASEPATH + "/listinviinfo";
		String LIST_INVIINFO_DETAIL = BASEPATH + "/listinviinfodetail";
	}
	
	public interface AdminInviRequestMap {
		String BASEPATH=  "/admin/invi";
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
		
	}
	public interface AdminInviResponseMap {
		String BASEPATH = "/admin/invi";
		String ASSIGN_INVI = BASEPATH + "/assigninvi"; 
		String SEND_INVI_MESSAGE = BASEPATH + "/sendinvimessage";
		String UPDATE_INVI_INFO = BASEPATH + "/updateinviinfo";
		String INVI_MANAGEMENT = BASEPATH + "/invimanagement";
	}
	
	public interface AdminSettingRequestMap {
		String BASEPATH = "/admin/setting";
		String ADD_USER = BASEPATH + "/adduser";
		String UPDATE_USER = BASEPATH + "/updateuser";
		String SET_DEFAULTPWD = BASEPATH + "/setdefaultpwd"; 
		String UPDATE_USER_AJAX = BASEPATH + "/updateuserajax";
		String UPDATE_AUTH = BASEPATH + "/updateauth";
		String UPDATE_INVI = BASEPATH + "/updateinvi";
		String UPDATE_NOTIF = BASEPATH + "/updatenotif";
	}
	public interface AdminSettingResponseMap {
		String BASEPATH = "/admin/setting";
		String ADD_USER = BASEPATH + "/adduser";
		String UPDATE_USER = BASEPATH + "/updateuser"; 
	}
	
}
