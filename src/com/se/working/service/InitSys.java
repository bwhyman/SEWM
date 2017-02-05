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
	private InitService initService;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		initService.initGroup();
		initService.initTeacherTitle();
		initService.initUserAuthority();
		initService.initInviStatusType();
		// initService.initUser();
		initService.initInviMessageType();
		initService.initSpecInviType();
		initService.initFileType();
		initService.initFileTaskStatus();
		initService.importUser();
	}
	
	
}
