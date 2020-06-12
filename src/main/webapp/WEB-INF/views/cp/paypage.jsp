<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script src="/resources/js/jquery-3.5.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script>
$(function(){
	
	var boardState = $("#boardState").val(); // 글상태
	var memLevel = $("#memLevel").val(); // 직급
	var memDrPay = $("#memDrPay").val(); // 대리결재권한
	var boardWriter = $("#boardWriter").val(); // 작성자
	var memId = $("#memId").val(); // 로그인한사람아이디
	console.log(boardState);
	console.log(memLevel);
	console.log(memDrPay);
	//결재
	$("#btnEnroll").click(function(){
		if(boardState == '임시저장' && (memLevel == 'staff' || memLevel == 'amanager')){
			$("#boardState").val("paywait");
		}
		$("#frm").attr("method", "get").attr("action","payButton").submit(); /* update문 태우기 */
	});
	
	//임시저장
	$("#imsi").click(function(){
		$("#frm").attr("method", "get").attr("action","paypageImsibutton").submit();
	});
	
	//반려
	$("#ban").click(function(){
		$("#frm").attr("method", "get").attr("action","banButton").submit();
	});
	
	var boardState = $("#boardState").val(); // 글상태
	
	if(boardState == '결재대기'){
		$("#chk2").prop("checked", false);
		$("#chk3").prop("checked", false);
	}
	else if(boardState == '결재중'){
		$("#chk3").prop("checked", false);
	}
	else if(boardState == '임시저장' || boardState == '반려'){
		$("#chk1").prop("checked", false);
		$("#chk2").prop("checked", false);
		$("#chk3").prop("checked", false);
	}
	
	if(boardState == '임시저장'){
		$("#ban").prop("type", "hidden");
	}
	
	if((memDrPay == null || memDrPay == '' || memDrPay == 'null') && (memLevel == 'staff' || memLevel == 'amanager')){ // 사원 대리
		$("#ban").prop("type", "hidden");
 		if(boardState=="결재대기"){
 			$("#subject").attr("readonly", "readonly");
 			$("#content").attr("readonly", "readonly");
 			$("#imsi").prop("type", "hidden");
 			$("#btnEnroll").prop("type", "hidden");
 		}
		if(boardState=="결재완료" || boardState=="결재중"){
			$("#subject").attr("readonly", "readonly");
 			$("#content").attr("readonly", "readonly");
 			$("#imsi").prop("type", "hidden");
 			$("#ban").prop("type", "hidden");
 			$("#btnEnroll").prop("type", "hidden");
 		}
	}
	
	if(memLevel == 'manager' || memDrPay == 'manager'){ // 과장 & 과장권한부여
		if(boardState=="결재대기"){
			$("#subject").attr("readonly", "readonly");
 			$("#content").attr("readonly", "readonly");
 			$("#imsi").prop("type", "hidden");
		}
		if(boardWriter == memId && boardState == '반려'){
			$("#ban").prop("type", "hidden");
		}
		if((boardState=="결재중" || boardState=="결재완료" || boardState=="반려") && boardWriter != memId){
			$("#subject").attr("readonly", "readonly");
 			$("#content").attr("readonly", "readonly");
 			$("#imsi").prop("type", "hidden");
 			$("#ban").prop("type", "hidden");
 			$("#btnEnroll").prop("type", "hidden");
		}
		if(boardState == '결재중' || boardState=="결재완료" && boardWriter == memId){
			$("#imsi").prop("type", "hidden");
 			$("#ban").prop("type", "hidden");
 			$("#btnEnroll").prop("type", "hidden");
		}
	}
	
	if(memLevel == 'gmanager' || memDrPay == 'gmanager'){ // 부장
		if((boardState=="반려" || boardState=="결재완료") && boardWriter != memId){
			$("#subject").attr("readonly", "readonly");
 			$("#content").attr("readonly", "readonly");
 			$("#imsi").prop("type", "hidden");
 			$("#ban").prop("type", "hidden");
 			$("#btnEnroll").prop("type", "hidden");
		}
		if(boardState=="결재중"){
			$("#subject").attr("readonly", "readonly");
 			$("#content").attr("readonly", "readonly");
 			$("#imsi").prop("type", "hidden");
		}
		if(boardState == '결재대기' && boardWriter == memId){
			$("#imsi").prop("type", "hidden");
 			$("#ban").prop("type", "hidden");
 			$("#btnEnroll").prop("type", "hidden");
		}
		if(boardState == '반려' && boardWriter == memId) {
			
		}
		if(boardState == '결재완료' && boardWriter == memId) {
			$("#imsi").prop("type", "hidden");
 			$("#ban").prop("type", "hidden");
 			$("#btnEnroll").prop("type", "hidden");
		}
	}
});
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="frm" id="frm">
	<table border="1">
		<tr>
			<td>결재요청</td>
			<td>과장</td>
			<td>부장</td>
		</tr>
		<tr>
			<td><input type="checkbox" checked="checked" id="chk1" disabled="disabled" /></td>
			<td><input type="checkbox" checked="checked" id="chk2" disabled="disabled" /></td>
			<td><input type="checkbox" checked="checked" id="chk3" disabled="disabled" /></td>
		</tr>
	</table>
	
	<table>
		<tr>
			<td>번호: <input type="text" readonly="readonly" value="${writeInfo.boardSeq}" /></td>
		</tr>
		<tr>
			<td>작성자: <input type="text" readonly="readonly" id="boardWriterKR" name="boardWriterKR" value="${writeInfo.boardWriterKR}" /></td>
		</tr>
		<tr>
			<td>제목: <input type="text" id="subject"  name="subject" value="${writeInfo.boardSubject}" /></td>
		</tr>
		<tr>
			<td>내용: <textarea cols="50" rows="10" id="content" name="content" >${writeInfo.boardContent}</textarea></td>
		</tr>
		<tr>
			<td>
				<input type="button" id="imsi" value="임시저장" />
				<input type="button" id="ban" value="반려" />
				<input type="button" id="btnEnroll" value="결재" />
				
				<input type="hidden" name="memSeq" value="${sessionloginInfo.memSeq}" />
				<input type="hidden" name="boardUpdate" value="${writeInfo.boardUpdate}" />
				<input type="hidden" name="boardPasser" value="${writeInfo.boardPasser}" />
				<input type="hidden" name="boardSeq" value="${writeInfo.boardSeq}" />
				<input type="hidden" name="boardWriter" id="boardWriter" value="${writeInfo.boardWriter}" />
				<input type="hidden" name="memLevel" value="${sessionloginInfo.memLevel}" />
				<input type="hidden" id="boardState" name="boardState" value="${writeInfo.boardState}" />
				<input type="hidden" id="memName" value="${session.memName}" />
				<input type="hidden" id="memLevel" value="${session.memLevel}" />
				<input type="hidden" id="memDrPay" name="memDrPay" value="${session.memDrPay}" />
				<input type="hidden" id="memId" name="memId" value="${session.memId}" />
			</td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<th>번호</th>
			<th>결재일</th>
			<th>결재자</th>
			<th>결재상태</th>
		</tr>
		
		<c:forEach items="${paypageList}" var="paypageList">
		<tr>
			<td>${paypageList.logSeq}</td>
			<td>${paypageList.logUpdate}</td>
			<td>${paypageList.logPasser}</td>
			<td>${paypageList.logState}</td>
		</tr>
		</c:forEach>
	</table>
</form>
</body>
</html>