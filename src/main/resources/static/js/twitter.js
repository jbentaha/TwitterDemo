function charger(){
	jQuery.ajax({
		type : 'GET',
		url : '/api',
		dataType: 'json',
		data: {
			action : "lookup"
		},
		success : function(result) {
			var usr = jQuery('<div></div>').text("Bonjour "+result.userSession);
			$("#userNow").append(usr);
			for (i in result.messages){
				var ms = result.messages[i];
				var id = (ms.id);
				var msg = (ms.msg);
				var msgUser = (ms.msgUser);
				if(msg != null){
					var res = jQuery('<div></div>').attr("id",id).attr("class", "success").text(result.messages[i].msgUser+"..>>   "+msg);
					if(msgUser == result.userSession){
						var del = jQuery('<img src="img/delete.png"></img>').attr("onClick","supprimer(\""+id+"\")").attr("class", "delete");
						res.append(del);
					}
					likeOrNot(result.userSession, id, msg);
					var likeit = jQuery('<div></div>').attr("id",1+id).attr("class", "like");
					
					res.append(likeit);
					
					$('#messageView').prepend(res);
				}
				$("#message").val("");
			}
		},
		error : function() {
			console.log('ooops!');
		}
	});
}

function likeOrNot(user, idMsg, msg){
	jQuery.ajax({
		type : 'GET',
		url : '/api',
		dataType: 'json',
		data: {
			action : "isUserLiked",
			idMsg : idMsg,
			idUser : user
		},
		success : function(result) {
			if(result.isLiked == false){
				res = jQuery('<button></button>').attr("id",user+idMsg).attr("onClick","likeIt(\""+idMsg+"\",\""+user+"\",\""+user+idMsg+"\")").attr("class", "like").text("like it");	
			}
			if(result.isLiked == true){
				res = jQuery('<button></button>').attr("id",user+idMsg).attr("onClick","likeIt(\""+idMsg+"\",\""+user+"\",\""+user+idMsg+"\")").attr("class", "like").text("unlike it");
			}
			res2 = jQuery('<p></p>').text(result.nbLikes).attr("class","nbLikes").attr("id",user+idMsg+1);
			$('#'+1+idMsg).append(res2);
			$('#'+1+idMsg).prepend(res);
		},
		error : function() {
			console.log('ooops!');
		}
	});
}


function ajouter(){
	if($("#message").val() != "" && $("#message").val() != "\n"){
		jQuery.ajax({
			type : 'GET',
			url : '/api',
			dataType: 'json',
			data: {
				action : "add",
				content : $("#message").val()
			},
			success : function(result) {
				var id = (result.id);
				var msg = (result.msg);
				if(msg != null){
					var res = jQuery('<div></div>').attr("id",id).attr("class", "success").text(result.userSession+"..>>   "+msg);
					var del = jQuery('<img src="img/delete.png"></img>').attr("onClick","supprimer(\""+id+"\")").attr("class", "delete");
					res.append(del);
					
					likeOrNot(result.userSession, id, msg);
					var likeit = jQuery('<div></div>').attr("id",1+id).attr("class", "like");
					res.append(likeit);
					$('#messageView').prepend(res);
				}
				$("#message").val("");
			},
			error : function() {
				console.log('ooops!');
			}
		});
	}else{
		$("#message").val("");
	}
}

function supprimer(id){
	jQuery.ajax({
		type: 'GET',
		url: '/api',
		dataType: 'json',
		data: {
			action : "remove",
			id : id
		},
		success : function(result){
			//if(confirm("Want to delete?")) {
				if(result.isDeleted){
					$('#'+id).remove();
				}else{
					alert('erreur de suppresion');
				}
			//}
			
		},
		error : function(){
			console.log('oopss no delete !');
		},
	});
}


function deconnecter(){
	jQuery.ajax({
		type : 'GET',
		url : '/api',
		dataType: 'json',
		data: {
			action : "deconnect"
		},
		success : function(result) {
			window.location.href = '/';
		},
		error : function() {
			console.log('ooops de Login !');
		}
	});
}

function likeIt(idMsg, idUsr,idbutton){
	jQuery.ajax({
		type : 'GET',
		url : '/api',
		dataType: 'json',
		data: {
			action : "likee",
			idMsg: idMsg,
			idUser: idUsr
		},
		success : function(result) {
			changeIt(result.isAdded, idbutton, result.nbLikes, idUsr+idMsg+1);
		},
		error : function() {
			console.log('ooops de Like !');
		}
	});
}

function changeIt(isAdded,id, nbLikes, idnbLikes){
	if(isAdded == true){
		$('#'+id).text("unlike it");
	} 
	if(isAdded == false){
		$('#'+id).text('like it');
	}
	$('#'+idnbLikes).text(nbLikes);
}