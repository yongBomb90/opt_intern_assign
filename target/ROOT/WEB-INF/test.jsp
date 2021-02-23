<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<h1>테스트페이지 입니다</h1>
	<img src="http://192.168.6.18:9103/board/8/file/8" />
	
	
	<div class='container-fluid' style="width: 100%; margin-top: 1%">
		<header class="head-section">
			<div class="navbar navbar-default navbar-static-top container">
			<div class="navbar-header">
				<a href="/home"> <img src="/resources/images/openit_ci.png"
					style="width: 50%; height: 50%; margin-top: 1%;">
				</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="loginModalBtn"><a href="" class="loginModalBtn">로그인</a>
					</li>
					<li class="registerModalBtn"><a href=""
						class="registerModalBtn"> 회원가입 </a></li>
				</ul>
			</div>
		</div>
		</header>
	</div>
</body>
<script type="text/javascript">
	
	$(window).load(function(){
	    alert('로드함수');
	});
	$(document).ready(function() {
		alert("레디함수")
		(function GO2() {
			alert("레디함수즉시실행함수")
			
		})();
	});
	(function GO() {
		alert("즉시실행함수")
		
	})();
	
	$("")
	
	
</script>
</html>