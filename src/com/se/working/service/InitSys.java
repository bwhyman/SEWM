package com.se.working.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 系统初始化
 * @author BO
 *
 */
@Service
@Transactional
public class InitSys implements InitializingBean {

	@Autowired
	private SuperAdminService superAdminService;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		superAdminService.initTeacherTitle();
		superAdminService.initUserAuthority();
		superAdminService.initInviStatusType();
		superAdminService.initUser();
		superAdminService.initSpecInviType();
		superAdminService.initFileType();
		superAdminService.initFileTaskStatus();
	}
	public InitSys() {
		// TODO Auto-generated constructor stub
	}
}
