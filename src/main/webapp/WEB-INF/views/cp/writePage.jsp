<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<style>

.bt1 {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 7px 12px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 12px;
  margin: 4px 2px;
  cursor: pointer;
  }
body {
	background-image:url('https://www.waglebagle.net/uploads/editor/20190611142816.png');
}
  
</style>
<script src="/resources/js/jquery-3.5.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script>
function goBack() {
	window.history.back();
}

$(function(){
	//결재
	$("#btnEnroll").click(function(){
		$("#frm").attr("method", "get").attr("action","writeEnroll").submit();
	});
	
	$("#btnBack").click(function(){
		$("#frm").attr("method", "get").attr("action","list").submit();
	});
	
	//임시저장
	$("#imsi").click(function(){
		$("#frm").attr("method", "get").attr("action","imsiEnroll").submit();
	});
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
			<td><input type="checkbox" disabled="disabled" /></td>
			<td><input type="checkbox" disabled="disabled" /></td>
			<td><input type="checkbox" disabled="disabled" /></td>
		</tr>
		
	</table>
	
	<table>
		<tr>
			<td>번호: <input type="text" readonly="readonly" name="boardSeq" value="${count}" /></td>
		</tr>
		<tr>
			<td>작성자: <input type="text" readonly="readonly" value="${sessionloginInfo.memName}" /></td>
		</tr>
		<tr>
			<td>제목: <input type="text" name="subject" /></td>
		</tr>
		<tr>
			<td>내용: <textarea cols="50" rows="10" name="content" ></textarea></td>
		</tr>
		<tr>
			<td>
				<input type="button" class="bt1" id="imsi" value="임시저장" />
				<input type="button" class="bt1" id="btnEnroll" value="결재" />
				<input type="button" class="bt1" onclick="goBack()" value="뒤로" />
				
				<input type="hidden" name="name" value="${sessionloginInfo.memId}" />
				<input type="hidden" name="boardWriter" value="${sessionloginInfo.memId}" />
				<input type="hidden" name="level" value="${sessionloginInfo.memLevel}" />
				<input type="hidden" name="memDrPay" value="${sessionloginInfo.memDrPay}" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>