window.onload = function() {
	$(".p-menu").on("click",function(){
		const menuUrl = $(this).attr("menu-url");
		if(menuUrl){
			$("#p-content-mid").attr("src",menuUrl);
		}
	});
	
	if($(".p-menu").length > 0){
		$($(".p-menu")[0]).click();
	}
}
