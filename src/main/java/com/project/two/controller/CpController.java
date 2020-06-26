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
	
	//�α��� ������&�α׾ƿ�&���� ����
	@RequestMapping("loginPage")
	public String loginPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("sessionloginInfo");		
		return "cp/login";
	}//loginPage
	
	//�α��� �� ����Ʈ
	//���̵�&��й�ȣ ��ȸ
	//���ǿ� �α������� set
	@RequestMapping("login")
	public String login(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
	
		Map<String, Object> loginInfo = cpService.login(map); // �α����� ����� ������ ����
		
		if(loginInfo != null) { // �α����� ������
			if(loginInfo.containsKey("memDrPay") == false ) { // �븮���� ������ ���°��
				loginInfo.put("memDrPay", "null");
			}
		}
		else { // �븮���� ������ �ִ°�� ���ѹ��� ��¥�� ���ǿ� ���� 
			Map<String, Object> smemInfo = cpService.payDate(map);
			session.setAttribute("smemInfo", smemInfo);
			System.out.println(smemInfo);
		}
		
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

		else { // �α��� ���� �ϸ�
			String memLevel = (String)loginInfo.get("memLevel");
			if((memLevel.equals("staff")) || (memLevel.equals("amanager"))) { // ���(staff) or �븮(amanager) �̸� 3�� �ѱ�ڴ�.
				session.setAttribute("number", 3);
			}else if((memLevel.equals("manager")) || (memLevel.equals("gmanager"))){ // ����(manager) or ����(gmanager) �̸� 99�� �ѱ�ڴ�.
				session.setAttribute("number", 99);
			}
			
			loginInfo.put("op1",map.get("op1"));
			loginInfo.put("op2",map.get("op2"));
			loginInfo.put("keyword",map.get("keyword"));
			loginInfo.put("data1",map.get("data1"));
			loginInfo.put("data2",map.get("data2"));
			
			session.setAttribute("sessionloginInfo", loginInfo); // �α��������� ���ǿ� ����

			return "redirect:list";
		}
	}//login
	
	//�Խ��� �۸�� ��ȸ + ajax + search
	@RequestMapping("list")
	public String list(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		if(session.getAttribute("sessionloginInfo") == null) { // ������ ��������� list�� ���� ���ϰ� �����ֱ�.
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
		
		int totPage = cpService.totalCount(map); // �������� ���� �Ѱ���
		
		if(totPage == 0) { // ���� ������ ȭ��ó��
			model.addAttribute("ck", "true");
		}
		
		List<Map<String, Object>> seqListMap = cpService.seqList(map); // �α����� ����� ���� �۵��� seq��ȣ
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
		
		String dLevel = cpService.dLevel(map); // �븮���� ������ �� ������ ����
		model.addAttribute("dLevel", dLevel);
		
		List<Map<String, Object>> writeList = cpService.writeList(map); // �α��ν� �۸�� ����Ʈ ��ȸ
		
		model.addAttribute("writeList", writeList);
		System.out.println(map);
		
		Map<String, Object> sMem = cpService.drSmem(map); // �븮���� ������ �� ����
		model.addAttribute("sMem", sMem);
		
		if(sMem == null) { // �븮���� ������ ������
			model.addAttribute("flag", 1);
		}
		
		model.addAttribute("map", map);
		
		return "cp/detail";
	}//list
	
	//����������
	@RequestMapping("paypage")
	public String paypage(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		
		if(session.getAttribute("sessionloginInfo") == null) { // ������ null�̸� ���������� ���ٺҰ�
			return "redirect:loginPage";
		}
		
		List<Integer> seqList = (List<Integer>)session.getAttribute("seqList"); // ���� �� �� �ִ� �۵��� �������� ���ǿ��� ������
		String check = "false";
		
		for(int i=0; i<seqList.size(); i++) {
				if(Integer.parseInt(map.get("seq").toString()) == seqList.get(i)) { // ���� ������ ���� ��������ȣ�� �� �� �ִ� ���� ��������ȣ�� �� 
					check="true";
					break;
				}	
		}

		if(check!="true") { // ���� �� �� �ִ� ������ ���ٸ� ���Ͻ�Ű�ڴ�.
			return "redirect:list";
		}
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo"); // ���ǿ� ��� ������ �ʿ� ��ڴ�.
		model.addAttribute("session", sessionMap);
		
		Map<String, Object> writeInfo = cpService.writeInfo(map); // Ŭ���� ���� ������ ����
		model.addAttribute("writeInfo", writeInfo);
		
		List<Map<String, Object>> paypageList = cpService.paypageList(map); // ���� �α׸� List�� ����
		model.addAttribute("paypageList", paypageList);		
		
		return "cp/paypage";
	}//paypage
	
	//�۵��
	@RequestMapping("writeEnroll")
	public String writeEnroll(@RequestParam Map<String, Object> map, HttpSession session) {
		
		int Enroll = cpService.enroll(map); // �۾��� insert
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
		else if(memLevel.equals("gmanager")) { // ���� �϶�, boardPasser = ���� ���̵�
			map.put("boardState", "paid");
			map.put("boardPasser", boardPasser);
		}
		
		int logInsert = cpService.logInsert(map); // �ӽ�����, ����, �ݷ� �̺�Ʈ �߻��� �׻� log insert
		System.out.println(logInsert);
		
		return "redirect:list";
	}//writeEnroll
	
	//writePage �ӽ�����(insert)
	@RequestMapping("imsiEnroll")
	public String imsiEnroll(@RequestParam Map<String, Object> map, HttpSession session) {
		
		int imsiEnroll = cpService.imsiEnroll(map); // �ӽ����� insert
		System.out.println(imsiEnroll);
		
		int seqCount = cpService.seqCount(); // ������ ��ȣ ��ȸ
		
		map.put("boardSeq", seqCount);
		map.put("boardState", "imsi");
		map.put("boardUpdate", map.get("boardUpdate"));
		map.put("boardPasser", map.get("boardPasser"));
		map.put("boardWriter", map.get("boardWriter"));
		
		int num2 = cpService.logInsert(map); // �ӽ�����, ����, �ݷ� �̺�Ʈ �߻��� �׻� log insert
		System.out.println(num2);
		
		return "redirect:list";
	}//imsiEnroll
	
	//�۾��� ��ư Ŭ���� ������
	@RequestMapping("writePage")
	public String writePage(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
	
		if(session.getAttribute("sessionloginInfo") == null) { // ������ null�̸� ������ ���ٸ���.
			return "redirect:loginPage";
		}
		
		int count = cpService.count(); // ������+1 (����)
		model.addAttribute("count", count);
		
		return "cp/writePage";
	}
	
	//paypage �ӽ�����(update)
	@RequestMapping("paypageImsibutton")
	public String paypageImsibutton(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		Map<String, Object> trInfo = cpService.clickTrInfo(map); // html tr Ŭ���� ���� ������ȸ
		System.out.println(trInfo);
		String state = "imsi";
		map.put("state", state);
		map.put("boardState", state);
		int num = cpService.button(map); // �ӽ�����ۿ��� �ӽ������� �� ������ update
		System.out.println(num);

		map.put("boardUpdate", map.get("boardUpdate"));
		map.put("boardPasser", map.get("boardPasser"));
		map.put("boardWriter", map.get("boardWriter"));
		
		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		map.put("boardWriter", sessionMap.get("memId").toString());
		
		int num2 = cpService.logInsert(map); // �ӽ�����, ����, �ݷ� �̺�Ʈ �߻��� �׻� log insert
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
		
		int num = cpService.banButton(map); // �ݷ���ư Ŭ��. �ۻ��� update
		System.out.println(num);
		int num2 = cpService.logInsert(map); // �ӽ�����, ����, �ݷ� �̺�Ʈ �߻��� �׻� log insert
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
		
		if(("staff".equals(memLevel) || "amanager".equals(memLevel)) && "null".equals(map.get("memDrPay"))) { // ���&�븮 �϶� boardState = 'paywait' 
			String state = "paywait";
			map.put("state", state);
			int logInsert = cpService.logInsert2(map); // insert
			System.out.println(logInsert);
			int listUpdate = cpService.payButton(map); // update
			System.out.println(listUpdate);
		}
		
		if("manager".equals(memLevel) || "manager".equals(map.get("memDrPay"))) { // ���� �϶� boardState = 'paing'
			String state = "paing";
			map.put("state", state);
			int payButton = cpService.payButton(map); // update
			System.out.println(payButton);
			int logInsert = cpService.logInsert2(map); // insert
			System.out.println(logInsert);
			return "redirect:list";
		}
		else if("gmanager".equals(memLevel) || "gmanager".equals(map.get("memDrPay"))) { // ���� �϶�

			if("imsi".equals(map.get("boardStateEN")) || "ban".equals(map.get("boardStateEN"))) { // �ۻ��°� �ӽ�or�ݷ� �϶�
				
				if("gmanager".equals(map.get("memDrPay"))) { // �븮���� ������ �����϶�
					map.put("state", "paywait");
					map.put("memDrPay", "null");
				}
				else { // �븮���� ������ ������ �ƴҶ�
					map.put("state", "paid");
				}
				
			}
			else if("paing".equals(map.get("boardStateEN"))) { // �ۻ��°� �������϶�
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
		
		String kLevel = cpService.kLevel(memLevel); // �α����� ����� ���� �ҷ�����
		
		model.addAttribute("memName", memName);
		model.addAttribute("memLevel", kLevel);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memLevel",memLevel);
		
		List<Map<String, Object>> memList = cpService.memList(map); // ������� mem_id, mem_name ����Ʈ ��������
		model.addAttribute("memList", memList);
		
		return "cp/dr";
	}
	
	@RequestMapping(value="drr",method = RequestMethod.POST)
	public String drr(@RequestParam String select, HttpSession session) {

		Map<String, Object> sessionMap = (Map<String, Object>)session.getAttribute("sessionloginInfo");
		Map<String, Object> map = new HashMap<String, Object>();
		
		String setMemberLevel = sessionMap.get("memLevel").toString();
		String setMemberId = sessionMap.get("memId").toString();
		
		if(setMemberLevel.equals("manager")) { //������ ���� �־�����
			map.put("smem","manager");
			map.put("gmem",select);

				int memLevelUpdate = cpService.updateDP(map); // member���̺� �����÷�(mem_drpay) update
				System.out.println(memLevelUpdate);
				map.put("setMemberId",setMemberId);
				int insertDP = cpService.insertDP(map); // �븮���� ���̺� ������ ����, ���� ���� ���, ��¥ insert
				System.out.println(insertDP);

		}
		
		else if(setMemberLevel.equals("gmanager")) { //������ ���� �־�����
			map.put("smem","gmanager");
			map.put("gmem",select);
			
				int memLevelUpdate = cpService.updateDP(map); // member���̺� �����÷�(mem_drpay) update
				System.out.println(memLevelUpdate);
				map.put("setMemberId",setMemberId);
				int insertDP = cpService.insertDP(map); // �븮���� ���̺� ������ ����, ���� ���� ���, ��¥ insert
				System.out.println(insertDP);
		}
		
		map.put("gmem", select);
		
		return "redirect:dr";
	}
	
	@RequestMapping(value="drrr", produces = "application/text; charset=utf8") // ajax �ѱ۱����� ��ȯ
	@ResponseBody // ajax ���ϰ� ������
	public String drrr(@RequestParam Map<String, Object> map) {
		String kLevel2 = cpService.kLevel2(map); // ajax ���õ� ����� ���� ��ȸ
		return kLevel2;
	}
	
}
