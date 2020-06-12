<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
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

input[type=text]:focus {
  background-color: lightblue;
}

input[type=text] {
   height: 25px;
}

input[type=password] {
   height: 25px;
}
	
input[type=password]:focus {
  background-color: lightblue;
} 

table{
	display : table;
	margin-top : 17%;
	margin-left : auto;
	margin-right : auto;
	padding: 50px;
	background-color: white;
}

body{
	background-color: white;
}

</style>
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
				<th><input type="text" id="id" name="id" placeholder="아이디" value="${inputId}" /></th>
			</tr>
			
			<tr>
				<th><input type="password" id="pwd" name="pwd" placeholder="비밀번호"/></th>
			</tr>
			
			<tr id="tr">
				<td colspan="2" align="center"><input type="button" class="bt1" id="btnLogin" value="로그인"/></td>
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