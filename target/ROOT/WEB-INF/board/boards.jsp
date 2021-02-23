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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<script src="/resources/js/bootstrap.min.js"></script>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>


<link href="/resources/css/header.css" rel="stylesheet">
</head>
<body>


	<nav id="headerNav" class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class='navbar-header'>
			<button class="navbar-toggle navbar-menu">
				<span>메뉴</span>
			</button>
			<a class="navbar-brand" href="home">Open it SNS</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right navbar-custom ">
				<li class="navbar-menu navbar-cus-modal" ><a href="/boardView">게시글</a></li>
				<li class="navbar-menu navbar-cus-modal"><a href="/myBoards">내 게시글</a></li>
				<li class="navbar-menu navbar-cus-modal" ><a href="#" class="board-reg-btn">글 작성</a></li><!-- 모달 -->
				<li class=" dropdown " value="3">
					<img src="/user/${user.getUserNum()}/image" data-close-others="false" data-delay="0" data-hover="dropdown" 
					data-toggle="dropdown" aria-expanded="false" />
					<ul class="dropdown-menu">
						<li class="navbar-menu">
							<a class="user-mod-btn">회원수정</a><!-- 모달 -->
						</li>
						<li class="navbar-menu">
							<a href="/logout">로그아웃</a>
						</li>
					</ul>
				
				</li>
			</ul>
		</div>
	</div>
	</nav>


		<div class="user-mod-modal modal fade modal-sign" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close modal-close" data-dismiss="modal">×</button>
					<h4 class="modal-title">회원수정</h4>
				</div>
				<div class="modal-body">
					
					<img class="modal-img" src="/user/${user.getUserNum()}/image" />
					<button class="modal-img-cancel" >취소</button>
					<div class="form-group-lg">
						<p>아이디</p>
						<input type="text" class="input-sm" id="userId" name="${user.getUserNum()}" value="${user.getUserId()}"  placeholder="아이디" disabled>
						<p>비밀번호</p>
						<input type="password" class="input-sm user-input" name="userPass"  placeholder="비밀번호 ">
						<button class='btn btn-danger  user-pass-btn' >비밀번호확인</button>
						<p>비밀번호 확인</p>
						<input type="password" class="input-sm user-input" name="userPassCheck" disabled placeholder="비밀번호">
						<p>별명</p>
						<input class="input-sm user-input" name="checkname" value="${user.getUserNick()}" disabled placeholder="별명">
						<button class='btn btn-danger  user-check-btn' value="checkname">중복</button>
						<div class="dropzone profile">파일을 올려 주세요</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info modal-user-mod-btn">수정</button>
					<button type="button" class="btn btn-default modal-close" data-dismiss="modal">닫기</button>
``
				</div>

			</div>
		</div>
	</div>
	
	
	<div class="board-reg-modal modal fade modal-sign" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close modal-board-close" data-dismiss="modal">×</button>
					<h4 class="modal-title">게시글 등록</h4>
				</div>
				<div class="modal-body">
				<p>게시글</p>
				<textarea class="textarea board-textarea"></textarea>
				<div class="dropzone board">파일을 올려 주세요</div>
				<p>파일</p>
				<div class="board-file-list"></div>
				</div>
				
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default modal-board-close" data-dismiss="modal">닫기</button>
``				</div>

			</div>
		</div>
	</div>

	<script type="text/javascript" src="/resources/js/header.js"></script>
	</body>
</html>