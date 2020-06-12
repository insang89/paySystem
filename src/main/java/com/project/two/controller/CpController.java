package com.project.two.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.two.service.CpService;

@Controller
public class CpController {

	@Resource(name = "Service")
	private CpService cpService;
	
	//로그인 페이지&로그아웃&세션 삭제
	@RequestMapping("loginPage")
	public String loginPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("sessionloginInfo");		
		return "cp/login";
	}//loginPage
	
	//로그인 후 리스트
	//아이디&비밀번호 조회
	//세션에 로그인정보 set
	@RequestMapping("login")
	public String login(@RequestParam Map<String, Object> map
						, Model model
						, HttpServletRequest request
						, HttpSession session) {
	
		Map<String, Object> loginInfo = new HashMap<String, Object>();
		
		loginInfo = cpService.login(map); // 로그인 정보를 담음
		
		if(loginInfo.containsKey("memDrPay") == false) { // 대리결재 권한이 없는경우
			loginInfo.put("memDrPay", "null");
//			loginInfo.put("payDate", ""); // 권한받은 날짜
		}
		else { // 권한이 있는경우 권한받은 날짜를 세션에 넣음 
			Map<String, Object> smemInfo = cpService.payDate(map);
			session.setAttribute("smemInfo", smemInfo);
			System.out.println(smemInfo);
		}
		
		if(loginInfo == null) { // 입력한 아이디가 없을때
			model.addAttribute("idCheck", 1);
			return "cp/login";
		}
		
		String dbPwd = loginInfo.get("memPwd").toString();
		String dbId = loginInfo.get("memId").toString();
		String inputPwd = map.get("pwd").toString();
		
		if(!inputPwd.equals(dbPwd)) { // 암호가 맞지 않을때
			model.addAttribute("pwdCheck", 2);
			model.addAttribute("inputId", dbId);
			return "cp/login";
		}

		else {
			String memLevel = (String)loginInfo.get("memLevel");
			if((memLevel.equals("staff")) || (memLevel.equals("amanager"))) { // 사원(staff) or 대리(amanager) 이면
				session.setAttribute("number", 3);
			}else if((memLevel.equals("manager")) || (memLevel.equals("gmanager"))){ // 과장(manager) or 부장(gmanager) 이면
				session.setAttribute("number", 99);
			}
			
			loginInfo.put("op1",map.get("op1"));
			loginInfo.put("op2",map.get("op2"));
			loginInfo.put("keyword",map.get("keyword"));
			loginInfo.put("data1",map.get("data1"));
			loginInfo.put("data2",map.get("data2"));
			
			session.setAttribute("sessionloginInfo", loginInfo); // 로그인정보를 세션에 담음

			return "redirect:list";
		}
	}//login
	
	//게시판 글목록 조회
	@RequestMapping("list")
	public String list(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		if(session.getAttribute("sessionloginInfo") == null) {
			return "redirect:loginPage";
		}
		if(map.isEmpty()) {
			map.put("curPage", 1);
			map.put("listSize", 10);
		}
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		
		map.put("id", sessionMap.get("memId").toString());
		map.put("seq", sessionMap.get("memSeq").toString());
		map.put("level", sessionMap.get("memLevel").toString());
		map.put("memDrPay", sessionMap.get("memDrPay").toString());
		map.put("setMemId",cpService.setMemId(map));
		
		int totPage = cpService.totalCount(map);
		int curPage = Integer.parseInt(map.get("curPage").toString());
		
		Map<String, Object> map2 = cpService.paging(curPage, totPage); 
		
		map.put("Tcnt", totPage);
		map.put("curPage", map2.get("curPage"));
		map.put("totPage", map2.get("totPage"));
		map.put("BLOCK_SCALE", map2.get("BLOCK_SCALE"));
		map.put("curBlock", map2.get("curBlock"));
		map.put("prevPage", map2.get("prevPage"));
		map.put("blockBegin", map2.get("blockBegin"));
		map.put("blockEnd", map2.get("blockEnd"));
		map.put("totBlock", map2.get("totBlock"));
		map.put("nextPage", map2.get("nextPage"));
		
		model.addAttribute("pageMap", map);
		
		map.put("listSize", 10);
		
		String dLevel = cpService.dLevel(map);
		model.addAttribute("dLevel", dLevel);
		
		List<Map<String, Object>> writeList = new ArrayList<Map<String, Object>>();
		writeList = cpService.writeList(map); // 로그인시 글목록을 불러옴
		model.addAttribute("writeList", writeList);
		System.out.println(map);
		
		Map<String, Object> sMem = cpService.drSmem(map);
		model.addAttribute("sMem", sMem);
		
		if(sMem == null) {
			model.addAttribute("flag", 1);
		}
		
		model.addAttribute("map", map);
		
		return "cp/detail";
	}//list
	
	//결제페이지
	@RequestMapping("paypage")
	public String paypage(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		
		if(session.getAttribute("sessionloginInfo") == null) {
			return "redirect:loginPage";
		}
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		model.addAttribute("session", sessionMap);
		
		Map<String, Object> writeInfo = cpService.writeInfo(map); // 클릭한 글의 정보를 담음
		model.addAttribute("writeInfo", writeInfo);
		
		List<Map<String, Object>> paypageList = cpService.paypageList(map); // 글의 로그를 List에 담음
		model.addAttribute("paypageList", paypageList);
		
		return "cp/paypage";
	}//paypage
	
	//비동기식 리스트 조회
	@RequestMapping("ajaxList")
	public String ajaxList(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		map.put("id", sessionMap.get("memId"));
		map.put("memLevel", sessionMap.get("memLevel"));
		
		List<Map<String, Object>> ajaxSearch = cpService.writeList(map);
		model.addAttribute("ajaxSearch", ajaxSearch);
		return "cp/ajaxList";
	}//ajaxList
	
	//글등록
	@RequestMapping("writeEnroll")
	public String writeEnroll(@RequestParam Map<String, Object> map, HttpSession session) {
		
		int Enroll = cpService.enroll(map);
		System.out.println(Enroll);
		
		int seqCount = cpService.seqCount(); // 시퀀스 번호 조회
		
		map.put("boardSeq", seqCount);
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		String memLevel = sessionMap.get("memLevel").toString();
		String boardPasser = sessionMap.get("memId").toString();		
		
		if((memLevel.equals("staff")) || (memLevel.equals("amanager"))) { // 사원 대리 일때, boardPasser = null
			map.put("boardState", "paywait");
			map.put("boardPasser", null);
		}
		else if(memLevel.equals("manager")) { // 과장 일때, boardPasser = 본인 아이디
			map.put("boardState", "paing");
			map.put("boardPasser", boardPasser);
		}
		else if(memLevel.equals("gmanager")) { // 부장 일때
			map.put("boardState", "paid");
			map.put("boardPasser", boardPasser);
		}
		
		int logInsert = cpService.logInsert(map);
		System.out.println(logInsert);
		
		return "redirect:list";
	}//writeEnroll
	
	//writePage 임시저장(insert)
	@RequestMapping("imsiEnroll")
	public String imsiEnroll(@RequestParam Map<String, Object> map, HttpSession session) {
		
		int imsiEnroll = cpService.imsiEnroll(map); // subject=insert글등록, content=테스트, name=staff111, boardSeq=
		System.out.println(imsiEnroll);
		
		int seqCount = cpService.seqCount(); // 시퀀스 번호 조회
		
		map.put("boardSeq", seqCount);
		map.put("boardState", "imsi");
		map.put("boardUpdate", map.get("boardUpdate"));
		map.put("boardPasser", map.get("boardPasser"));
		map.put("boardWriter", map.get("boardWriter"));
		
		int num2 = cpService.logInsert(map);
		System.out.println(num2);
		
		return "redirect:list";
	}//imsiEnroll
	
	//글쓰기 버튼 클릭시 페이지
	@RequestMapping("writePage")
	public String writePage(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
	
		if(session.getAttribute("sessionloginInfo") == null) {
			return "redirect:loginPage";
		}
		
		int count = cpService.count(); // 시퀀스+1 (새글)
		model.addAttribute("count", count);
		
		return "cp/writePage";
	}
	
	//paypage 임시저장(update)
	@RequestMapping("paypageImsibutton")
	public String paypageImsibutton(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		Map<String, Object> trInfo = cpService.clickTrInfo(map);
		System.out.println(trInfo);
		String state = "imsi";
		map.put("state", state);
		map.put("boardState", state);
		int num = cpService.button(map);
		System.out.println(num);

		map.put("boardUpdate", map.get("boardUpdate"));
		map.put("boardPasser", map.get("boardPasser"));
		map.put("boardWriter", map.get("boardWriter"));
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		map.put("boardWriter", sessionMap.get("memId").toString());
		
		int num2 = cpService.logInsert(map);
		System.out.println(num2);
		
		return "redirect:list";
	}

	//반려버튼
	@RequestMapping("banButton")
	public String banButton(@RequestParam Map<String, Object> map, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		
		String boardSeq = map.get("boardSeq").toString();
		String boardWriter = cpService.wInfo(boardSeq); // boardSeq로 글쓴이 ID가져옴
		map.put("boardState", "ban");
		map.put("boardWriter", boardWriter);
		map.put("boardSeq", map.get("boardSeq"));
		map.put("boardPasser", sessionMap.get("memId").toString());
		map.put("boardUpdate", map.get("boardUpdate"));
		map.put("boardWriter", sessionMap.get("memId").toString());
		
		int num = cpService.banButton(map);
		System.out.println(num);
		int num2 = cpService.logInsert(map);
		System.out.println(num2);
		
		return "redirect:list";
	}
	
	//결재페이지 결재버튼
	@RequestMapping("payButton")
	public String payButton(@RequestParam Map<String, Object> map, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		
		String boardSeq = map.get("boardSeq").toString();
		String boardWriter = cpService.wInfo(boardSeq); // boardSeq로 글쓴이 ID가져옴
		map.put("boardWriter", boardWriter);
		
		String memLevel = sessionMap.get("memLevel").toString();
		String memId = sessionMap.get("memId").toString();
		map.put("memId", memId);
		
		if(("staff".equals(memLevel) || "amanager".equals(memLevel)) && "null".equals(map.get("memDrPay"))) { // 사원 대리이면
			String state = "paywait";
			map.put("state", state);
			int logInsert = cpService.logInsert2(map);
			System.out.println(logInsert);
			int listUpdate = cpService.payButton(map);
			System.out.println(listUpdate);
		}
		
		if("manager".equals(memLevel) || "manager".equals(map.get("memDrPay"))) { // 과장이면
			String state = "paing";
			map.put("state", state);
			int payButton = cpService.payButton(map); // update
			System.out.println(payButton);
			int logInsert = cpService.logInsert2(map); // insert
			System.out.println(logInsert);
			return "redirect:list";
		}
		else if("gmanager".equals(memLevel) || "gmanager".equals(map.get("memDrPay"))) { // 부장이면
			String state = "paid";
			map.put("state", state);
			int payButton = cpService.payButton(map); // update
			System.out.println(payButton);
			int logInsert = cpService.logInsert2(map); // insert
			System.out.println(logInsert);
			return "redirect:list";
		}
		return "redirect:list";
	}
	
	@RequestMapping("dr")
	public String dr(Model model, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		String memName = sessionMap.get("memName").toString();
		String memLevel = sessionMap.get("memLevel").toString();
		
		String kLevel = cpService.kLevel(memLevel);
		
		model.addAttribute("memName", memName);
		model.addAttribute("memLevel", kLevel);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memLevel",memLevel);
		
		List<Map<String, Object>> memList = cpService.memList(map);
		model.addAttribute("memList", memList);
		
		return "cp/dr";
	}
	
	@RequestMapping("drr")
	public String dr(@RequestParam String select, HttpSession session) {

		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		Map<String, Object> map = new HashMap<String, Object>();
		
		String setMemberLevel = sessionMap.get("memLevel").toString();
		String setMemberId = sessionMap.get("memId").toString();
		
		if(setMemberLevel.equals("manager")) { //과장이 권한 주었을때
			map.put("smem","manager");
			map.put("gmem",select);
			int memLevelUpdate = cpService.updateDP(map);
			System.out.println(memLevelUpdate);
			map.put("setMemberId",setMemberId);
			int insertDP = cpService.insertDP(map);
			System.out.println(insertDP);
		}
		
		else if(setMemberLevel.equals("gmanager")) { //부장이 권한 주었을때
			map.put("smem","gmanager");
			map.put("gmem",select);
			int memLevelUpdate = cpService.updateDP(map);
			System.out.println(memLevelUpdate);
			map.put("setMemberId",setMemberId);
			int insertDP = cpService.insertDP(map);
			System.out.println(insertDP);
		}
		
		map.put("gmem", select);
		
		return "redirect:dr";
	}
	
	@RequestMapping(value="drrr", produces = "application/text; charset=utf8") // ajax 한글깨진거 변환
	@ResponseBody // ajax 단일값 보낼때
	public String drrr(@RequestParam Map<String, Object> map) {
		String kLevel2 = cpService.kLevel2(map);
		return kLevel2;
	}
	
}
