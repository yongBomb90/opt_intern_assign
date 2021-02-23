//헤더의 이벤트 노드

var $headerNav = $('#headerNav');
var $headerNavCon = $('#headerNav .container');
var $body = $('body');
$body.on('dragenter dragover', function(event) {
	event.preventDefault();
});
$body.on('drop', function(event) {
	event.preventDefault();
});

// 사용자 관련
var $userModBtn = $headerNavCon.find('.navbar-collapse .dropdown ul li .user-mod-btn');
var $userModModal = $('.user-mod-modal');
var $userIpts = $('.user-mod-modal .user-input');
var $userPassCkBtn = $userModModal.find('.modal-dialog .modal-content .modal-body .user-pass-btn');
var $userPassIpt = $('.user-mod-modal .user-input[name=userPass]');
var $userCkBtn = $userModModal.find('.modal-dialog .modal-content .modal-body .user-check-btn');
var $userDropZone = $userModModal.find('.modal-dialog .modal-content .modal-body .dropzone');
var $userModalModBtn = $userModModal.find('.modal-dialog .modal-content .modal-footer .modal-user-mod-btn');
var $userModalCloseBtn = $userModModal.find('.modal-close');
var userDTO = {usernickCk : false, userPassCk: false};




$userModBtn.on('click', function(e) {
	$userPassCkBtn.show();
	$userCkBtn.hide();
	$userDropZone.hide();
	$userModModal.modal();
	$userModModal.find('.modal-footer .modal-user-mod-btn').css('visibility','hidden');
	$userModModal.find('.modal-body .modal-img-cancel').css('visibility','hidden');
	
});

$userPassCkBtn.on('click', function(e) {
	var userPass = $userPassIpt.val();
	$.ajax({
		url : '/user/checkpass',
		type : 'POST',
		data : {
			'userPass' : userPass
		},
		success : function(data) {
			console.log(data)
			if (data.code == 0) {
				alert('비밀번호가 다릅니다');
			} else {
				for (var i = 0, len = $userIpts.length; i < len; i++) {
					$($userIpts[i]).removeAttr('disabled')
				}
				userDTO.usernickCk = true;
				userDTO.userPassCk = true;
				$userPassCkBtn.siblings('[name=userPassCheck]').val(userPass)
				$userCkBtn.show();
				$userDropZone.show();
				$userPassCkBtn.hide();
				$userModModal.find('.modal-footer .modal-user-mod-btn').css('visibility','visible');
			}
		}
	});
})

$userCkBtn.on('click', function(e) {
	var selector = '.user-mod-modal .user-input[name=' + $(this).val() + ']';
	var thisselector = '.user-mod-modal button[value=' + $(this).val() + ']';
	var $thisIpt = $(selector);
	var res = $thisIpt.val();
	var Url = 'user/' + $(this).val() + '/' + res;
	$.ajax({
		url : Url,
		type : 'GET',
		success : function(data) {
			console.log(data)
			if (data.code == 0) {
				alert('불가능한 이름입니다');
				$thisIpt.css('color', 'red');
				userDTO.usernickCk = false;
			} else if (data.message.indexOf('본인의 이름입니다') > -1) {
				alert('본인의 이름입니다');
				$thisIpt.css('color', 'black');
				userDTO.usernickCk = true;
				userDTO.userNick = null;
				$(thisselector).removeClass('btn-danger').addClass('btn-success')
			} else {
				alert('사용가능한 이름 입니다');
				$thisIpt.css('color', 'black');
				$(thisselector).removeClass('btn-danger').addClass('btn-success')
				userDTO.usernickCk = true;
				userDTO.userNick = res;
			}
		}
	});

})

$userIpts.siblings('[name=userPassCheck],[name=userPass]').on('keyup',function(e){
	var $userPassIpts = $userIpts.siblings('[name=userPassCheck],[name=userPass]')
	if($(this).siblings('[name=userPassCheck]').attr('disabled')){
		return;
	}
	if($userPassIpts.eq(0).val() == $userPassIpts.eq(1).val()) {
		$userPassIpts.css('background-color','green')
		userDTO.userPass = $userPassIpts.eq(0).val();
		userDTO.userPassCk = true;
		return;
	}
	userDTO.userPassCk = false;
	userDTO.userPass = null;
	$userPassIpts.css('background-color','red')
	
})


$userDropZone.on('dragenter dragover',function(event){
	event.preventDefault();
})
$userDropZone.on('drop',function(event){
	event.preventDefault();
	var files = event.originalEvent.dataTransfer.files;
	var $modalImg = $userModModal.find('.modal-body  .modal-img');
	var imageFile = files[0];
	if (files.length != 1) {
		alert('한개의 이미지만 올려주세요');
		return;
	}
	if (!imageFile.type.match('image')) {
		alert('이미지만 올려주세요');
		return;
	}
	var picReader = new FileReader();
	picReader.addEventListener("load", function(event) {
		var image = event.target;
		$modalImg.attr('src', image.result);
		$modalImg.attr('title', image.name);

	});
	picReader.readAsDataURL(imageFile);
	alert($modalImg.siblings('.modal-img-cancel').length)
	$modalImg.siblings('.modal-img-cancel').css('visibility','visible');
	userDTO.userFile = imageFile;
	
})

$userModModal.find('.modal-body .modal-img-cancel').on('click',function(event){
	var userNum = $('#userId').attr('name');
	var $modalImg = $userModModal.find('.modal-body  .modal-img');
	$(this).css('visibility','hidden');
	$modalImg.attr('src', '/user/'+userNum+'/image');
	userDTO.userFile = null;
});

$userModalModBtn.on('click',function(event){
	console.log("이름중복 "+userDTO.usernickCk);
	if(!userDTO.usernickCk){
		alert('이름중복을 체크해주세요')
		return;
	}
	if(!userDTO.userPassCk){
		alert('비밀번호 중복을 체크해주세요')
		return;
	}
	
	var userFormData = new FormData();
	if(userDTO.userPass){
	userFormData.append('userPass', userDTO.userPass);
	}
	if(userDTO.userNick) {
	userFormData.append('userNick', userDTO.userNick);
	}
	if(userDTO.userFile) {
	userFormData.append('userFile', userDTO.userFile);
	}
	$.ajax({
		url : '/user/modify',
		data : userFormData,
		processData : false,
		contentType : false,
		type : 'POST',
		success : function(data) {
			console.log(data.code);
			console.log(data.message);
			var code = data.code;
			var message = data.message;
			var data = data.data;
			if (code == 0) {
				if (message.indexOf('DuplicateKeyException') > -1) {
					alert('중복된 이름또는 아이디가 있습니다');
				} else {
					alert('죄송합니다 잠시후 다시 접속해주세요')
				}
			} else {
				alert('회원수정 처리 되었습니다');
				$userModModal.modal('hide');
				userModalRepage();
				
			}
		},
		error : function(data) {
			alert('중복된 이름 또는 아이디가 있습니다')
			userFormData.reset;
		}
	})
})

$userModalCloseBtn.on('click',function(e){
	userModalRepage();
})

function userModalRepage(){
	var $modalImg = $userModModal.find('.modal-body  .modal-img');
	var userNum = $('#userId').attr('name');
	$.ajax({
		url : '/user/'+userNum,
		type : 'GET',
		success : function(data) {
			if(data.code>0){
				var userNick = data.data.userNick;
				var math = Math.random()*100;
				var imageUrl = data.data.imageUrl+'?fakeparam='+math;
				$userIpts.siblings('[name=userPassCheck],[name=userPass]').val("");
				$userIpts.siblings('[name=checkname]').val(userNick);
				$userIpts.siblings('[name=userPassCheck],[name=checkname]').attr('disabled','disabled');
				$headerNavCon.find('.collapse li.dropdown img').attr('src',imageUrl);
				$modalImg.attr('src',imageUrl);
			}
		},
		error : function(data) {
			alert('중복된 이름 또는 아이디가 있습니다')
			userFormData.reset;
		}
	})
	
}

//게시판 관련
var $boardRegBtn = $headerNavCon.find('.navbar-collapse .navbar-menu .board-reg-btn');
var $boardRegModal = $('.board-reg-modal');
var $boardRegModalBody = $boardRegModal.find('.modal-dialog .modal-content .modal-body');
var $boardRegModalFileDiv = $boardRegModal.find('.modal-dialog .modal-content .modal-body .board-file-list');
var $boardRegModalFileZone = $boardRegModal.find('.modal-dialog .modal-content .modal-body .dropzone.board');
var files = new Array();
var boardVO = {files:files, boardContent:null}

$boardRegBtn.on('click',function(){
	$boardRegModal.modal();
	
});

$boardRegModalFileZone.on('dragenter dragover',function(event){
	event.preventDefault();
});

$boardRegModalFileZone.on('drop',function(event){
	event.preventDefault();
	var files = event.originalEvent.dataTransfer.files;
	for(var i = 0, len=files.length; i<len; i++ ) {
		fileUrl(files[i],boardVO)
	}
	
	alert(boardVO.files.length)
	
});











function fileUrl (file,boardVO){
	var files = boardVO.files;
	var fileName = file.name;
	var html = 
	'<div class="col-md-2">'
	+'<img src="resources/images/audio.png" />'
	+'파일이름 <button>취소<button>'
	+'</div>'
	
	
	if (file.type.match('image')) {
		files.push(file)
		
	} else if (file.type.match('video')) {
		files.push(file)
		
	} else if (file.type.match('audio')) {
		files.push(file)
	}
	
}


