<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>snstemplat</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<script src="/resources/js/bootstrap.min.js"></script>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>
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
					<li class="loginModalBtn"><a href="" class="loginModalBtn">
							로그인 </a></li>
					<li class="registerModalBtn"><a> 회원가입 </a></li>
				</ul>
			</div>
		</div>
		</header>
	</div>









	<!-- 모달 팝업 -->
	<div class="modal fade" id="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span> <span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">로그인</h4>
				</div>
				<div class="modal-body">
				
				<div >
				<h3><span class="label label-info col-md-2 " >아이디</span></h3>
					<input type="text">
				</div>
				
				<div >
					<h3><span class="label label-info col-md-2 " >비밀번호</span></h3>
					<input type="text">
				</div>
				
			     
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary">로그인</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(".loginModalBtn").on("click", function(event) {
			event.preventDefault();
			$('.modal').modal();
		})
	</script>





</body>
</html>