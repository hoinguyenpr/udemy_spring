


function run() {


	var ultra = document.getElementById("Ultra");
	
	var size= ultra.value;
	var search = document.getElementById("search").value;
	
	var page = document.getElementById("pageNum").value;
	
	document.location.href = "/?size="+ size +"&search=" + search + "&page=" + page  ;
}




function editAccount(accountId) {
	$.ajax({
	    type:"GET",
	    url: "/getAccountById/" + accountId,
	
	    success:function(data) {
	        
	        //error mt-2 text-danger
			var e =document.getElementsByClassName("error");
			for (var i = 0; i < e.length; i++) {
		         e[i].remove();
		    }
			
	       
			// remove check box when load
			var c = document.getElementsByTagName("input");
			for (var i = 0; i < c.length; i++) {
		        if (c[i].type == 'checkbox') {
		            c[i].checked = false;
		        }
		    }
		    
		    //alert(document.getElementById("editactive").value);
		    
		    // set value for each account
		    document.getElementById("idAccountEdit").value = data.id ;
		    document.getElementById("fullNameEdit").value = data.fullName ;
		    document.getElementById("editpassword").value = data.password ;
		    document.getElementById("emailEdit").value = data.email ;
		    document.getElementById("emailFsoftEdit").value = data.emailFsoft ;
		    if(data.active==true){
		    	document.getElementById("editactive").checked = true;
		    }
		    
	        var txt = "";
	        for (var x in data.role) {
	            txt = data.role[x].roleName;
	            // alert(txt);
	            document.getElementById(txt).checked = true;
	        };
	
	    },
	    error:function(e) {
	        alert('QIRAGI');
	    }
	});
}



