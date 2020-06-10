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
			<c:forEach items="${writeList}" var="writeList">
			<tr onclick="location.href='paypage?seq=${writeList.boardSeq}'">
				<td>${writeList.boardSeq}</td>
				<td>${writeList.boardWriterKR}</td>
				<td>${writeList.boardSubject}</td>
				<td>${writeList.boardRegdate}</td>
				<td>${writeList.boardUpdate}</td>
				<td>${writeList.boardPasserKR}</td>
				<td>${writeList.boardState}</td>
			</tr>
			<input type="hidden" name="boardSeq" value="${writeList.boardSeq}" />
			<input type="hidden" name="boardWriter" value="${writeList.boardWriterKR}" />
			<input type="hidden" name="boardSubject" value="${writeList.boardSubject}" />
			<input type="hidden" name="boardRegdate" value="${writeList.boardRegdate}" />
			<input type="hidden" name="boardUpdate" value="${writeList.boardUpdate}" />
			<input type="hidden" name="boardPasser" value="${writeList.boardPasser}" />
			<input type="hidden" name="boardState" value="${writeList.boardState}" />
			</c:forEach>

			<tr align="center">
				<td colspan="7">
					 <c:if test="${pageMap.curBlock > 1}">
		                    <a href="javascript:goPage('1')">[처음]</a>
		                </c:if>
		                <!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->
		                <c:if test="${pageMap.curBlock > 1}">
		                    <a href="javascript:goPage('${pageMap.prevPage}')">[이전]</a>
		                </c:if>
		                <!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->
		                <c:forEach var="num" begin="${pageMap.blockBegin}" end="${pageMap.blockEnd}">
		                    <!-- **현재페이지이면 하이퍼링크 제거 -->
		                    <c:choose>
		                        <c:when test="${num == pageMap.curPage}">
		                            <span style="color: red">${num}</span>&nbsp;
		                        </c:when>
		                        <c:otherwise>
		                            <a href="javascript:goPage('${num}')">${num}</a>&nbsp;
		                        </c:otherwise>
		                    </c:choose>
		                </c:forEach>
		                <!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->
		                <c:if test="${pageMap.curBlock <= pageMap.totBlock}">
		                    <a href="javascript:goPage('${pageMap.nextPage}')">[다음]</a>
		                </c:if>
		                <!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->
		                <c:if test="${pageMap.curPage <= pageMap.totPage}">
		                    <%-- <a href="javascript:goPage('${pageMap.totPage}')">[끝]</a> --%>
		                </c:if>
		             </td>
               </tr> 
</body>
</html>