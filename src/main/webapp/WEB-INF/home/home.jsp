<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta charset='utf8'>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>snstemplat</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<script src="/resources/js/bootstrap.min.js"></script>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>


<link href="/resources/css/header.css" rel="stylesheet">
</head>
<body>


	<nav id="homeNav" class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class='navbar-header'>
			<button class="navbar-toggle navbar-menu">
				<span>메뉴</span>
			</button>
			<a class="navbar-brand" href="home">Open it SNS</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right navbar-custom ">
				<li class="navbar-menu navbar-cus-modal" value="0"><a href="#">로그인</a></li>
				<li class="navbar-menu navbar-cus-modal" value="1"><a href="#">회원가입</a></li>
				
			</ul>
		</div>
	</div>
	</nav>

	<div>
	</div>

	<div class="modal fade modal-sign">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close modal-close" data-dismiss="modal">×</button>
					<h4 class="modal-title">회원가입</h4>
				</div>
				<div class="modal-body">
					
					<img class="modal-img" src="/resources/images/profile_default.jpg" />
					<button class="modal-img-cancel" >취소</button>
					<div class="form-group-lg">
						<p>아이디</p>
						<input type="text" class="input-sm" name="ID"  placeholder="아이디"><button class='btn btn-danger  btn-check-id'>중복</button>
						<p>비밀번호</p>
						<input type="password" class="input-sm" name="PASSWORD"  placeholder="비밀번호 ">
						<p>비밀번호 확인</p>
						<input type="password" class="input-sm" name="PASSWORD재확인"  placeholder="비밀번호">
						<p>별명</p>
						<input class="input-sm" name="별명"  placeholder="별명"><button class='btn btn-danger  btn-check-name'>중복</button>
						<div class="dropzone profile">파일을 올려 주세요</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info userRegBtn">가입</button>
					<button type="button" class="btn btn-default modal-close" data-dismiss="modal">닫기</button>

				</div>

			</div>
		</div>
	</div>
	<div class="modal fade modal-login">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close modal-close" data-dismiss="modal">×</button>
					<h4 class="modal-title">로그인</h4>
				</div>
				<div class="modal-body">
						<input class="input-lg " name="userId"  placeholder="ID"><br>
						<input class="input-lg" type="password" name="userPass"  placeholder="PASSWORD">
						<button class="btn btn-custom btn-login">로그인</button>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info btn-modal-sign" value="3" >회원가입</button>
					<button type="button" class="btn btn-default modal-close" data-dismiss="modal">닫기</button>

				</div>

			</div>
		</div>
	</div>


	<script type="text/javascript" src="/resources/js/home.js"></script>
	</body>
</html>