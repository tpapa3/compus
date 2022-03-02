
$(document).ready(function () {

    $("input[name='send']").click(function () {
	    var fileId = $(this).attr('id');
        gradeFile(fileId);
    });

   function gradeFile(fileId){
   
   var grade=$('#grade').val();
   
     $.ajax({
			  type:'POST',
			  url:'/Teacher/CourseFileGrade?id='+fileId,
			  success:function(result){
				  console.log(result);
				  if(result!="pass" && result!="fail"){
				   alert(result);
				  }else{
				  $('#result').append(result);
				  }
			  }, 
			  error:function(e){
			    console.log(e);
			  },
			data:{fileGrade:grade}	 
		  });
   }
});