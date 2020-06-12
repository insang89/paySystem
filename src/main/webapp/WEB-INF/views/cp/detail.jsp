<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<style>

.ft {
	color: blue;
	font-weight: bold;
}

select {
	height: 25px;
}

input[type=text] {
   height: 21px;
}

#tr:hover {
  background-color: orange;
  }
  
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
  
body{
	display : table;
	margin-left : auto;
	margin-right : auto;
	padding: 30px;
	background-color: white;
}

.table {
	border: solid 1px;
	border-color: gray;
	width: 700px;
}

td {
	height: 25px;
}

.blink {
      -webkit-animation: blink 1.0s linear infinite;
      font-weight: bold;
        }
@keyframes blink {
       /*0% 부터 100% 까지*/ 
        0% { color:black;}
        50% { color:yellow;}
        100% {color:red; }
        /*from부터 to 까지         
        from {color:#00a0e9;}
        to {color:#000;} 
        */
        }

</style>
<script src="/resources/js/jquery-3.5.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script>
function goPage(num){
	$("#curPage").val(num);
	$("#btnSearch").click();
}

function popup(){
	var url = "dr";
	var name = "popup test";
	var option = "width = 500, height = 200, top = 100, left = 200, location = no"
	window.open(url, name, option);
}

	$(function(){
		//달력
		$("#testDatepicker").click(function(){
			$("#testDatepicker").datepicker({
		    	dateFormat: 'yy-mm-dd'
		    });
		});
  	    
		$("#testDatepicker").click(function(){
	    	$("#testDatepickerr").datepicker({
	    		dateFormat: 'yy-mm-dd'
	    	});
	    });
		
	    //검색버튼클릭
	    $("#btnSearch").click(function(){
			$("#frm").attr("method", "get").attr("action","list").submit();
		});
	    
	    //select option2 ajax
		$("#op2").change(function(){
			$.ajax({
					type : "get",
					url : "list",
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
<body id="ajax">
<form id="frm" name="frm">
	<table>
		<tr>
			<td>
				<c:if test="${flag != 1}">
				<span class='ft'>${sessionloginInfo.memName}(${dLevel})</span>님 환영합니다 - <span class='ft'>${sMem.MEM_NAME}(${sMem.MEM_LEVEL})</span>님 환영합니다
				<input type="button" class="bt1" onclick="location.href='loginPage'" value="로그아웃" /><br>
				<span class='blink'>(대리결재일 : ${sMem.DP_DATE})</span>
				</c:if>
				
				<c:if test="${flag == 1}">
				<span class='ft'>${sessionloginInfo.memName}(${dLevel})</span>님 환영합니다
				<input type="button" class="bt1" onclick="location.href='loginPage'" value="로그아웃" /><br>
				</c:if>
			</td>
		</tr>
				<input type="hidden" name="seq" value="${sessionloginInfo.memSeq}" />
				<input type="hidden" name="level" value="${sessionloginInfo.memLevel}" />
				<input type="hidden" name="curPage" id="curPage" value="1" />
				<input type="hidden" name="listSize" value="10" />
		<tr>
			<td>
				<c:if test="${number == 3}" >
				<input type="button" class="bt1" onclick="location.href='writePage'" value="글쓰기" />
				</c:if>
				<c:if test="${number == 99}" >
				<input type="button" class="bt1" onclick="location.href='writePage'" value="글쓰기" />
				<input type="button" class="bt1" onclick="popup()" value="대리결재" />
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
				<input type="text" name="date1" id="testDatepicker" value="${map.date1}" placeholder="날짜선택" autocomplete="off" /> ~
				<input type="text" name="date2" id="testDatepickerr" value="${map.date2}" placeholder="날짜선택" autocomplete="off" />
				<input type="button" class="bt1" id="btnSearch" value="검색" />
			</td>
		</tr>
		</table>
		<table class="table" border="1" id="ajax">
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
			<tr onclick="location.href='paypage?seq=${writeList.boardSeq}'" id="tr">
				<td align="center">${writeList.boardSeq}</td>
				<td align="center">${writeList.boardWriterKR}</td>
				<td align="center">${writeList.boardSubject}</td>
				<td align="center">${writeList.boardRegdate}</td>
				<td align="center">${writeList.boardUpdate}</td>
				<td align="center">${writeList.boardPasserKR}</td>
				<td align="center">${writeList.boardState}</td>
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
		</table>
</form>	
</body>
</html>