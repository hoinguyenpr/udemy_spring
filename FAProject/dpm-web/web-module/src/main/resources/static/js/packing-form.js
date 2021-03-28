/**
 * Author by HoiNX1
 */


function updatePackingFormByID(packingFormID){
    
 $.get({
         url: 'getById/' + packingFormID,

         success: function (packingFormDTO){
            $("#id").val(packingFormDTO.id);
            $("#typeProductCode").val(packingFormDTO.typeProductID);
            $("#machineID").val(packingFormDTO.machineID);
            $("#createManID").val(packingFormDTO.createManID);
            $("#packingDate").val(packingFormDTO.packingDate);
            $("#semi_Product_Lot").val(packingFormDTO.semi_Product_Lot);
            $("#timePacking").val(packingFormDTO.timePacking);
            $("#finished_Product_Lot").val(packingFormDTO.finished_Product_Lot);
            $("#quanity").val(packingFormDTO.quanity);

            if(packingFormDTO.shift == 1){
                $("#shift1").attr("checked", true);
            }else {
                $("#shift3").attr("checked", true);
            }
            
            $("#packingID").val(packingFormDTO.packingID);
            $("#foremanID").val(packingFormDTO.foreManID);
            $("#typeOfPackingID").val(packingFormDTO.typeOfPackingID);
            $("#inChargeManID").val(packingFormDTO.inChargeManID);
            $("#packingQuanity").val(packingFormDTO.packingQuanity);
            $("#sttNo").val(packingFormDTO.sttNo);

         },
         error : function(e) {
            swal("Lưu", "Lưu dữ liệu thất bại!", "error");
          console.log("ERROR: ", e);
        }
     }) 
 };


function deletePackingFormById(packingFormID){
    swal({
        title: 'Xác nhận xóa?',
        text: "Dữ liệu sẽ được xóa hoàn toàn!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3f51b5',
        cancelButtonColor: '#ff4081',
        confirmButtonText: 'Great ',
        buttons: {
            cancel: {
                text: "Hủy",
                value: "null",
                visible: true,
                className: "btn btn-danger",
                closeModal: true,
            },
            confirm: {
                text: "Xóa",
                value: "true",
                visible: true,
                className: "btn btn-primary",
                closeModal: true
            }
        }
    }).then(function (value){
        if(value == "true"){
            $.ajax({
                type: "GET",
                url: "deleteById/" + packingFormID,

                success: function (data) {
                    swal("Xóa!", "Xóa dữ liệu thành công!", "success");
                    document.location.href = "/packing-form/list-packing-form";
                },
                error: function () {
                    swal("Xóa!", "Xóa dữ liệu thất bại!)", "error");
                }
            });
        }
    });
};


