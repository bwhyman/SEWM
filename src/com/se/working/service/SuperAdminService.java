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
import com.se.working.project.dao.ProjectFileTypeDao;
import com.se.working.project.dao.StudentProjectDao;
import com.se.working.project.dao.TeacherProjectDao;
import com.se.working.project.entity.ProjectFileType;
import com.se.working.project.entity.StudentProject;
import com.se.working.project.entity.TeacherProject;
import com.se.working.task.dao.FileTaskStatusDao;
import com.se.working.task.dao.FileTypeDao;
import com.se.working.task.dao.TeacherTaskDao;
import com.se.working.task.entity.FileTaskStatus;
import com.se.working.task.entity.FileType;
import com.se.working.task.entity.TeacherTask;
import com.se.working.util.MD5;

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
	@Autowired
	private TeacherTaskDao teacherTaskDao;
	@Autowired
	private FileTypeDao fileTypeDao;
	@Autowired
	private FileTaskStatusDao fileTaskStatusDao;
	@Autowired
	private ProjectFileTypeDao projectFileTypeDao;
	@Autowired
	private TeacherProjectDao teacherProjectDao;
	@Autowired
	private StudentProjectDao studentProjectDao;
	
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
		if (userAuthorityDao.get(UserAuthorityType.TEACHER).getUsers().size() == 0) {
			int point = 100;
			int sqnum = 10;
			User luo = new User();
			luo.setName("罗嗣卿");
			luo.setEmployeeNumber("1001");
			luo.setPassword(MD5.generateMD5(luo.getEmployeeNumber()));
			luo.setTitle(new TeacherTitle(TeacherTitleType.AP));
			luo.setPhoneNumber("13304507766");
			luo.setUserAuthority(new UserAuthority(UserAuthorityType.ADAMIN));
			userDao.persist(luo);
			TeacherInvigilation iluo = new TeacherInvigilation();
			iluo.setUser(luo);
			iluo.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(iluo);
			TeacherTask tluo = new TeacherTask();
			tluo.setUser(luo);
			tluo.setPoint(point);
			teacherTaskDao.persist(tluo);
			TeacherProject tpluo = new TeacherProject();
			tpluo.setUser(luo);
			teacherProjectDao.persist(tpluo);
			
			
			User lili = new User();
			lili.setName("李莉");
			lili.setEmployeeNumber("1002");
			lili.setPassword(MD5.generateMD5(lili.getEmployeeNumber()));
			lili.setTitle(new TeacherTitle(TeacherTitleType.AP));
			lili.setPhoneNumber("15046066917");
			lili.setUserAuthority(new UserAuthority(UserAuthorityType.ADAMIN));
			userDao.persist(lili);
			TeacherInvigilation ilili = new TeacherInvigilation();
			ilili.setUser(lili);
			ilili.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(ilili);
			TeacherTask tlili = new TeacherTask();
			tlili.setUser(lili);
			tlili.setPoint(point);
			teacherTaskDao.persist(tlili);
			TeacherProject tplili = new TeacherProject();
			tplili.setUser(lili);
			teacherProjectDao.persist(tplili);
			
			User wangjian = new User();
			wangjian.setName("王健");
			wangjian.setEmployeeNumber("1003");
			wangjian.setPassword(MD5.generateMD5(wangjian.getEmployeeNumber()));
			wangjian.setTitle(new TeacherTitle(TeacherTitleType.AP));
			wangjian.setPhoneNumber("13845082633");
			wangjian.setUserAuthority(new UserAuthority(UserAuthorityType.ADAMIN));
			userDao.persist(wangjian);
			TeacherInvigilation iwangjian = new TeacherInvigilation();
			iwangjian.setUser(wangjian);
			iwangjian.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(iwangjian);
			TeacherTask twangjian = new TeacherTask();
			twangjian.setUser(wangjian);
			twangjian.setPoint(point);
			teacherTaskDao.persist(twangjian);
			TeacherProject tpwangjian = new TeacherProject();
			tpwangjian.setUser(wangjian);
			teacherProjectDao.persist(tpwangjian);
			
			User sujianmin = new User();
			sujianmin.setName("苏健民");
			sujianmin.setEmployeeNumber("1004");
			sujianmin.setPassword(MD5.generateMD5(sujianmin.getEmployeeNumber()));
			sujianmin.setTitle(new TeacherTitle(TeacherTitleType.PROF));
			sujianmin.setPhoneNumber("13394513177");
			sujianmin.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(sujianmin);
			TeacherInvigilation isujianmin = new TeacherInvigilation();
			isujianmin.setUser(sujianmin);
			isujianmin.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(isujianmin);
			TeacherTask tsujianmin = new TeacherTask();
			tsujianmin.setUser(sujianmin);
			tsujianmin.setPoint(point);
			teacherTaskDao.persist(tsujianmin);
			TeacherProject tpsujianmin = new TeacherProject();
			tpsujianmin.setUser(sujianmin);
			teacherProjectDao.persist(tpsujianmin);
			
			User zhangxiying = new User();
			zhangxiying.setName("张锡英");
			zhangxiying.setEmployeeNumber("1005");
			zhangxiying.setPassword(MD5.generateMD5(zhangxiying.getEmployeeNumber()));
			zhangxiying.setTitle(new TeacherTitle(TeacherTitleType.AP));
			zhangxiying.setPhoneNumber("13100969429");
			zhangxiying.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(zhangxiying);
			TeacherInvigilation izhangxiying = new TeacherInvigilation();
			izhangxiying.setUser(zhangxiying);
			izhangxiying.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(izhangxiying);
			TeacherTask tzhangxiying = new TeacherTask();
			tzhangxiying.setUser(zhangxiying);
			tzhangxiying.setPoint(point);
			teacherTaskDao.persist(tzhangxiying);
			TeacherProject tpzhangxiying = new TeacherProject();
			tpzhangxiying.setUser(zhangxiying);
			teacherProjectDao.persist(tpzhangxiying);
			
			User qiuzhaowen = new User();
			qiuzhaowen.setName("邱兆文");
			qiuzhaowen.setEmployeeNumber("1006");
			qiuzhaowen.setPassword(MD5.generateMD5(qiuzhaowen.getEmployeeNumber()));
			qiuzhaowen.setTitle(new TeacherTitle(TeacherTitleType.AP));
			qiuzhaowen.setPhoneNumber("13903601207");
			qiuzhaowen.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(qiuzhaowen);
			TeacherInvigilation iqiuzhaowen = new TeacherInvigilation();
			iqiuzhaowen.setUser(qiuzhaowen);
			iqiuzhaowen.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(iqiuzhaowen);
			TeacherTask tqiuzhaowen = new TeacherTask();
			tqiuzhaowen.setUser(qiuzhaowen);
			tqiuzhaowen.setPoint(point);
			teacherTaskDao.persist(tqiuzhaowen);
			TeacherProject tpqiuzhaowen = new TeacherProject();
			tpqiuzhaowen.setUser(qiuzhaowen);
			teacherProjectDao.persist(tpqiuzhaowen);
			
			User bo = new User();
			bo.setName("王波");
			bo.setEmployeeNumber("1007");
			bo.setPassword(MD5.generateMD5(bo.getEmployeeNumber()));
			bo.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			bo.setPhoneNumber("15104548299");
			bo.setUserAuthority(new UserAuthority(UserAuthorityType.SUPERADMIN));
			userDao.persist(bo);
			TeacherInvigilation ibo = new TeacherInvigilation();
			ibo.setUser(bo);
			ibo.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(ibo);
			TeacherTask tbo = new TeacherTask();
			tbo.setUser(bo);
			tbo.setPoint(point);
			teacherTaskDao.persist(tbo);
			TeacherProject tpbo = new TeacherProject();
			tpbo.setUser(bo);
			teacherProjectDao.persist(tpbo);
			
			User liyan = new User();
			liyan.setName("李琰");
			liyan.setEmployeeNumber("1008");
			liyan.setPassword(MD5.generateMD5(liyan.getEmployeeNumber()));
			liyan.setPhoneNumber("13936130520");
			liyan.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			liyan.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(liyan);
			TeacherInvigilation iliyan = new TeacherInvigilation();
			iliyan.setUser(liyan);
			iliyan.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(iliyan);
			TeacherTask tliyan = new TeacherTask();
			tliyan.setUser(liyan);
			tliyan.setPoint(point);
			teacherTaskDao.persist(tliyan);
			TeacherProject tpliyan = new TeacherProject();
			tpliyan.setUser(liyan);
			teacherProjectDao.persist(tpliyan);
			
			User zhaoyuming = new User();
			zhaoyuming.setName("赵玉茗");
			zhaoyuming.setEmployeeNumber("1009");
			zhaoyuming.setPassword(MD5.generateMD5(zhaoyuming.getEmployeeNumber()));
			zhaoyuming.setPhoneNumber("");
			zhaoyuming.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			zhaoyuming.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(zhaoyuming);
			TeacherInvigilation izhaoyuming = new TeacherInvigilation();
			izhaoyuming.setUser(zhaoyuming);
			izhaoyuming.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(izhaoyuming);
			TeacherTask tzhaoyuming = new TeacherTask();
			tzhaoyuming.setUser(zhaoyuming);
			tzhaoyuming.setPoint(point);
			teacherTaskDao.persist(tzhaoyuming);
			TeacherProject tpzhaoyuming = new TeacherProject();
			tpzhaoyuming.setUser(zhaoyuming);
			teacherProjectDao.persist(tpzhaoyuming);
			
			User wu = new User();
			wu.setName("吴頔");
			wu.setEmployeeNumber("1010");
			wu.setPassword(MD5.generateMD5(wu.getEmployeeNumber()));
			wu.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			wu.setPhoneNumber("15124506720");
			wu.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(wu);
			TeacherInvigilation iwu = new TeacherInvigilation();
			iwu.setUser(wu);
			iwu.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(iwu);
			TeacherTask twu = new TeacherTask();
			twu.setUser(wu);
			twu.setPoint(point);
			teacherTaskDao.persist(twu);
			TeacherProject tpwu = new TeacherProject();
			tpwu.setUser(wu);
			teacherProjectDao.persist(tpwu);
	
			
			User bian = new User();
			bian.setName("边继龙");
			bian.setEmployeeNumber("1011");
			bian.setPassword(MD5.generateMD5(bian.getEmployeeNumber()));
			bian.setTitle(new TeacherTitle(TeacherTitleType.LECTURER));
			bian.setPhoneNumber("13274508193");
			bian.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			userDao.persist(bian);
			TeacherInvigilation ibian = new TeacherInvigilation();
			ibian.setUser(bian);
			ibian.setSqecQuantity(sqnum);
			teacherInvigilationDao.persist(ibian);
			TeacherTask tbian = new TeacherTask();
			tbian.setUser(bian);
			tbian.setPoint(point);
			teacherTaskDao.persist(tbian);
			TeacherProject tpbian = new TeacherProject();
			tpbian.setUser(bian);
			teacherProjectDao.persist(tpbian);
			
		}
		
		if (userAuthorityDao.get(UserAuthorityType.STUDENT).getUsers().size() == 0) {
			User xuqingqing = new User();
			xuqingqing.setName("胥清清");
			xuqingqing.setEmployeeNumber("20124628");
			xuqingqing.setPassword(MD5.generateMD5(xuqingqing.getEmployeeNumber()));
			xuqingqing.setUserAuthority(new UserAuthority(UserAuthorityType.STUDENT));
			userDao.persist(xuqingqing);
			StudentProject spxuqingqing = new StudentProject();
			spxuqingqing.setUser(xuqingqing);
			studentProjectDao.persist(spxuqingqing);
			
			User dengqianwen = new User();
			dengqianwen.setName("邓茜文");
			dengqianwen.setEmployeeNumber("20124593");
			dengqianwen.setPassword(MD5.generateMD5(dengqianwen.getEmployeeNumber()));
			dengqianwen.setUserAuthority(new UserAuthority(UserAuthorityType.STUDENT));
			userDao.persist(dengqianwen);
			StudentProject spdengqianwen = new StudentProject();
			spdengqianwen.setUser(dengqianwen);
			studentProjectDao.persist(spdengqianwen);
		}
	}
	
	public void initSpecInviType() {
		if (specialInviTypeDao.list().size()  == 0) {
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
	}
	
	public void initFileType() {
		if (fileTypeDao.list().size() == 0) {
			FileType fnone = new FileType();
			fnone.setName("无格式要求");
			fnone.setType("");
			fileTypeDao.persist(fnone);
			FileType doc = new FileType();
			doc.setName("Word文档");
			doc.setType(".doc,.docx");
			fileTypeDao.persist(doc);
			FileType xls = new FileType();
			xls.setName("Excel表格");
			xls.setType(".xls,.xlsx");
			fileTypeDao.persist(xls);
		}
		
	}
	
	public void initFileTaskStatus() {
		if (fileTaskStatusDao.list().size() == 0) {
			FileTaskStatus started = new FileTaskStatus();
			started.setName("已开启");
			fileTaskStatusDao.persist(started);
			FileTaskStatus expired = new FileTaskStatus();
			expired.setName("已过期");
			fileTaskStatusDao.persist(expired);
			FileTaskStatus closed = new FileTaskStatus();
			closed.setName("已关闭");
			fileTaskStatusDao.persist(closed);
		}
	}
	
	/**
	 * 初始化毕设上传文档类型信息
	 */
	public void initProjectFileType(){
		if (projectFileTypeDao.list().size() == 0) {
			ProjectFileType demonstrationReport = new ProjectFileType();
			demonstrationReport.setName("论证报告");
			projectFileTypeDao.persist(demonstrationReport);
			
			ProjectFileType openningReport = new ProjectFileType();
			openningReport.setName("开题报告");
			projectFileTypeDao.persist(openningReport);
			
			ProjectFileType openDefenseRecord = new ProjectFileType();
			openDefenseRecord.setName("开题答辩记录");
			projectFileTypeDao.persist(openDefenseRecord);
			
			ProjectFileType interimDefenseRecord = new ProjectFileType();
			interimDefenseRecord.setName("中期答辩记录");
			projectFileTypeDao.persist(interimDefenseRecord);
			
			ProjectFileType paperDefenseRecord = new ProjectFileType();
			paperDefenseRecord.setName("论文答辩记录");
			projectFileTypeDao.persist(paperDefenseRecord);
			
			ProjectFileType interimReport = new ProjectFileType();
			interimReport.setName("中期报告");
			projectFileTypeDao.persist(interimReport);
			
			ProjectFileType paper = new ProjectFileType();
			paper.setName("论文");
			projectFileTypeDao.persist(paper);
		}
	}
	
	public SuperAdminService() {
		// TODO Auto-generated constructor stub
	}

}
