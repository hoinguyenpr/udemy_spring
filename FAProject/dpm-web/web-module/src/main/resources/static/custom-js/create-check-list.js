<!-- LamPQT -->
$(document).ready(function () {
	const main = document.getElementById("toast");
	const delay = (2000 / 1000).toFixed(2);
	
    $("#id").val(0);
    getFirstTime();
    resetError();
    
    $('.js-example-basic-single').select2({
       
        width: '100%'
    });
   
    $('#myRange').change(function (e) { 
	    e.preventDefault();
	    
	    let hour = (($(this).val())/60).toString();
	    console.log($(this).val());
	    console.log(hour);
	    hour = parseInt(hour)+1;
	    

	    let ampm = formatAMPM(new Date());
	    if (hour<10) {
			hour = "0"+hour;			
		}
	    
	    
	    let time = hour+":"+"00"+" "+ampm;
	   
	    $('#hourCreate').val(time);
	    
	  });
    	$("#resetTime").click(function (e) {
    		 e.preventDefault();
    		getFirstTime();
    		resetError();
		})
		
		
    $(".remove-checklist").click(function(e) {
    	 e.preventDefault();
    	 let deleteId = $(this).attr('data-remove');
    	 $("#deleteId").val(deleteId);
          
    });
    
    $(".edit-checklist").click(function(e) {
    	e.preventDefault();
    	resetError('');
   	 let editId = $(this).attr('data-edit');
   	 // call api get data of checklist by id
   	 callAPI(editId);
    
    });
    // For Create and Edit Feature
   
    $("#saveButton").click(function(e) {
      	 e.preventDefault();      	
      	 var errors = true;
      	 var count = 0;
     	$("#saveCheckList input").map(function(){
            if( !$(this).val() ) {
            	if (this.id == 'remark') {               
                	$(this).removeClass('is-invalid');
    			}else{
    				$(this).addClass('is-invalid')
    				$(this).parent('.form-group').children('.label-err').text('Bạn Không được để trống!')
    				if (this.id == 'hourCreate') {
    					$('#errTime').text('Bạn Không được để trống!')
					}
    				count++
    			}                
           } else if ($(this).val()) {
                 $(this).removeClass('is-invalid');           
           } 
           
       });    	
     	if(count==0){
        	validate(''); 
        	$("#saveButton").prop('disabled',true);
        }
     	
    });
    $("#resetButton").click(function(e) { // reset text to '' and border is black
    	e.preventDefault();
    	$("#saveCheckList").trigger('reset');	
    	$("#saveCheckList").attr("action","/checklist/create");
  	  	$("#saveButton").text("Thêm");
  	  	$("#saveButton").addClass("btn-primary");
  	  	$("#saveButton").removeClass("btn-danger");
  	  	$('#setDefaultByRecord').addClass('hide');
  	  	$("#saveCheckList input").map(function(){
  	  		$(this).removeClass('is-invalid');
  	  	});    
  	  	getFirstTime();
  	  	resetError('');
    });
    
    // For Create and Edit Feature
    $(".setting-checklist").click(function(e) {
    	let settingID = $(this).attr('data-setting');
    	let host = window.location.host+'/checklist/api/setting/'+settingID;
    	
    	$.ajax({
                url: 'api/setting/'+settingID,
                type: 'GET',
                
            }).done(function(result) {
            	if (result =='FAILED') {
            		$('.toast').addClass('toast--error')
					$('.toast__title').text('Thiết lập giá trị mặc định');;
					$('.toast__msg').text('Có lỗi mời liên hệ Admin');
					main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s 2s forwards`;
				    $('#toast').removeClass('toast-hide');
            		
				}else{
					$('.toast').addClass('toast--success');
        			$('.toast__msg').text('Thiết lập giá trị mặc định thành công')
        		    main.style.animation = `slideInLeft ease .5s, fadeOut linear 1s 2s forwards`;
        		    $('#toast').removeClass('toast-hide');
        		    
        		    changeValue(result);
        		    $("html, body").animate({ scrollTop: 30 }, "slow");

				}
            });
    	/*$.get({
            url: host,      
            success: function (result) {
            	alert(result)
            	console.log(result)
            	
            	
            }
        })*/
   
	});
   
    
    $('#setDefaultByRecord').click(function(e) { // reset text to '' and border is black
    	e.preventDefault();
    	$("#saveCheckList").attr("action","/checklist/setting");
    	resetError('');
    	validate();   	  	 	  	
    });
    
    
   
});



function validate() {


	$.post({
        url: '/checklist/api/validate',
        data: $('form[name=validateForm]').serialize(),
        success: function (objectErrors,) {
        	console.log(objectErrors)
        	
           if (Object.keys(objectErrors).length == 0) {
        	   
        		   $('#saveCheckList').submit();  
        	      
           }else{
        	   writeError(objectErrors,'');
        	   $("#saveButton").prop('disabled',false);	
           }
        }
    })
}
  


function qcValidate(modal) {
	let isValidate = true;
	let valQC = $('#qcs'+modal).val();

	let qc = $('#qcs'+modal).find("option[value='" + valQC + "']");
	if (qc != null && qc.length > 0) {
		isValidate = true;
	}else{
		isValidate = false;
		
	}
	return isValidate;
}

  function lotValidate(modal) {
	let isValidate = true;
	let lotVal = $('#lotCode'+modal).val();
	console.log(lotVal)
	let lot = $('#lotCodes'+modal).find("option[value='" + lotVal + "']");
	if (lot != null && lot.length > 0) {
		isValidate = true;
	}else{
		isValidate = false;
		
	}
	return isValidate;
}

  function formatAMPM(date) {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    let ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    
    minutes = minutes < 10 ? '0'+minutes : minutes;
    let strTime =  ampm;
    return strTime;
  }

  function format24hTo0h(date){
    let hour = date.getHours();
    if(hour>12){
        return hour-12;
    }
    return hour;
  }

  function getFirstTime() {
    var date = new Date();
    var hh= format24hTo0h(date);
    var MM = date.getMinutes();
    var ampm = formatAMPM(date);
    var range = parseInt(hh-1)*60 ;
    if (hh<10) {
		hh = "0"+hh;
	}
    if (MM<10) {
    	MM = "0"+MM;
	}
    if (hh==0) {
		range = 720;
	}
    $('#hourCreate').val(hh+":"+MM+" "+ampm);
    $('#timeSetting').val(hh+":"+MM+" "+ampm);
    $('#myRange').val(parseInt(range+5));
    
    $('#dateCreate').val(currentDate());
    $('#dateSetting').val(currentDate());
    $('#dateResultSelect').val(currentDate());
  }
  
  function currentDate() {
  	let now = new Date();
    let month = (now.getMonth() + 1);               
    let day = now.getDate();
    if (month < 10) 
        month = "0" + month;
    if (day < 10) 
        day = "0" + day;
    let today = now.getFullYear() + '-' + month + '-' + day;
    
    return today
 } 
  function callAPI(id) {
	  let url = window.location.origin ;
	  console.log(url)
	  $.ajax({
          url: url+'/checklist/api/'+id,
          type: 'GET'
      }).done(function(result) {
          if (result) {
        	  console.log(result)
        	  setDatatoForm(result);
        	 
          }else{
        	  console.log('error!');
          }
      }).fail(function(error) {
		console.log(error)
    	  
      });
}
  
  function setDatatoForm(checklist) {
	  let id = checklist.id;
	  let time = checklist.time;
	  let date = checklist.date
	  let qc = checklist.qc;
	  let lot = checklist.lot;
	  let quantitySatisfied = checklist.quantitySatisfied;
	  let quantityUnsatisfied = checklist.quantityUnsatisfied;
	  let quantityMix = checklist.quantityMix;
	  let remark = checklist.remark;
	  let moisture = checklist.moisture;
	  let ph = checklist.ph;
	  let brix = checklist.brix;
	  let color = checklist.color;
	  let taste = checklist.taste;
	  let impurity = checklist.impurity;
	  
	  let range = getRangeFromDateAndTime(date,time);
	  
	

	  
	  // set value to input
	  $("#id").val(id);
	  $("#dateCreate").val(date);
	  $("#hourCreate").val(time);
	  $("#myRange").val(range);
	  
	  $("#qc").val(qc.username);
	  $("#lotCode").val(lot.lotCode)
	
	  
	  $("#quantitySatisfied").val(quantitySatisfied);
	  $("#quantityUnsatisfied").val(quantityUnsatisfied);
	  $("#quantityMix").val(quantityMix);
	  $("#remark").val(remark);
	  
	  $("#moisture").val(moisture)
	  $("#ph").val(ph);
	  $("#brix").val(brix);

	  $("#impurity").val(impurity);
	  
	  $("#saveCheckList").attr("action","/checklist/update");
	  $("#saveButton").text("Cập nhật");
	  $("#saveButton").removeClass("btn-primary");
	  $("#saveButton").addClass("btn-danger");
	  
	  $("html, body").animate({ scrollTop: 30 }, "slow");
	  $('#card-create').removeClass('hide');
	  // radio handle
	  if (color) {
	  		$("#colorTrue").attr('checked', true);
		  	$("#colorFalse").attr('checked', false);
	  }else{
		  $("#colorTrue").attr('checked', false);
		  	$("#colorFalse").attr('checked', true);
	  }
	  
	  if (taste) {	 
		  
	  		$("#tasteTrue").attr('checked', true);
		  	$("#tasteFalse").attr('checked', false);
	  }else{
		  $("#tasteTrue").attr('checked', false);
		  	$("#tasteFalse").attr('checked', true);
	  }
	  
	  $('#setDefaultByRecord').removeClass('hide');
	  
}
  
  function getRangeFromDateAndTime(date,time){
	  let d = new Date();
	  let h = time.substring(0, 2);
	  d.setHours(h);
	  let hour = format24hTo0h(d);
	  console.log(hour)
	  
	  let range = (parseInt(hour-1)*60);
	  return range+5;
	  
  }
  
  function resetError(modal) {
	  $('#errQC'+modal).text('')
	  $('#errLotCode'+modal).text('')	  
	  $('#errTime'+modal).text('') 
	  $('#errDate'+modal).text('')
	  $('#errMoisture'+modal).text('')
	  $('#errPh'+modal).text('')
	  $('#errBrix'+modal).text('')
	  $('#errImpurity'+modal).text('')
	  $('#errSatisfied'+modal).text('')
	  $('#errUnsatisfied'+modal).text('') 	  
	  $('#errMix'+modal).text('')
	  
	   $('#qc'+modal).removeClass('border-danger');
	   $('#lotCode'+modal).removeClass('border-danger');
	   $('#hourCreate'+modal).removeClass('border-danger');
	   $('#dateCreate'+modal).removeClass('border-danger');
	   $('#moisture'+modal).removeClass('border-danger');
	   $('#ph'+modal).removeClass('border-danger');
	   $('#brix'+modal).removeClass('border-danger');
	   $('#impurity'+modal).removeClass('border-danger');
	   $('#quantitySatisfied'+modal).removeClass('border-danger');
	   $('#quantityUnsatisfied'+modal).removeClass('border-danger');
	   $('#quantityMix'+modal).removeClass('border-danger');
	   
}
  
  function writeError(objectErrors,modal) {
	  for (var object of objectErrors) {
//		   console.log(object.defaultMessage)
          if (object.field == 'time') {
             $('#errTime').text(object.defaultMessage);
             $('#hourCreate').addClass('border-danger');
          }
          if (object.field == 'date') {
       	   $('#errDate'+modal).text(object.defaultMessage);
       	   $('#dateCreate'+modal).addClass('border-danger');
          }
          
          if (object.field == 'moisture') {
       	   $('#errMoisture'+modal).text(object.defaultMessage);
       	   $('#moisture'+modal).addClass('border-danger');
          }
          if (object.field == 'ph') {
       	   $('#errPh'+modal).text(object.defaultMessage);
       	   $('#ph'+modal).addClass('border-danger');
          }
          if (object.field == 'brix') {
       	   $('#errBrix'+modal).text(object.defaultMessage);
       	   $('#brix'+modal).addClass('border-danger');
          }
          if (object.field == 'impurity') {
       	   $('#errImpurity'+modal).text(object.defaultMessage);
       	   $('#impurity'+modal).addClass('border-danger');
          }
          if (object.field == 'quantitySatisfied') {
       	   $('#errSatisfied'+modal).text(object.defaultMessage);       	
       	   $('#quantitySatisfied'+modal).addClass('border-danger');
          }
          if (object.field == 'quantityUnsatisfied') {
       	   $('#errUnsatisfied'+modal).text(object.defaultMessage);
       	   $('#quantityUnsatisfied'+modal).addClass('border-danger');
          }
          if (object.field == 'quantityMix') {
       	   $('#errMix'+modal).text(object.defaultMessage);
       	   $('#quantityMix'+modal).addClass('border-danger');
          }                                             
      }
}
  
  function changeValue(values) {
	 $('#brix').val(values.brix);
	 $('#moisture').val(values.moisture);
	 $('#impurity').val(values.impurity);
	 $('#quantityMix').val(values.quantityMix);
	 $('#quantitySatisfied').val(values.quantitySatisfied);
	 $('#quantityUnsatisfied').val(values.quantityUnsatisfied);
	 $('#qc').val(values.qc);
	 $('#lotCode').val(values.lotCode);
	 
	 $('#brixSetting').val(values.brix);
	 $('#moistureSetting').val(values.moisture);
	 $('#impuritySetting').val(values.impurity);
	 $('#quantityMixSetting').val(values.quantityMix);
	 $('#quantitySatisfiedSetting').val(values.quantitySatisfied);
	 $('#quantityUnsatisfiedSetting').val(values.quantityUnsatisfied);
	 $('#qcSetting').val(values.qc);
	 $('#lotCodeSetting').val(values.lotCode);	 
	 if (values.color) {
		 $('input:radio[name=color][value=false]').attr('checked', false);

		 $('input:radio[name=color][value=true]').attr('checked', true);
		
	 }else{
		 $('input:radio[name=color][value=true]').attr('checked', false);

		 $('input:radio[name=color][value=false]').attr('checked', true);
	
	 }
	 
	 if (values.taste) {
		 $('input:radio[name=taste][value=false]').attr('checked', false);

		 $('input:radio[name=taste][value=true]').attr('checked', true);
	 }else{
		 $('input:radio[name=taste][value=true]').attr('checked', false);

		 $('input:radio[name=taste][value=false]').attr('checked', true);
	 }
  }
  