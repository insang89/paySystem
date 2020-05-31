<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
			<tr>
				<th>번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>작성일</th>
				<th>결재일</th>
				<th>결재자</th>
				<th>결재상태</th>
			</tr>
			<c:forEach items="${ajaxSearch}" var="ajaxSearch">
			<tr onclick="location.href='paypage?seq=${ajaxSearch.boardSeq}'">
				<td>${ajaxSearch.boardSeq}</td>
				<td>${ajaxSearch.boardWriter}</td>
				<td>${ajaxSearch.boardSubject}</td>
				<td>${ajaxSearch.boardRegdate}</td>
				<td>${ajaxSearch.boardUpdate}</td>
				<td>${ajaxSearch.boardPasser}</td>
				<td>${ajaxSearch.boardState}</td>
			</tr>
			</c:forEach>
</body>
</html>