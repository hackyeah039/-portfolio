// (1) 회원정보 수정
function update(userid,event) {
	event.preventDefault();
	let data = $("#profileUpdate").serialize();
	console.log(data);
	$.ajax({
		type:"put",
		url:`/api/user/${userid}`,
		data:data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(res=>{
		console.log("update 성공",res);
		location.href=`/user/${userid}`;
	}).fail(error=>{
		if(error.data == null ){
			alert(error.responseJSON.message);
		}else{
			alert(JSON.stringify(error.responseJSON.data));		
		}
	})
	
}