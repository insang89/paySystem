package com.project.two.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.two.dao.CpDao;

@Repository("dao")
public class CpDaoImpl implements CpDao{

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public Map<String, Object> login(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.loginInfo", map);
	}

	@Override
	public List<Map<String, Object>> search(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mapper.searchList", map);
	}

	@Override
	public List<Map<String, Object>> writeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mapper.writeList", map);
	}

	@Override
	public List<Map<String, Object>> ajaxList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mapper.ajaxList", map);
	}

	@Override
	public int enroll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.insert("mapper.enroll", map);
	}

	@Override
	public int imsiEnroll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.insert("mapper.imsiEnroll", map);
	}

	@Override
	public Map<String, Object> writeInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.writeInfo", map);
	}

	@Override
	public int button(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.update("mapper.button", map);
	}

	@Override
	public int logInsert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.insert("mapper.logInsert", map);
	}

	@Override
	public Map<String, Object> clickTrInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.clickTrInfo", map);
	}

	@Override
	public List<Map<String, Object>> paypageList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mapper.paypageList", map);
	}

	@Override
	public int seqCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.seqCount");
	}

	@Override
	public int logInsert2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.insert("mapper.logInsert2", map);
	}

	@Override
	public String wInfo(String boardSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.wInfo", boardSeq);
	}

	@Override
	public int payButton(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.update("mapper.payButton", map);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.count");
	}

	@Override
	public int banButton(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.update("mapper.banButton", map);
	}

	@Override
	public List<Map<String, Object>> list2() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mapper.writeList");
	}

	@Override
	public String kLevel(String memLevel) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.kLevel", memLevel);
	}

	@Override
	public List<Map<String, Object>> memList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mapper.memList", map);
	}

	@Override
	public String kLevel2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.kLevel2", map);
	}

	@Override
	public int insertDP(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.insert("mapper.insertDP", map);
	}

	@Override
	public String dLevel(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.dLevel", map);
	}

	@Override
	public Map<String, Object> drSmem(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.drSmem", map);
	}

	@Override
	public int updateDP(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.update("mapper.updateDP", map);
	}

	@Override
	public Map<String, Object> payDate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.payDate", map);
	}

	@Override
	public int totalCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.totalCount", map);
	}

	@Override
	public String setMemId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.setMemId", map);
	}
	
}
