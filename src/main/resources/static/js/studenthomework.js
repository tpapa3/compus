
$(document).ready(function () {

    $("input[name='buttonSend']").click(function () {
	    var courseId = $(this).attr('id');
        var studentId =$(this).attr("class");
        uploadFile(courseId,studentId);
    });

});

function uploadFile(courseId,studentId) {

    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "http://localhost:8080/Student/homework/idc?="+courseId+"&ids?="+studentId,
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {
            console.log(data);
        },
        error: function (e) {
            console.log("ERROR : ", e); 
        }
    });
}