package com.se.working.invigilation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.invigilation.entity.Invigilation;
import com.se.working.service.GenericService;

/**
 * 监考相关业务逻辑处理
 * @author BO
 *
 */
@Service
@Transactional
public class InviService extends GenericService<Invigilation, Long>{

}
