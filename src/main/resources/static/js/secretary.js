/**
 * 
 */
$(document).ready(function() {
	 var teachername = null;
	 var teachervalue = null;
	 var coursename = null;
	 var coursevalue = null;
	 var studentname = null;
	 var studentvalue = null;
	 
  $('#teacherlist').on('change', function() {
	  teachername= $(this).children('option:selected').text();
	  teachervalue= $(this).children('option:selected').val();
	 $("#teacherinput").val(teachername);
  });
  
  $('#courselist').on('change', function() {
	  coursename=  $(this).children("option:selected").text();
	  coursevalue= $(this).children("option:selected").val();
	 $("#courseinput").val(coursename);
  });
  
  $('#studentlist').on('change', function() {
	  studentname=  $(this).children("option:selected").text();
	  studentvalue= $(this).children("option:selected").val();
	 $("#studentinput").val(studentname);
  });
  
	$('#teacher-course').on('click',function(){
		 $("#teacherinput").val("");
         $("#courseinput").val("");
	  $.ajax({
		  type:'POST',
		  url:'/Secretary/TeacherPage',
		  dataType:'json',
		  success:function(msg){
			  console.log(msg);
		  }, 
		  data:{idt:teachervalue,idc:coursevalue}	  
		  });
	  });
	$('#student-course').on('click',function(){
          $("#studentinput").val("");
          $("#courseinput").val("");
		  $.ajax({
			  type:'POST',
			  url:'/Secretary/StudentPage',
			  dataType:'json',
			  success:function(msg){
				  console.log(msg);
			  }, 
			data:{ids:studentvalue,idc:coursevalue}	 
		  });
	  }); 
});
