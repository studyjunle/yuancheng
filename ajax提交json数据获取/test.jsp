<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="jq.js"></script>
</head>

<body>
	<input type="button" value="click" onclick="post1()">
</body>
<script type="text/javascript">
	var info = {
		"username" : "上海",
		"date" : "20190924"
	};
	var infoData = {
		"userInfo" : JSON.stringify(info)
	};
	function post1() {
		$.ajax({
			url : "/smartbi/vision/Ajax",
			type : "POST",
			dataType : "json",
			data : infoData,
			success : function(result) {

			},
			error : function(result) {

			}
		});
	}
</script>
</html>
