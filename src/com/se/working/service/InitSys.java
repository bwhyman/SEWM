package com.se.working.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 系统初始化，不能直接调用dao层，必须调用其他service，由其他service调用dao???
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
		superAdminService.initGroup();
		superAdminService.initTeacherTitle();
		superAdminService.initUserAuthority();
		superAdminService.initInviStatusType();
		superAdminService.initUser();
		superAdminService.initInviMessageType();
		superAdminService.initSpecInviType();
		superAdminService.initFileType();
		superAdminService.initFileTaskStatus();
	}
	public InitSys() {
		// TODO Auto-generated constructor stub
	}
}
