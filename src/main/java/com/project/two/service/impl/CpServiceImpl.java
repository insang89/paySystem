package com.project.two.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.project.two.dao.CpDao;
import com.project.two.service.CpService;

@Service("Service")
public class CpServiceImpl implements CpService{

	@Resource(name = "dao")
	private CpDao cpDao;

	@Override
	public Map<String, Object> login(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.login(map);
	}

	@Override
	public List<Map<String, Object>> search(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.search(map);
	}

	@Override
	public List<Map<String, Object>> writeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.writeList(map);
	}

	@Override
	public List<Map<String, Object>> ajaxList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.ajaxList(map);
	}

	@Override
	public int enroll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.enroll(map);
	}

	@Override
	public int imsiEnroll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.imsiEnroll(map);
	}

	@Override
	public Map<String, Object> writeInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.writeInfo(map);
	}

	@Override
	public int button(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.button(map);
	}

	@Override
	public int logInsert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.logInsert(map);
	}

	@Override
	public Map<String, Object> clickTrInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.clickTrInfo(map);
	}

	@Override
	public List<Map<String, Object>> paypageList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.paypageList(map);
	}

	@Override
	public int seqCount() {
		// TODO Auto-generated method stub
		return cpDao.seqCount();
	}

	@Override
	public int logInsert2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.logInsert2(map);
	}

	@Override
	public String wInfo(String boardSeq) {
		// TODO Auto-generated method stub
		return cpDao.wInfo(boardSeq);
	}

	@Override
	public int payButton(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.payButton(map);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return cpDao.count();
	}

	@Override
	public int banButton(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.banButton(map);
	}

	@Override
	public List<Map<String, Object>> list2() {
		// TODO Auto-generated method stub
		return cpDao.list2();
	}

	@Override
	public String kLevel(String memLevel) {
		// TODO Auto-generated method stub
		return cpDao.kLevel(memLevel);
	}

	@Override
	public List<Map<String, Object>> memList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.memList(map);
	}

	@Override
	public String kLevel2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.kLevel2(map);
	}

	@Override
	public int insertDP(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.insertDP(map);
	}

	@Override
	public String dLevel(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.dLevel(map);
	}

	@Override
	public Map<String, Object> drSmem(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.drSmem(map);
	}

	@Override
	public int updateDP(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.updateDP(map);
	}

	@Override
	public Map<String, Object> payDate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cpDao.payDate(map);
	}
	
}
