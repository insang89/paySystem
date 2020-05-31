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

import com.project.two.service.CpService;

@Controller
public class CpController {

	@Resource(name = "Service")
	private CpService cpService;
	
	//�α��� ������
	@RequestMapping("loginPage")
	public String loginPage() {
		return "cp/login";
	}//loginPage
	
	//�α׾ƿ� & ���� ����
	@RequestMapping("logOut")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("sessionloginInfo");
		return "cp/login";
	}
	
	//�α��� �� ����Ʈ
	//���̵�&��й�ȣ ��ȸ
	//���ǿ� �α������� set
	@RequestMapping("login")
	public String login(@RequestParam Map<String, Object> map
						, Model model
						, HttpServletRequest request
						, HttpSession session) {
	
		Map<String, Object> loginInfo = new HashMap<String, Object>();
		loginInfo = cpService.login(map); // �α��� ������ ����

		if(loginInfo == null) { // �Է��� ���̵� ������
			model.addAttribute("idCheck", 1);
			return "cp/login";
		}
		
		String dbPwd = loginInfo.get("memPwd").toString();
		String dbId = loginInfo.get("memId").toString();
		String inputPwd = map.get("pwd").toString();
		
		if(!inputPwd.equals(dbPwd)) { // ��ȣ�� ���� ������
			model.addAttribute("pwdCheck", 2);
			model.addAttribute("inputId", dbId);
			return "cp/login";
		}

		else {
			String memLevel = (String)loginInfo.get("memLevel");
			if((memLevel.equals("staff")) || (memLevel.equals("amanager"))) { // ���(staff) or �븮(amanager) �̸�
				session.setAttribute("number", 3);
			}else if((memLevel.equals("manager")) || (memLevel.equals("gmanager"))){ // ����(manager) or ����(gmanager) �̸�
				session.setAttribute("number", 99);
			}
			
			session.setAttribute("sessionloginInfo", loginInfo); // �α��������� ���ǿ� ����
			
			return "redirect:list";
		}
	}//login
	
	//�Խ��� �۸�� ��ȸ
	@RequestMapping("list")
	public String list(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		if (map.isEmpty()) {
			map.put("curPage", 1);
			map.put("listSize", 10);
		}
		
		if(session.getAttribute("sessionloginInfo") == null) {
			return "cp/login";
		}
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		
		map.put("id", sessionMap.get("memId").toString());
		map.put("seq", sessionMap.get("memSeq").toString());
		map.put("level", sessionMap.get("memLevel").toString());
		
		List<Map<String, Object>> writeList = new ArrayList<Map<String, Object>>();
		writeList = cpService.writeList(map); // �α��ν� �۸���� �ҷ���
		model.addAttribute("writeList", writeList);
		System.out.println(map);
		
		return "cp/detail";
	}//list
	
	//�˻�
	@RequestMapping("search")
	public String search(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		if (map.isEmpty()) {
			map.put("curPage", 1);
			map.put("listSize", 10);
		}
		
		List<Map<String, Object>> searchList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		
		map.put("id", sessionMap.get("memId").toString());
		
		searchList = cpService.search(map); // �˻��� �۸���� �ҷ���
		model.addAttribute("searchList", searchList);
		model.addAttribute("map", map);
		
		return "cp/detail";
	}//search
	
	//����������
	@RequestMapping("paypage")
	public String paypage(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		model.addAttribute("session", sessionMap);
		
		Map<String, Object> writeInfo = cpService.writeInfo(map); // Ŭ���� ���� ������ ����
		model.addAttribute("writeInfo", writeInfo);
		
		List<Map<String, Object>> paypageList = cpService.paypageList(map); // ���� �α׸� List�� ����
		model.addAttribute("paypageList", paypageList);
		
		return "cp/paypage";
	}//paypage
	
	//�񵿱�� ����Ʈ ��ȸ
	@RequestMapping("ajaxList")
	public String ajaxList(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		map.put("id", sessionMap.get("memId"));
		map.put("memLevel", sessionMap.get("memLevel"));
		
		List<Map<String, Object>> ajaxSearch = cpService.ajaxList(map);
		model.addAttribute("ajaxSearch", ajaxSearch);
		return "cp/ajaxList";
	}//ajaxList
	
	//�۵��
	@RequestMapping("writeEnroll")
	public String writeEnroll(@RequestParam Map<String, Object> map, HttpSession session) {
		int Enroll = cpService.enroll(map);
		System.out.println(Enroll);
		
		int seqCount = cpService.seqCount(); // ������ ��ȣ ��ȸ
		
		map.put("boardSeq", seqCount);
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		String memLevel = sessionMap.get("memLevel").toString();
		String boardPasser = sessionMap.get("memId").toString();		
		
		if((memLevel.equals("staff")) || (memLevel.equals("amanager"))) { // ��� �븮 �϶�, boardPasser = null
			map.put("boardState", "paywait");
			map.put("boardPasser", null);
		}
		else if(memLevel.equals("manager")) { // ���� �϶�, boardPasser = ���� ���̵�
			map.put("boardState", "paing");
			map.put("boardPasser", boardPasser);
		}
		else if(memLevel.equals("gmanager")) { // ���� �϶�
			map.put("boardState", "paid");
			map.put("boardPasser", boardPasser);
		}
		
		int logInsert = cpService.logInsert(map);
		System.out.println(logInsert);
		
		return "redirect:list";
	}//writeEnroll
	
	//writePage �ӽ�����(insert)
	@RequestMapping("imsiEnroll")
	public String imsiEnroll(@RequestParam Map<String, Object> map, HttpSession session) {
		
		int imsiEnroll = cpService.imsiEnroll(map); // subject=insert�۵��, content=�׽�Ʈ, name=staff111, boardSeq=
		System.out.println(imsiEnroll);
		
		int seqCount = cpService.seqCount(); // ������ ��ȣ ��ȸ
		
		map.put("boardSeq", seqCount);
		map.put("boardState", "imsi");
		map.put("boardUpdate", map.get("boardUpdate"));
		map.put("boardPasser", map.get("boardPasser"));
		map.put("boardWriter", map.get("boardWriter"));
		
		int num2 = cpService.logInsert(map);
		System.out.println(num2);
		
		return "redirect:list";
	}//imsiEnroll
	
	//�۾��� ��ư Ŭ���� ������
	@RequestMapping("writePage")
	public String writePage(@RequestParam Map<String, Object> map, Model model) {
		
		int count = cpService.count(); // ������+1 (����)
		model.addAttribute("count", count);
		
		return "cp/writePage";
	}
	
	//paypage �ӽ�����(update)
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

	//�ݷ���ư
	@RequestMapping("banButton")
	public String banButton(@RequestParam Map<String, Object> map, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		
		String boardSeq = map.get("boardSeq").toString();
		String boardWriter = cpService.wInfo(boardSeq); // boardSeq�� �۾��� ID������
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
	
	//���������� �����ư
	@RequestMapping("payButton")
	public String payButton(@RequestParam Map<String, Object> map, HttpSession session) {
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		
		String boardSeq = map.get("boardSeq").toString();
		String boardWriter = cpService.wInfo(boardSeq); // boardSeq�� �۾��� ID������
		map.put("boardWriter", boardWriter);
		
		String memLevel = sessionMap.get("memLevel").toString();
		String memId = sessionMap.get("memId").toString();
		map.put("memId", memId);
		
		if("staff".equals(memLevel) || "amanager".equals(memLevel)) { // ��� �븮�̸�
			String state = "paywait";
			map.put("state", state);
			int logInsert = cpService.logInsert2(map);
			System.out.println(logInsert);
			int listUpdate = cpService.payButton(map);
			System.out.println(listUpdate);
			
		}
		
		if("manager".equals(memLevel)) { // �����̸�
			String state = "paing";
			map.put("state", state);
			int payButton = cpService.payButton(map); // update
			System.out.println(payButton);
			int logInsert = cpService.logInsert2(map); // insert
			System.out.println(logInsert);
			return "redirect:list";
		}
		else if("gmanager".equals(memLevel)) { // �����̸�
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
	public String dr() {
		return "cp/dr";
	}
	
	@RequestMapping("drr")
	public String dr(@RequestParam String select) {
//		String memLevel = cpService.drPay(select);
//		model.addAttribute("memLevel", memLevel);
		return "cp/dr";
	}
	
}
