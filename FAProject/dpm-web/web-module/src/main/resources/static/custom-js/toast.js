$(document).ready(function () {
	const main = document.getElementById("toast");
	
	const delay = (2000 / 1000).toFixed(2);
//	
//	let url = window.location.href ;
//	let pathname = window.location.pathname;
//	
//	
//	let devide = url.split('?');
//	let mes = devide[1].split('=');
//	
//	let action = mes[0];
//	let result = mes[1];
//	console.log(mes);
	let state = $('#stateValue').val();
	console.log(state);
	switch(state) {
	  case "CREATE_SUCCESS":
		  $('.toast').addClass('toast--success');
			$('.toast__msg').text('Tạo mới thành công')
		    main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s ${delay}s forwards`;
		    $('#toast').removeClass('toast-hide');
	    break;
	  case "CREATE_FAILED":
		  $('.toast').addClass('toast--error');
			$('.toast__title').text('Thất bại');
			$('.toast__msg').text('Có lỗi mời liên hệ Admin');
			main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s ${delay}s forwards`;
		    $('#toast').removeClass('toast-hide');
	    break;
	  case "UPDATE_SUCCESS":
		  $('.toast').addClass('toast--info');
			$('.toast__msg').text('Cập nhật thành công');
			main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s ${delay}s forwards`;
		    $('#toast').removeClass('toast-hide');
	    break;
	  case "UPDATE_FAILED":
		  $('.toast').addClass('toast--error');
			$('.toast__title').text('Thất bại');
			$('.toast__msg').text('Có lỗi mời liên hệ Admin');
			main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s ${delay}s forwards`;
		    $('#toast').removeClass('toast-hide');
	    break;
	  case "DELETE_SUCCESS":
		  	$('.toast').addClass('toast--warning');
			$('.toast__msg').text('Đã xóa thành công')
		    main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s ${delay}s forwards`;
		    $('#toast').removeClass('toast-hide');
	    break;
	  case "DELETE_FAILED":
		  	$('.toast').addClass('toast--error')
			$('.toast__title').text('Thất bại');;
			$('.toast__msg').text('Có lỗi mời liên hệ Admin');
			main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s ${delay}s forwards`;
		    $('#toast').removeClass('toast-hide');
	    break;
	  case "SETTING_SUCCESS":
		  	$('.toast').addClass('toast--success');
			$('.toast__msg').text('Thiết lập giá trị mặc định thành công')
		    main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s ${delay}s forwards`;
		    $('#toast').removeClass('toast-hide');
	    break;
	  case "SETTING_FAILED":
		  	$('.toast').addClass('toast--error')
			$('.toast__title').text('Thiết lập giá trị mặc định');;
			$('.toast__msg').text('Có lỗi mời liên hệ Admin');
			main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s ${delay}s forwards`;
		    $('#toast').removeClass('toast-hide');
	    break;
	}
	

	setTimeout(function() {
	},1000)
	$('.toast__close').click(function (e) {
	    $('#toast').addClass('toast-hide');

	})
    
	
	
});