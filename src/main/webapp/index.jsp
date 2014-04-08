<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<p>
		<a href="page1">goto page1</a>
	</p>
	<p>
		<a href="page2">goto page2</a>
	</p>
	<div>
		<form action="insertUser" method="POST">
			<div>
				<s:fielderror></s:fielderror>
			</div>
			<div>
				<label>姓名</label><input type="text" name="username" value="<s:property value='username'/>">
			</div>
			<div>
				<label>年龄</label><input type="text" name="age" value="<s:property value='age'/>">
			</div>
			<div>
				<input type="submit" value="提交">
			</div>
		</form>

	</div>
</body>
</html>
