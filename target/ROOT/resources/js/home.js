		var $body = $("body");
		var $modal = $(".modal.modal-sign")
		var $modalLogin = $(".modal-login")
		var $modalbtns = $('#homeNav .container .navbar-collapse .navbar-cus-modal');
		var $modalheader = $('.modal .modal-dialog .modal-content .modal-header');
		var $modalbody = $('.modal .modal-dialog .modal-content .modal-body');
		var $proFiledropzone = $('.modal .modal-dialog .modal-content .modal-body .dropzone.profile');
		var $modalImg = $('.modal .modal-dialog .modal-content .modal-body .modal-img');
		var $userIpts = $('.modal  input');
		var $userRegBtn = $('.modal .modal-dialog .modal-content .modal-footer .userRegBtn');
		var $userCheckId = $('.modal .modal-dialog .modal-content .modal-body .form-group-lg .btn-check-id');
		var $userCheckName = $('.modal .modal-dialog .modal-content .modal-body .form-group-lg .btn-check-name');
		var $passwordChecks = $modalbody.find('[type=password]');
		var $signModalBtn = $(".modal-login .modal-dialog .modal-content .modal-footer .btn-modal-sign");
		var $idIpt = $('.modal .modal-dialog .modal-content .modal-body .form-group-lg input[name=ID]');
		var $nameIpt = $('.modal .modal-dialog .modal-content .modal-body .form-group-lg input[name=별명]');
		var $loginBtn = $modalLogin.find('.modal-dialog .modal-content .modal-body .btn-login');
		
		var userFile;//유저 프로파일 데이터
		var checkList = new Array();
		
		//0:아이디 1:이름 3:비밀번호
		checkList[0] = false;
		checkList[1] = false;
		checkList[2] = false;
		
		$loginBtn.on('click',function(){
			var userId =$modalLogin.find('.modal-dialog .modal-content .modal-body input[name=userId]').val();
			var userPass =$modalLogin.find('.modal-dialog .modal-content .modal-body input[name=userPass]').val();
			 $.ajax({
					url : '/login',
					type : 'POST',
					data : { 'userId' : userId , 'userPass' : userPass , 'userType' : 'user'},
					success : function(data) {
						if (data.code == 0) {
							alert('로그인 실패하였습니다');
						} else {
							alert('로그인 되었습니다');
							$modalLogin.modal('hide');
							location.href='/boardView';
						}
					}
			});
			
			
		})
		
		$idIpt.on('keyup',function(){
			checkList[0] = false;
			$idIpt.css('color','red')
			$userCheckId.addClass('btn-danger')
		});
		
		$nameIpt.on('keyup',function(){
			checkList[1] = false;
			$nameIpt.css('color','red')
			$userCheckName.addClass('btn-danger')
		});
		
		$('.modal .modal-close').on('click',function(){
				reloadUserModal();
		})
		
		$body.on('dragenter dragover', function(event) {
			event.preventDefault();
		});
		$body.on('drop', function(event) {
			event.preventDefault();
		});
		
		$userIpts.find('[name=ID],[name=별명]').on('keyup',function(){
			alert('입력')
		})
		$signModalBtn.on('click', function() {
			$modalLogin.modal('hide');
			$modal.modal();
		})
		$modalbtns.on('click', function() {
			var val = $(this).val();
			var modalCondition = '';
			var modalBodyHtml = '';
			if (val == 0) {
				modalCondition = '로그인';
				$modalLogin.modal();
				return;
			} else if (val == 1) {
				modalCondition = '회원가입';
				$modal.modal();
			} 
		})

		$proFiledropzone.on('drop', function(event) {
			event.preventDefault();
			var files = event.originalEvent.dataTransfer.files;
			var imageFile = files[0];
			console.log(files.length)
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
			$modalbody.find('.modal-img-cancel').css('visibility','visible');
			userFile = imageFile;

		});
		
		$modalbody.find('.modal-img-cancel').on('click',function(){
			userFile = null;
			$modalImg.attr('src', '/resources/images/profile_default.jpg');
			$(this).css('visibility','hidden');
		})
		
		$userCheckId.on('click', function(event) {
			 var userId = $idIpt.val();
			 $.ajax({
					url : '/user/checkid/' + userId,
					type : 'GET',
					success : function(data) {
						if (data.code == 0) {
							checkList[0] = false;
							alert('중복된 아이디 입니다')
							$idIpt.css('color','red')
							$userCheckId.addClass('btn-danger')
						} else {
							checkList[0] = true;
							alert("사용가능한 아이디 입니다")
							$idIpt.css('color','black')
							$userCheckId.removeClass('btn-danger').addClass('btn-success')
						}
					}
				});
		 })
		 
		$userCheckName.on('click', function(event) {
			 var username = $nameIpt.val();
			  $.ajax({
					url : '/user/checkname/' + username,
					type : 'GET',
					success : function(data) {
						if (data.code == 0) {
							checkList[1] = false;
							alert('중복된 이름 입니다')
							$nameIpt.css('color','red')
							$userCheckName.addClass('btn-danger')
						} else {
							checkList[1] = true;
							alert("사용가능한 이름 입니다")
							$nameIpt.css('color','black')
							$userCheckName.removeClass('btn-danger').addClass('btn-success')
						}
					}
				});
		 })
		
		
		 
		 
		
		 $passwordChecks.on('keyup',function(){
			 var $passwordipt = $($passwordChecks[0])
			 var $passwordckipt = $($passwordChecks[1])
			 var password = $passwordipt.val();
			 var ckPassword = $passwordckipt.val();
			 if(password == ckPassword){
				 $passwordipt.css('background-color','green');
				 $passwordckipt.css('background-color','green');
				 checkList[2] = true;
			 } else {
				 $passwordipt.css('background-color','red');
				 $passwordckipt.css('background-color','red');
				 checkList[2] = false;
			 }
			 
			 
		 });
		 
		$userRegBtn.on('click', function(event) {
			
			//널값체크
			var emptyIpt = '';
			for (var i = 0; i < $userIpts.length; i++) {
				var value = $($userIpts[i]).val()
				if(!value){
					var name = $($userIpts[i]).attr('name')
					emptyIpt += name+' '
				}
			}
			if(emptyIpt){
				alert(emptyIpt+' 를 작성해 주세요')
				return;
			}
			for(var s = 0; s<checkList.length; s++ ){
				if(!checkList[s]){
					alert("중복 확인해 주세요")
					return;
				}
			}
			
			var userId =  $modalbody.find('input[name=ID]').val();
			var userPass = $modalbody.find('input[name=PASSWORD]').val();
			var userNick = $modalbody.find('input[name=별명]').val();
			var userFormData = new FormData();
			userFormData.append('userId', userId);
			userFormData.append('userPass', userPass);
			userFormData.append('userNick', userNick);
			userFormData.append('userType', 'user');
			userFormData.append('userFile', userFile);
			$.ajax({
				url : '/user',
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
							reloadUserModalDanger();
						} else {
							alert('죄송합니다 잠시후 다시 접속해주세요')
						}
					} else {
						alert('회원가입 처리 되었습니다')
						reloadUserModal();
					}
				},
				error : function(data) {
					alert('중복된 이름 또는 아이디가 있습니다')
					userFormData.reset;
				}
			})

		});
		
		function reloadUserModal(){
			for (var i = 0; i < $userIpts.length; i++) {
				$($userIpts[i]).val('')
				$($userIpts[i]).css('background-color','');
			}
			userFile = null;
			$modalImg.attr('src', '/resources/images/profile_default.jpg');
			$modalbody.find('.modal-img-cancel').css('visibility','hidden');
			$modal.modal('hide');
		}
		function reloadUserModalDanger(){
			$modalbody.find('input[name=ID]').css('color','red');
			$modalbody.find('input[name=별명]').css('color','red');
			checkList[0] = false;
			checkList[1] = false;
		}
		
