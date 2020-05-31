package com.project.two.service;

import java.util.List;
import java.util.Map;

public interface CpService {

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
	
}
