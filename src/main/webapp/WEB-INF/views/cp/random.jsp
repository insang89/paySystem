<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script type="text/javascript">

var timeId = "";
	
$(function(){
	
	var strArray = new Array();
	var b=0;
	var c=0;
	var d=0;

	for(var i=1; i<=40; i++) {
		if(i<=10) {
			strArray[i]="A"+"-"+i;
		}
		else if(i>10 && i<=20) {
			var a=1+b;
			strArray[i]="B"+"-"+a;
			b++;
		}
		else if(i>20 && i<=30) {
			var b=1+c;
			strArray[i]="C"+"-"+b;
			c++;
		}
		else if(i>30 && i<=40) {
			var c=1+d;
			strArray[i]="D"+"-"+c;
			d++;
		}
	}

	$("#btn").click(function(){
		
		var num = "";
		var i = 1;
		var b = new Array();
		
		while(i < 41) {
			num = Math.floor(Math.random() * (strArray.length - 1)) + 1;
			if (! sameNum(num)) {
				b[i] = num;
				i++;
			}
		}
		
		function sameNum (num) {
			for (var i=1; i<=b.length; i++) {
				if (num == b[i]) {
				return true;
				}
			}
			return false;
		}

		var count = 1;
		var tr = "";
		var td = "";
		
		Start();
		
		function Start() {
				add();
				timeId = setInterval(add, 50);
		}
		
		function add() {
			
			if((count%4) == 1) {
				tr = $("<tr>");
				$("#table").append(tr);
			}
			
			td = $("<td>",{text:strArray[b[count]]});
			tr.append(td);
			
			count++;
			
			if(count == 41) {
				Stop();
			}
		}
	});
});

	function Stop() {
		if(timeId != null) {
			clearInterval(timeId);
// 			history.go(0);
		}
	}

</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- <table border="1" id="on" style="text-align: center;"> -->
<!-- 	<tr> -->
<!-- 		<th style="width: 150px;">팀1</th> -->
<!-- 		<th style="width: 150px;">팀2</th> -->
<!-- 		<th style="width: 150px;">팀3</th> -->
<!-- 		<th style="width: 150px;">팀4</th> -->
<!-- 	</tr> -->
<!-- </table> -->
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="멈춤" style="width: 100px; height: 80px;" onclick="Stop()" />
<input type="button" id="btn" value="추첨" style="width: 100px; height: 80px;" />
<input type="button" value="새로고침" style="width: 100px; height: 80px;" onclick="location.reload(true);" />
<table border="1" id="table" style="text-align: center;">
	<tr>
		<th style="width: 150px;">팀1</th>
		<th style="width: 150px;">팀2</th>
		<th style="width: 150px;">팀3</th>
		<th style="width: 150px;">팀4</th>
	</tr>
</table>

</body>
</html>