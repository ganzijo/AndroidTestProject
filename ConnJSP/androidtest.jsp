<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>�ȵ���̵�� JSP�� ���</title>
<script type="text/javascript">
function setMessage(arg) {
    document.getElementById('textMessageFromApp').innerHTML = arg;
}
function sendMessage(msg){
    window.hybrid.setMessage(msg);
}
</script>
</head>
<body>
<h1>JSP View</h1>
<font size="3">Android To JSP</font>
<button type = "button" name = "exit" onclick = "javascript:sendMessage('exit');">����</button>
</body>
</html>