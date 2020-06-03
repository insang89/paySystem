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

function cl(){
	window.close();
}
 
	$(function(){
		
		$("#pass").click(function(){
			$("#frm").attr("method", "post").attr("action","drr").submit();
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
				<c:if test="${memLevel != '부장'}">
					<select name="select" id="select">
						<option>선택하세요</option>
						<c:forEach items="${memList}" var="memList">
						<option value="${memList.memId}">${memList.memName}</option>
						</c:forEach>
					</select>
				</c:if>
				
				<c:if test="${memLevel == '부장'}">
					<select name="select" id="select">
						<option>선택하세요</option>
						<c:forEach items="${memList}" var="memList">
						<option value="${memList.memId}">${memList.memName}</option>
						</c:forEach>
					</select>
				</c:if>
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
				<input type="button" id="pass" value="승인" />
				<input type="button" onclick="cl()" value="창닫기" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>