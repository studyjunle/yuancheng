<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/md5.js"></script>
<title>系统集成</title>
</head>
<body>
	<form method="POST" id="formLogin" onsubmit="login();return false">
		<p>
			<input type="submit" value="单击强制登录" />
		</p>
	</form>
	
	<form method="POST" id="formOpenData" onsubmit="openSource();return false">
		<p>
			<span>报表ID：</span>
			<span>
				<input type="text" name="openID" id="openID" value="I8a8ae5d7016cdc4ddc4dd6a1016cdc529fed0041"/>
			</span>
			<span style="margin-left: 10px;">
				<input type="submit" value="打开报表"/>
			</span>
		</p>
	</form>

</body>
<script type="text/javascript">
// 免密登录
function login() {
	// 生成用户名
	var username = "admin";
	// 生成现在的时间戳
	var time = generateTimeRequestNumber(new Date());
	// 生成token
	var token = hex_md5(username + time);
	// 拼接url
    formLogin.action = "http://localhost:8080/smartbi/vision/index.jsp?token=" + token + "&username=" + username + "&datetime=" + time; 
    // 提交
    formLogin.submit(); 
    return true; 
}

// 打开报表
function openSource() {
	// 生成用户名
	var username = "admin";
	// 生成现在的时间戳
	var time = generateTimeRequestNumber(new Date());
	// 生成token
	var token = hex_md5(username + time);
	// 获取报表ID
	var openId = document.getElementById("openID").value;
	// 拼接url
    formOpenData.action = "http://localhost:8080/smartbi/vision/openresource.jsp?token=" + token + "&username=" + username + "&datetime=" + time + "&resid=" + openId; 
    // 提交
    formOpenData.submit(); 
    return true; 
}
// 生成时间戳
function generateTimeRequestNumber(date) {
	var year = date.getFullYear().toString();
	var month = conversionTime(date.getMonth() + 1);
	var today = conversionTime(date.getDate());
	var hours = conversionTime(date.getHours());
	var minutes = conversionTime(date.getMinutes());
	var seconds = conversionTime(date.getSeconds());
	var time = year + month + today + hours + minutes + seconds;
    return time;
}
function conversionTime(n) {
	return n < 10 ? '0' + n : n;
}
</script>
</html>