<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<style>
.a {
	text-align: center;
	width: 70px;
		}
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
	background-image:url('https://www.waglebagle.net/uploads/editor/20190611142816.png');
		}

table {
    width: 1000px;
    border: 1px solid #444444;
    border-collapse: collapse;
 		 }
th, td {
    border: 1px solid #444444;
    padding: 5px;
    height: 17px;
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

.wr {
	margin-right: 500px;
	}

</style>
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
<body id="ajax" onload="printClock()">

<form id="frm" name="frm">

				<c:if test="${number == 3}" >
				<input type="button" class="bt1" onclick="location.href='writePage'" value="글쓰기" />
				</c:if>
				<c:if test="${number == 99}" >
				<input type="button" class="bt1" onclick="location.href='writePage'" value="글쓰기" />
				<input type="button" class="bt1" onclick="popup()" value="대리결재" />
				</c:if>
				<div style="width:150px; color:black;font-size:23px; text-align:center;" id="clock"></div>

	<table>
		<tr>
			<td align="center">
				<c:if test="${flag != 1}">
				<span class='ft'>${sessionloginInfo.memName}(${dLevel})</span>님 환영합니다 - <span class='ft'>${sMem.MEM_NAME}(${sMem.MEM_LEVEL})</span>님 환영합니다
				<span class='blink'>(대리결재일 : ${sMem.DP_DATE})</span>
				<input type="button" class="bt1" onclick="location.href='loginPage'" value="로그아웃" /><br>
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

		<tr align="center">
			<td>
				<input type="text" name="date1" class='a' id="testDatepicker" value="${map.date1}" placeholder="날짜선택" autocomplete="off" /> ~
				<input type="text" name="date2" class='a' id="testDatepickerr" value="${map.date2}" placeholder="날짜선택" autocomplete="off" /><br>
				<select name="op1" id="op1">
					<option value="select">선택</option>
					<option value="writer" <c:if test="${map.op1 == 'writer'}">selected</c:if>>작성자</option>
					<option value="subject" <c:if test="${map.op1 == 'subject'}">selected</c:if>>제목</option>
					<option value="passer" <c:if test="${map.op1 == 'passer'}">selected</c:if>>결재자</option>
					<option value="subcon" <c:if test="${map.op1 == 'subcon'}">selected</c:if>>제목+내용</option>
				</select>				
				<input type="text" id="keyword" name="keyword" placeholder="검색어를 입력하세요" value="${map.keyword}" />
				<select name="op2" id="op2">
					<option value="state">결재상태</option>
					<option value="imsi" <c:if test="${map.op2 == 'imsi'}">selected</c:if>>임시저장</option>
					<option value="paywait" <c:if test="${map.op2 == 'paywait'}">selected</c:if>>결재대기</option>
					<option value="paing" <c:if test="${map.op2 == 'paing'}">selected</c:if>>결재중</option>
					<option value="paid" <c:if test="${map.op2 == 'paid'}">selected</c:if>>결재완료</option>
					<option value="ban" <c:if test="${map.op2 == 'ban'}">selected</c:if>>반려</option>
				</select>
				<input type="button" class="bt1" class='a' id="btnSearch" value="검색" />
				<br>
			</td>
		</tr>
		</table>
		<table class="table" border="1" id="ajax">
			<tr>
				<th>번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>내용</th>
				<th>작성일</th>
				<th>결재일</th>
				<th>결재자</th>
				<th>결재상태</th>
				<th>조회수</th>
			</tr>
			
			<c:if test="${ck != 'true'}">
			<c:forEach items="${writeList}" var="writeList">
			<tr onclick="location.href='paypage?seq=${writeList.boardSeq}&curPage=${pageMap.curPage}'" id="tr" style="cursor: pointer;">
				<td align="center">${writeList.boardSeq}</td>
				<td align="center">${writeList.boardWriterKR}</td>
				<td align="center">${writeList.boardSubject}</td>
				<td align="center">${writeList.boardContent}</td>
				<td align="center">${writeList.boardRegdate}</td>
				<td align="center">${writeList.boardUpdate}</td>
				<td align="center">${writeList.boardPasserKR}</td>
				<td align="center">${writeList.boardState}</td>
				<td align="center">${writeList.viewCount}</td>
			</tr>
				
			<input type="hidden" name="boardSeq" value="${writeList.boardSeq}" />
			<input type="hidden" name="boardWriter" value="${writeList.boardWriterKR}" />
			<input type="hidden" name="boardSubject" value="${writeList.boardSubject}" />
			<input type="hidden" name="boardContent" value="${writeList.boardContent}" />
			<input type="hidden" name="boardRegdate" value="${writeList.boardRegdate}" />
			<input type="hidden" name="boardUpdate" value="${writeList.boardUpdate}" />
			<input type="hidden" name="boardPasser" value="${writeList.boardPasser}" />
			<input type="hidden" name="boardState" value="${writeList.boardState}" />
			<input type="hidden" name="boardState" value="${writeList.viewCount}" />
			</c:forEach>
			</c:if>

			<c:if test="${ck == 'true'}">
				<tr>
					<td colspan="8" align="center"><img src="https://topclass.chosun.com/upload/topp/2019-04/simg_org/sp.jpg"></td>
				</tr>
			</c:if>

			<tr align="center">
				<td colspan="9">
					 <c:if test="${pageMap.curBlock > 1}">
		                    <!-- <a href="javascript:goPage('1')">[처음]</a> -->
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