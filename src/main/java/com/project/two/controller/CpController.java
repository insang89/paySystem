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
import org.springframework.web.bind.annotation.RequestMethod;
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
	public String login(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
	
		Map<String, Object> loginInfo = cpService.login(map); // 로그인한 사람의 정보를 담음
		
		if(loginInfo != null) { // 로그인을 했으면
			if(loginInfo.containsKey("memDrPay") == false ) { // 대리결재 권한이 없는경우
				loginInfo.put("memDrPay", "null");
			}
		}
		else { // 대리결재 권한이 있는경우 권한받은 날짜를 세션에 넣음 
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

		else { // 로그인 성공 하면
			String memLevel = (String)loginInfo.get("memLevel");
			if((memLevel.equals("staff")) || (memLevel.equals("amanager"))) { // 사원(staff) or 대리(amanager) 이면 3를 넘기겠다.
				session.setAttribute("number", 3);
			}else if((memLevel.equals("manager")) || (memLevel.equals("gmanager"))){ // 과장(manager) or 부장(gmanager) 이면 99를 넘기겠다.
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
	
	//게시판 글목록 조회 + ajax + search
	@RequestMapping("list")
	public String list(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		if(session.getAttribute("sessionloginInfo") == null) { // 세션이 비어있으면 list에 접근 못하게 막아주기.
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
		
		int totPage = cpService.totalCount(map); // 보여야할 글의 총갯수
		
		if(totPage == 0) { // 글이 없을때 화면처리
			model.addAttribute("ck", "true");
		}
		
		List<Map<String, Object>> seqListMap = cpService.seqList(map); // 로그인한 사원이 보는 글들의 seq번호
		List<Integer> seqList = new ArrayList<Integer>();
		
		for(int i=0; i<seqListMap.size(); i++) {
			seqList.add(Integer.parseInt(seqListMap.get(i).get("boardSeq").toString()));
		}

		session.setAttribute("seqList", seqList);
		
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
		
		String dLevel = cpService.dLevel(map); // 대리결재 권한을 준 직원의 직급
		model.addAttribute("dLevel", dLevel);
		
		List<Map<String, Object>> writeList = cpService.writeList(map); // 로그인시 글목록 리스트 조회
		
		model.addAttribute("writeList", writeList);
		System.out.println(map);
		
		Map<String, Object> sMem = cpService.drSmem(map); // 대리결재 권한을 준 직원
		model.addAttribute("sMem", sMem);
		
		if(sMem == null) { // 대리결재 권한이 없으면
			model.addAttribute("flag", 1);
		}
		
		model.addAttribute("map", map);
		
		return "cp/detail";
	}//list
	
	//결제페이지
	@RequestMapping("paypage")
	public String paypage(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		
		if(session.getAttribute("sessionloginInfo") == null) { // 세션이 null이면 결재페이지 접근불가
			return "redirect:loginPage";
		}
		
		List<Integer> seqList = (List<Integer>)session.getAttribute("seqList"); // 내가 볼 수 있는 글들의 시퀀스를 세션에서 가져옴
		String check = "false";
		
		for(int i=0; i<seqList.size(); i++) {
				if(Integer.parseInt(map.get("seq").toString()) == seqList.get(i)) { // 내가 보려는 글의 시퀀스번호와 볼 수 있는 글의 시퀀스번호를 비교 
					check="true";
					break;
				}	
		}

		if(check!="true") { // 글을 볼 수 있는 권한이 없다면 리턴시키겠다.
			return "redirect:list";
		}
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo"); // 세션에 담긴 정보를 맵에 담겠다.
		model.addAttribute("session", sessionMap);
		
		Map<String, Object> writeInfo = cpService.writeInfo(map); // 클릭한 글의 정보를 담음
		model.addAttribute("writeInfo", writeInfo);
		
		List<Map<String, Object>> paypageList = cpService.paypageList(map); // 글의 로그를 List에 담음
		model.addAttribute("paypageList", paypageList);		
		
		return "cp/paypage";
	}//paypage
	
	//글등록
	@RequestMapping("writeEnroll")
	public String writeEnroll(@RequestParam Map<String, Object> map, HttpSession session) {
		
		int Enroll = cpService.enroll(map); // 글쓰기 insert
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
		else if(memLevel.equals("gmanager")) { // 부장 일때, boardPasser = 본인 아이디
			map.put("boardState", "paid");
			map.put("boardPasser", boardPasser);
		}
		
		int logInsert = cpService.logInsert(map); // 임시저장, 결재, 반려 이벤트 발생시 항상 log insert
		System.out.println(logInsert);
		
		return "redirect:list";
	}//writeEnroll
	
	//writePage 임시저장(insert)
	@RequestMapping("imsiEnroll")
	public String imsiEnroll(@RequestParam Map<String, Object> map, HttpSession session) {
		
		int imsiEnroll = cpService.imsiEnroll(map); // 임시저장 insert
		System.out.println(imsiEnroll);
		
		int seqCount = cpService.seqCount(); // 시퀀스 번호 조회
		
		map.put("boardSeq", seqCount);
		map.put("boardState", "imsi");
		map.put("boardUpdate", map.get("boardUpdate"));
		map.put("boardPasser", map.get("boardPasser"));
		map.put("boardWriter", map.get("boardWriter"));
		
		int num2 = cpService.logInsert(map); // 임시저장, 결재, 반려 이벤트 발생시 항상 log insert
		System.out.println(num2);
		
		return "redirect:list";
	}//imsiEnroll
	
	//글쓰기 버튼 클릭시 페이지
	@RequestMapping("writePage")
	public String writePage(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
	
		if(session.getAttribute("sessionloginInfo") == null) { // 세션이 null이면 페이지 접근막기.
			return "redirect:loginPage";
		}
		
		int count = cpService.count(); // 시퀀스+1 (새글)
		model.addAttribute("count", count);
		
		return "cp/writePage";
	}
	
	//paypage 임시저장(update)
	@RequestMapping("paypageImsibutton")
	public String paypageImsibutton(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		Map<String, Object> trInfo = cpService.clickTrInfo(map); // html tr 클릭한 글의 정보조회
		System.out.println(trInfo);
		String state = "imsi";
		map.put("state", state);
		map.put("boardState", state);
		int num = cpService.button(map); // 임시저장글에서 임시저장을 또 누르면 update
		System.out.println(num);

		map.put("boardUpdate", map.get("boardUpdate"));
		map.put("boardPasser", map.get("boardPasser"));
		map.put("boardWriter", map.get("boardWriter"));
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		map.put("boardWriter", sessionMap.get("memId").toString());
		
		int num2 = cpService.logInsert(map); // 임시저장, 결재, 반려 이벤트 발생시 항상 log insert
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
		
		int num = cpService.banButton(map); // 반려버튼 클릭. 글상태 update
		System.out.println(num);
		int num2 = cpService.logInsert(map); // 임시저장, 결재, 반려 이벤트 발생시 항상 log insert
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
		
		if(("staff".equals(memLevel) || "amanager".equals(memLevel)) && "null".equals(map.get("memDrPay"))) { // 사원&대리 일때 boardState = 'paywait' 
			String state = "paywait";
			map.put("state", state);
			int logInsert = cpService.logInsert2(map); // insert
			System.out.println(logInsert);
			int listUpdate = cpService.payButton(map); // update
			System.out.println(listUpdate);
		}
		
		if("manager".equals(memLevel) || "manager".equals(map.get("memDrPay"))) { // 과장 일때 boardState = 'paing'
			String state = "paing";
			map.put("state", state);
			int payButton = cpService.payButton(map); // update
			System.out.println(payButton);
			int logInsert = cpService.logInsert2(map); // insert
			System.out.println(logInsert);
			return "redirect:list";
		}
		else if("gmanager".equals(memLevel) || "gmanager".equals(map.get("memDrPay"))) { // 부장 일때

			if("imsi".equals(map.get("boardStateEN")) || "ban".equals(map.get("boardStateEN"))) { // 글상태가 임시or반려 일때
				
				if("gmanager".equals(map.get("memDrPay"))) { // 대리결재 직급이 부장일때
					map.put("state", "paywait");
					map.put("memDrPay", "null");
				}
				else { // 대리결재 직급이 부장이 아닐때
					map.put("state", "paid");
				}
				
			}
			else if("paing".equals(map.get("boardStateEN"))) { // 글상태가 결재중일때
				map.put("state", "paid");
			}
			int payButton = cpService.payButton(map); // update
			System.out.println(payButton);
			int logInsert = cpService.logInsert2(map); // insert
			System.out.println(logInsert);
		}
		return "redirect:list";
	}
	
	@RequestMapping("dr")
	public String dr(Model model, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		String memName = sessionMap.get("memName").toString();
		String memLevel = sessionMap.get("memLevel").toString();
		
		String kLevel = cpService.kLevel(memLevel); // 로그인한 사람의 권한 불러오기
		
		model.addAttribute("memName", memName);
		model.addAttribute("memLevel", kLevel);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memLevel",memLevel);
		
		List<Map<String, Object>> memList = cpService.memList(map); // 사원들의 mem_id, mem_name 리스트 가져오기
		model.addAttribute("memList", memList);
		
		return "cp/dr";
	}
	
	@RequestMapping(value="drr",method = RequestMethod.POST)
	public String drr(@RequestParam String select, HttpSession session) {

		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		Map<String, Object> map = new HashMap<String, Object>();
		
		String setMemberLevel = sessionMap.get("memLevel").toString();
		String setMemberId = sessionMap.get("memId").toString();
		
		if(setMemberLevel.equals("manager")) { //과장이 권한 주었을때
			map.put("smem","manager");
			map.put("gmem",select);

				int memLevelUpdate = cpService.updateDP(map); // member테이블 권한컬럼(mem_drpay) update
				System.out.println(memLevelUpdate);
				map.put("setMemberId",setMemberId);
				int insertDP = cpService.insertDP(map); // 대리결재 테이블에 권한준 직원, 권한 받은 사원, 날짜 insert
				System.out.println(insertDP);

		}
		
		else if(setMemberLevel.equals("gmanager")) { //부장이 권한 주었을때
			map.put("smem","gmanager");
			map.put("gmem",select);
			
				int memLevelUpdate = cpService.updateDP(map); // member테이블 권한컬럼(mem_drpay) update
				System.out.println(memLevelUpdate);
				map.put("setMemberId",setMemberId);
				int insertDP = cpService.insertDP(map); // 대리결재 테이블에 권한준 직원, 권한 받은 사원, 날짜 insert
				System.out.println(insertDP);
		}
		
		map.put("gmem", select);
		
		return "redirect:dr";
	}
	
	@RequestMapping(value="drrr", produces = "application/text; charset=utf8") // ajax 한글깨진거 변환
	@ResponseBody // ajax 단일값 보낼때
	public String drrr(@RequestParam Map<String, Object> map) {
		String kLevel2 = cpService.kLevel2(map); // ajax 선택된 사원의 직급 조회
		return kLevel2;
	}
	
}
