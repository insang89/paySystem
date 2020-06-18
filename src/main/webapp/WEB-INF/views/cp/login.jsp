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
	padding: 15px;
	background-color: none;
}

body{
	background-image:url('https://www.waglebagle.net/uploads/editor/20190611142816.png');
	background-color: #D9E5FF;
}

</style>
<head>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script type="text/javascript">
function printClock() {
    
    var clock = document.getElementById("clock");            // 출력할 장소 선택
    var currentDate = new Date();                                     // 현재시간
    var calendar = currentDate.getFullYear() + "-" + (currentDate.getMonth()+1) + "-" + currentDate.getDate() // 현재 날짜
    var amPm = 'AM'; // 초기값 AM
    var currentHours = addZeros(currentDate.getHours(),2); 
    var currentMinute = addZeros(currentDate.getMinutes() ,2);
    var currentSeconds =  addZeros(currentDate.getSeconds(),2);
    
    if(currentHours >= 12){ // 시간이 12보다 클 때 PM으로 세팅, 12를 빼줌
    	amPm = 'PM';
    	currentHours = addZeros(currentHours - 12,2);
    }

    if(currentSeconds >= 50){// 50초 이상일 때 색을 변환해 준다.
       currentSeconds = '<span style="color:#de1951;">'+currentSeconds+'</span>'
    }
    clock.innerHTML = currentHours+":"+currentMinute+":"+currentSeconds +" <span style='font-size:23px;'>"+ amPm+"</span>"; //날짜를 출력해 줌
    
    setTimeout("printClock()",1000);         // 1초마다 printClock() 함수 호출
}

function addZeros(num, digit) { // 자릿수 맞춰주기
	  var zero = '';
	  num = num.toString();
	  if (num.length < digit) {
	    for (i = 0; i < digit - num.length; i++) {
	      zero += '0';
	    }
	  }
	  return zero + num;
}
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
<body onload="printClock()">
<div style="width:150px; color:black;font-size:23px; text-align:center;" id="clock"></div>
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
				<th><input type="text" id="id" name="id" placeholder="아이디" value="${inputId}" style="text-align: left: ;" /></th>
			</tr>
			
			<tr>
				<th><input type="password" id="pwd" name="pwd" placeholder="비밀번호" style="text-align: left: ;" /></th>
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