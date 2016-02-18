package com.se.working.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.se.working.dao.TeacherTitleDao;
import com.se.working.dao.UserAuthorityDao;
import com.se.working.dao.UserDao;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.entity.TeacherTitle.TeacherTitleType;
import com.se.working.entity.UserAuthority.UserAuthorityLevel;
import com.se.working.entity.UserAuthority.UserAuthorityType;
import com.se.working.invigilation.dao.InviTypeDao;
import com.se.working.invigilation.dao.SpecialInviTypeDao;
import com.se.working.invigilation.dao.TeacherInviDao;
import com.se.working.invigilation.entity.InvigilationStatusType;
import com.se.working.invigilation.entity.SpecialInvigilationType;
import com.se.working.invigilation.entity.TeacherInvigilation;

@Service
@Transactional
public class SuperAdminService extends GenericService<User, Long>{
	
	@Autowired
	private TeacherTitleDao teacherTitleDao;
	@Autowired
	private UserAuthorityDao userAuthorityDao;
	@Autowired
	private InviTypeDao invigilationStatusTypeDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TeacherInviDao teacherInvigilationDao;
	@Autowired
	private SpecialInviTypeDao specialInviTypeDao;
	/**
	 * 初始化职称
	 * @return 
	 */
	public void initTeacherTitle() {
		if (teacherTitleDao.list().size() == 0) {
			TeacherTitle lecture = new TeacherTitle();
			lecture.setName("讲师");
			teacherTitleDao.persist(lecture);
			TeacherTitle ap = new TeacherTitle();
			ap.setName("副教授");
			teacherTitleDao.persist(ap);
			TeacherTitle prof = new TeacherTitle();
			prof.setName("教授");
			teacherTitleDao.persist(prof);
			TeacherTitle ass = new TeacherTitle();
			ass.setName("助教");
			teacherTitleDao.persist(ass);
		}
	}
	
	/**
	 * 初始化权限
	 */
	public void initUserAuthority() {
		if (userAuthorityDao.list().size() == 0) {
			UserAuthority teacher = new UserAuthority();
			teacher.setName("教师");
			teacher.setLevel(UserAuthority.UserAuthorityLevel.TEACHER);
			userAuthorityDao.persist(teacher);
			UserAuthority admin = new UserAuthority();
			admin.setName("管理员");
			admin.setLevel(UserAuthorityLevel.ADAMIN);
			userAuthorityDao.persist(admin);
			UserAuthority superadmin = new UserAuthority();
			superadmin.setName("超级管理员");
			superadmin.setLevel(UserAuthority.UserAuthorityLevel.SUPERADMIN);
			userAuthorityDao.persist(superadmin);
			UserAuthority student = new UserAuthority();
			student.setName("学生");
			student.setLevel(UserAuthority.UserAuthorityLevel.STUDENT);
			userAuthorityDao.persist(student);
		}
	}
	/**
	 * 初始化监考状态
	 */
	public void initInviStatusType() {
		if (invigilationStatusTypeDao.list().size() == 0) {
			InvigilationStatusType  unass = new InvigilationStatusType();
			unass.setName("未分配");
			invigilationStatusTypeDao.persist(unass);
			InvigilationStatusType ass = new InvigilationStatusType();
			ass.setName("已分配");
			invigilationStatusTypeDao.persist(ass);
			InvigilationStatusType reminded = new InvigilationStatusType();
			reminded.setName("已提醒");
			invigilationStatusTypeDao.persist(reminded);
			InvigilationStatusType done = new InvigilationStatusType();
			done.setName("已完成");
			invigilationStatusTypeDao.persist(done);
			
		}
		
	}

	/**
	 * 初始化用户
	 */
	public void initUser() {
		if (userDao.list().size() == 0) {
			User luo = new User();
			luo.setName("罗嗣卿");
			luo.setEmployeeNumber("1001");
			luo.setTitle(new TeacherTitle(TeacherTitleType.AP));
			luo.setPhoneNumber("");
			luo.setUserAuthority(new UserAuthority(UserAuthorityType.ADAMIN));
			userDao.persist(luo);
			TeacherInvigilation iluo = new TeacherInvigilation();
			iluo.setUser(luo);
			iluo.setSqecQuantity(10);
			teacherInvigilationDao.persist(iluo);
			
			User lili = new User();
			lili.setName("李莉");
			lili.setEmployeeNumber("1002");
			lili.setTitle(new TeacherTitle(TeacherTitleType.AP));
			lili.setPhoneNumber("");
			lili.setUserAuthority(new UserAuthority(UserAuthorityType.ADAMIN));
			userDao.persist(lili);
			TeacherInvigilation ilili = new TeacherInvigilation();
			ilili.setUser(lili);
			ilili.setSqecQuantity(12);
			teacherInvigilationDao.persist(ilili);
			
			User wu = new User();
			wu.setName("吴頔");
			wu.setEmployeeNumber("1003");
			wu.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			wu.setPhoneNumber("");
			wu.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(wu);
			TeacherInvigilation iwu = new TeacherInvigilation();
			iwu.setUser(wu);
			iwu.setSqecQuantity(13);
			teacherInvigilationDao.persist(iwu);
			
			User bian = new User();
			bian.setName("边继龙");
			bian.setEmployeeNumber("1004");
			bian.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			bian.setPhoneNumber("");
			bian.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(bian);
			TeacherInvigilation ibian = new TeacherInvigilation();
			ibian.setUser(bian);
			ibian.setSqecQuantity(14);
			teacherInvigilationDao.persist(ibian);
			
			User bo = new User();
			bo.setName("王波");
			bo.setEmployeeNumber("1020090008");
			bo.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			bo.setPhoneNumber("15104548299");
			bo.setUserAuthority(new UserAuthority(UserAuthorityType.SUPERADMIN));
			userDao.persist(bo);
			TeacherInvigilation ibo = new TeacherInvigilation();
			ibo.setUser(bo);
			ibo.setSqecQuantity(15);
			teacherInvigilationDao.persist(ibo);
			
			User sunzhe = new User();
			sunzhe.setName("孙哲");
			sunzhe.setEmployeeNumber("1020090007");
			sunzhe.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			sunzhe.setPhoneNumber("18946000922");
			sunzhe.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(sunzhe);
			TeacherInvigilation isun = new TeacherInvigilation();
			isun.setUser(sunzhe);
			isun.setSqecQuantity(15);
			teacherInvigilationDao.persist(isun);
			
		}
	}
	
	public void initSpecInviType() {
		SpecialInvigilationType en46 = new SpecialInvigilationType();
		en46.setName("英语四六级");
		specialInviTypeDao.persist(en46);
		
		SpecialInvigilationType servant = new SpecialInvigilationType();
		servant.setName("公务员");
		specialInviTypeDao.persist(servant);
		
		SpecialInvigilationType postgrad = new SpecialInvigilationType();
		postgrad.setName("研究生");
		specialInviTypeDao.persist(postgrad);

	}
	
	public SuperAdminService() {
		// TODO Auto-generated constructor stub
	}

}
