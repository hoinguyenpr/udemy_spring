$(document).ready(function () {
	
	//set date default is current
	
	let state = $('#stateValue').val();
	let defaultTypeProduct = $("#typeProducts").val();
	let typeCurrent 	= $("#typeCurrent").val();
	let lotCurrent 		= $("#lotCurrent").val();
	let dateCurrent 	= $("#dateCurrent").val();
	let shiftCurrent 	= $("#shiftCurrent").val();
	let statustCurrent 	= $("#statusCurrent").val();
	let sizeCurrent 	= $("#sizeCurrent").val();
	console.log(typeCurrent);
	console.log(lotCurrent);
	console.log(dateCurrent);
	console.log(shiftCurrent);
	
	if (dateCurrent) {//check date was chose
		$("#date").val(dateCurrent);

	}else{
		let date = currentDate();// date wasn't chose and this is new page
		$("#date").val(date);
	}
	
	if (typeCurrent) {	//check type was choose			
		$('#typeProducts').val(typeCurrent).change();
		if (typeCurrent=='0') {
			setValueALL() //set LotCode is ALL
		}else{
			callAPI(typeCurrent,lotCurrent); // call api and update type and lot 
		}	
	}else{
		let value = $('#typeProducts').val();
		if (value=='0') {
			setValueALL() //set LotCode is ALL
		}else{
			console.log(value);
			callAPI(defaultTypeProduct,lotCurrent); // call api to load new page
		}		
	}
	 
	if(shiftCurrent){
		$('#selectShift').val(shiftCurrent).change();
	}
	if(statustCurrent){
		$('#statusSelect').val(statustCurrent).change();
	}
	if (sizeCurrent) {
		$('#sizeSelect').val(sizeCurrent).change();

	}
	if (state == 'EXPORT_EMPTY') {
		swal({
            title: "Không có dữ liệu!",
            text: "Không có kết quả cho Bộ lọc trên!",
            icon: "error",
        })
	}
	
	// select product lot on change
	$('#typeProducts').on('change', function() {
		console.log('change')
	    let value = $(this).val();
		if (value=='0') {
			setValueALL() //set LotCode is ALL
		}else{
			console.log(value);
			callAPI(value,0); //call API get productLot by Typeid
		}
				
	});
	
	$('#report-btn').click(function (e) {
		e.preventDefault();
		$('#filterForm').attr('action','/checklist/report');
		$('#filterForm').submit();
		
		$('#filterForm').attr('action','/checklist/filter');
		console.log(this.value)
		setTimeout(function(){
			$('#filterForm').submit(); 
		}, 3000);

		
		
	})
	
	$('#sizeSelect').on('change', function() {
		$('#filterForm').attr('action','/checklist/filter');

		$('#filterForm').submit();
				
	});
	
});

function callAPI(id,lotCurrent) {
	console.log(lotCurrent);
	if (id =='undefined') { // check id not underfine then open ajax
		//stop
	}else{
		
		let url = window.location.origin+"/"+window.location.pathname;
		$.ajax({
	        url: 'api/lot-by-type/'+id,
	        type: 'GET'
	    }).done(function(result) {
	        if (result) {
	      	  console.log(result)
	      	 setValueToSelect(result,lotCurrent);
	      	 
	        }else{
	      	  console.log('error!');
	        }
	    }).fail(function(error) {
			console.log(error)
	  	  
	    });
	}
	
}
function setValueALL() {
	let element = ``;
	let allValue = $('#all').val();
	element += `	<option value="0">
				  		${allValue}`;
	
	$("#productLots").empty();
	$("#productLots").append(element);
}
function setValueToSelect(result,lotCurrent) {
	console.log(lotCurrent);
	let element = ``;
	let allValue = $('#all').val();
	element += `	<option value="0">
				  		${allValue}
				  	</option>`;	
	for (var i = 0; i < result.length; i++) {
		let id = result[i].id;
		var lotCode = result[i].lotCode;
		if (id == lotCurrent) {
			element += `	<option value="${id}" selected>
	      		${lotCode}
	      	</option>`;	
		}else{
			element += `	<option value="${id}">
	      		${lotCode}
	      	</option>`;	
		}
					
	}
	
	$("#productLots").empty();
	$("#productLots").append(element);
		
	
}


function currentDate() {
	  var now = new Date();
	    var month = (now.getMonth() + 1);               
	    var day = now.getDate();
	    if (month < 10) 
	        month = "0" + month;
	    if (day < 10) 
	        day = "0" + day;
	    var today = now.getFullYear() + '-' + month + '-' + day;
	    
	    return today
}
