package com.project.two.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CpDao {

	Map<String, Object> login(Map<String, Object> map);

	List<Map<String, Object>> search(Map<String, Object> map);

	List<Map<String, Object>> writeList(Map<String, Object> map);

	List<Map<String, Object>> ajaxList(Map<String, Object> map);

	int enroll(Map<String, Object> map);

	int imsiEnroll(Map<String, Object> map);

	Map<String, Object> writeInfo(Map<String, Object> map);

	int button(Map<String, Object> map);

	int logInsert(Map<String, Object> map);

	Map<String, Object> clickTrInfo(Map<String, Object> map);

	List<Map<String, Object>> paypageList(Map<String, Object> map);

	int seqCount();

	int logInsert2(Map<String, Object> map);

	String wInfo(String boardSeq);

	int payButton(Map<String, Object> map);

	int count();

	int banButton(Map<String, Object> map);

	List<Map<String, Object>> list2();

	String kLevel(String memLevel);

	List<Map<String, Object>> memList(Map<String, Object> map);

	String kLevel2(Map<String, Object> map);

	int insertDP(Map<String, Object> map);

	String dLevel(Map<String, Object> map);

	Map<String, Object> drSmem(Map<String, Object> map);

	int updateDP(Map<String, Object> map);

	Map<String, Object> payDate(Map<String, Object> map);

	int totalCount(Map<String, Object> map);

	String setMemId(Map<String, Object> map);

	List<Map<String, Object>> seqList(Map<String, Object> map);

	int viewCount(int seq);
	
}
