window.onload = function() {
    localStorage.accountList = undefined;

	$(".p-menu").on("click",function(){
		const menuUrl = $(this).attr("menu-url");
		if(menuUrl){
			$("#p-content-mid").attr("src",menuUrl);
		}
	});
	
	if($(".p-menu").length > 0){
		$($(".p-menu")[0]).click();
	}

	function dataInit(){
        localStorage.accountList = undefined;
        localStorage.currentAccount = undefined;
	}
};

