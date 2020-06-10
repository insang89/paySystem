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
		//로그인버튼 클릭시
		$("#btnLogin").click(function(){
			if($("#id").val()=='') {
				alert("아이디를 입력하세요");
				$("#id").focus();
				return;
			}
			
			else if($("#pwd").val()=='') {
				alert("비밀번호를 입력하세요");
				$("#pwd").focus();
				return;
			}
			
			$("#frm").attr("method", "post").attr("action","login").submit();
		});
	});	
</script>
<meta charset="UTF-8">
<title>login page</title>
</head>
<body>

	<c:if test="${idCheck == 1}">
		<script type="text/javascript">
		alert("아이디가 틀렸습니다");
		</script>
	</c:if>
	
	<c:if test="${pwdCheck == 2}">
		<script type="text/javascript">
		alert("비밀번호가 틀렸습니다");
		</script>
	</c:if>

	<form id="frm" name="frm">
		<table>
			<tr>
				<th>아이디:</th>
				<th><input type="text" id="id" name="id" placeholder="아이디를 입력하세요" value="${inputId}" /></th>
			</tr>
			
			<tr>
				<th>비밀번호:</th>
				<!-- 나중에 type password로 바꾸기 -->
				<th><input type="password" id="pwd" name="pwd" placeholder="비밀번호를 입력하세요"/></th>
			</tr>
			
			<tr>
				<th><input type="button" id="btnLogin" value="로그인"/></th>
				<td>
					<input type="hidden" name="op1" value="" />
					<input type="hidden" name="keyword" value="" />
					<input type="hidden" name="data1" value="" />
					<input type="hidden" name="data2" value="" />
					<input type="hidden" name="op2" value="" />
				</td>
			</tr>
		</table>
	</form>	
</body>
</html>