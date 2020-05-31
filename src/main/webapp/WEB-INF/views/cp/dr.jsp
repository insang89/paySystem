<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		$("#select").change(function(){
			change(this);
		});
		
		$("#pass").click(function(){
			$("#frm").attr("method", "post").attr("action","drr").submit();
		});
		
		function change(o){
			var val = $(":selected",o).val();
			$("#dr").val(val);
			if(val == '김사원' || val == '이사원' || val == '박사원'){
				$("#memLevel").val("사원");
			}
			if(val == '김대리' || val == '이대리'){
				$("#memLevel").val("대리");
			}
		}
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
					<option value="select">선택하세요</option>
					<option value="김사원">staff111 김사원</option>
					<option value="이사원">staff222 이사원</option>
					<option value="박사원">staff333 박사원</option>
					<option value="김대리">amanager111 김대리</option>
					<option value="이대리">amanager222 이대리</option>
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
				<input type="text" id="dr" readonly="readonly" />
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