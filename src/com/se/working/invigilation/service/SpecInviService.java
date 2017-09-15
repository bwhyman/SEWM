package com.se.working.invigilation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.invigilation.dao.SpecialInviDao;
import com.se.working.invigilation.dao.SpecialInviInfoDao;
import com.se.working.invigilation.dao.SpecialInviTypeDao;
import com.se.working.invigilation.dao.TeacherInviDao;
import com.se.working.invigilation.entity.SpecialInvigilation;
import com.se.working.invigilation.entity.SpecialInvigilationInfo;
import com.se.working.invigilation.entity.SpecialInvigilationType;
import com.se.working.invigilation.entity.TeacherInvigilation;

@Service
@Transactional
public class SpecInviService {

	@Autowired
	private SpecialInviTypeDao specTypeDao;
	@Autowired
	private SpecialInviDao specInviDao;
	@Autowired
	private SpecialInviInfoDao specInfoDao;
	@Autowired
	private TeacherInviDao teacherInviDao;
	/**
	 * 查找全部特殊监考类
	 * @return
	 */
	public List<SpecialInvigilationType> findSpecTypes() {
		return specTypeDao.find();
	}
	
	public List<TeacherInvigilation> findTeacherOrderBySpecNumber() {
		return teacherInviDao.listOrderBySpecNumber();
	}
	
	/**
	 * 添加特殊监考与分配
	 * @param info
	 * @param checkeds
	 */
	public void addSpecInfo(SpecialInvigilationInfo info, long[] checkeds) {
		System.out.println(info.getLocation());
		specInfoDao.persist(info);
		for (int i = 0; i < checkeds.length; i++) {
			TeacherInvigilation t = teacherInviDao.find(checkeds[i]);
			t.setSqecQuantity(t.getSqecQuantity() + 1);
			SpecialInvigilation sp = new SpecialInvigilation();
			sp.setSpecInv(info);
			sp.setTeacher(t);
			specInviDao.persist(sp);
		}
		
	}
	
	public SpecInviService() {
		// TODO Auto-generated constructor stub
	}

}
