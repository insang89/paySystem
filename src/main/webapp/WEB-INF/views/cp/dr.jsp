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
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script type="text/javascript">

function cl(){
	window.close();
}
 
	$(function(){
		$("#pass").click(function(){
			
			var select = $("#select").val();
			
			if(select == 'sel'){
				alert("대리결재자를 선택하세요");
			}
			
			else{
				$("#frm").attr("method", "POST").attr("action","drr").submit();
				alert("승인완료");
				window.close();
			}
			
		});
		
		$("#select").change(function(){
			$.ajax({
					type : "get",
					url : "drrr",
					data : $("#frm").serialize(),
					success : function(data) {
										$("#memLevel").val(data);
							},
					error : function(){
										alert("error");
							}
			});
		});
		
	});
</script>
<meta charset="UTF-8">
<title>대리결재</title>
</head>
<body>
<form name="frm" id="frm">
	<table>
		<tr>
			<td>대리결재자:</td>
			<td>

					<select name="select" id="select">
						<option value="sel">선택하세요</option>
						<c:forEach items="${memList}" var="memList">
						<option value="${memList.memId}">${memList.memName}</option>
						</c:forEach>
					</select>

			</td>
		</tr>
		<tr>
			<td>직급:</td>
			<td>
				<input type="text" id="memLevel" readonly="readonly" />
			</td>
		</tr>
		<tr>
			<td>대리자:</td>
			<td>
				<input type="text" id="dr" readonly="readonly" value="${memName}(${memLevel})" />
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" class="bt1" id="pass" value="승인" />
				<input type="button" class="bt1" onclick="cl()" value="닫기" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>