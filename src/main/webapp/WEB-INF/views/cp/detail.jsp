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
function popup(){
	var url = "dr";
	var name = "popup test";
	var option = "width = 300, height = 200, top = 100, left = 200, location = no"
	window.open(url, name, option);
}
	$(function(){
		//달력
	    $("#testDatepicker").datepicker({
	    	dateFormat: 'yy-mm-dd'
	    });
	    $("#testDatepickerr").datepicker({
	    	dateFormat: 'yy-mm-dd'
	    });
		
	    //검색버튼클릭
	    $("#btnSearch").click(function(){
			$("#frm").attr("method", "get").attr("action","search").submit();
		});
	    
	    //select option2 ajax
		$("#op2").change(function(){
			$.ajax({
					type : "get",
					url : "ajaxList",
					data : $("#frm").serialize(),
					success : function(data) {
										$("#ajax").html(data);
							},
					error : function(){
										alert("error");
							}
			});
		});
	    
	    //tr 클릭시
	    $("#trClick").click(function(){
			$("#frm").attr("method", "post").attr("action","paypage").submit();
		});
	    	    
	});

</script>
<meta charset="UTF-8">
<title>detail</title>
</head>
<body>
<form id="frm" name="frm">
	<table>
		<tr>
			<td>
				${sessionloginInfo.memName}님 환영합니다<input type="button" onclick="location.href='logOut'" value="로그아웃" />
				<input type="hidden" name="seq" value="${sessionloginInfo.memSeq}" />
				<input type="hidden" name="level" value="${sessionloginInfo.memLevel}" />
			</td>
		</tr>
		<tr>
			<td>
				<c:if test="${number == 3}" >
				<input type="button" onclick="location.href='writePage'" value="글쓰기" />
				</c:if>
				<c:if test="${number == 99}" >
				<input type="button" onclick="location.href='writePage'" value="글쓰기" />
				<input type="button" onclick="popup()" value="대리결재" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<select name="op1" id="op1">
					<option value="select">선택</option>
					<option value="writer" <c:if test="${map.op1 == 'writer'}">selected</c:if>>작성자</option>
					<option value="subject" <c:if test="${map.op1 == 'subject'}">selected</c:if>>제목</option>
					<option value="passer" <c:if test="${map.op1 == 'passer'}">selected</c:if>>결재자</option>
				</select>				
				<input type="text" id="keyword" name="keyword" placeholder="검색어를 입력하세요" value="${map.keyword}" />
				<select name="op2" id="op2">
					<option value="state">결재상태</option>
					<option value="imsi" <c:if test="${map.op2 == 'imsi'}">selected</c:if>>임시저장</option>
					<option value="paywait" <c:if test="${map.op2 == 'paywait'}">selected</c:if>>결재대기</option>
					<option value="paing" <c:if test="${map.op2 == 'paing'}">selected</c:if>>결재중</option>
					<option value="paid" <c:if test="${map.op2 == 'paid'}">selected</c:if>>결재완료</option>
					<option value="ban" <c:if test="${map.op2 == 'ban'}">selected</c:if>>반려</option>
				</select><br>
				<input type="text" name="date1" id="testDatepicker" value="${map.date1}" placeholder="날짜를 선택하세요" autocomplete="off" /> ~
				<input type="text" name="date2" id="testDatepickerr" value="${map.date2}" placeholder="날짜를 선택하세요" autocomplete="off" />
				<input type="button" id="btnSearch" value="검색" />
			</td>
		</tr>
		</table>
		<table border="1" id="ajax">
			<tr>
				<th>번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>작성일</th>
				<th>결재일</th>
				<th>결재자</th>
				<th>결재상태</th>
			</tr>
			<c:forEach items="${searchList}" var="searchList">
			<tr onclick="location.href='paypage?seq=${searchList.boardSeq}'">
				<td>${searchList.boardSeq}</td>
				<td>${searchList.boardWriter}</td>
				<td>${searchList.boardSubject}</td>
				<td>${searchList.boardRegdate}</td>
				<td>${searchList.boardUpdate}</td>
				<td>${searchList.boardPasser}</td>
				<td>${searchList.boardState}</td>
			</tr>
			<input type="hidden" name="boardSeq" value="${searchList.boardSeq}" />
			<input type="hidden" name="boardWriter" value="${searchList.boardWriter}" />
			<input type="hidden" name="boardSubject" value="${searchList.boardSubject}" />
			<input type="hidden" name="boardRegdate" value="${searchList.boardRegdate}" />
			<input type="hidden" name="boardUpdate" value="${searchList.boardUpdate}" />
			<input type="hidden" name="boardPasser" value="${searchList.boardPasser}" />
			<input type="hidden" name="boardState" value="${searchList.boardState}" />
			</c:forEach>
			<c:forEach items="${writeList}" var="writeList">
			<tr onclick="location.href='paypage?seq=${writeList.boardSeq}'">
				<td>${writeList.boardSeq}</td>
				<td>${writeList.boardWriter}</td>
				<td>${writeList.boardSubject}</td>
				<td>${writeList.boardRegdate}</td>
				<td>${writeList.boardUpdate}</td>
				<td>${writeList.boardPasser}</td>
				<td>${writeList.boardState}</td>
			</tr>
			<input type="hidden" name="boardSeq" value="${writeList.boardSeq}" />
			<input type="hidden" name="boardWriter" value="${writeList.boardWriter}" />
			<input type="hidden" name="boardSubject" value="${writeList.boardSubject}" />
			<input type="hidden" name="boardRegdate" value="${writeList.boardRegdate}" />
			<input type="hidden" name="boardUpdate" value="${writeList.boardUpdate}" />
			<input type="hidden" name="boardPasser" value="${writeList.boardPasser}" />
			<input type="hidden" name="boardState" value="${writeList.boardState}" />
			</c:forEach>
		</table>	
</form>	
</body>
</html>