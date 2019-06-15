function connecter(){
	jQuery.ajax({
		type : 'GET',
		url : '/api',
		dataType: 'json',
		data: {
			action : "login",
			username : $("#username").val(),
			password : $("#password").val()
		},
		success : function(result) {
			if(result.login == "loginOk"){
				window.location.href = '/';
			}else{
				window.location.href = '#';
				$("#msgErreur").show();
			}
		},
		error : function() {
			console.log('ooops de Login !');
		}
	});
}