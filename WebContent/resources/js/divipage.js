$(function(){
	if('${currentPage}'=='1'){
		$('#previous').addClass('disabled');
		$('#previous').click(function(){
			return false;
		})
	}else{
		$('#previous').removeClass('disabled');
	}
	if('${currentPage}'=='${countPage}'){
		$('#next').addClass('disabled');
		$('#next').click(function(){
			return false;
		})
	}else{
		$('#next').removeClass('disabled');
	}
})