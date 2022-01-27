/**
 * 
 */
function addCourse(studentId,courseId){
	
	var request = new XMLHttpRequest();

    request.open("GET", "http://localhost:8080/Student/courseAdd?idc="+courseId+"&ids="+studentId,true);
    request.setRequestHeader("Accept", "application/json");
    request.onreadystatechange = function() {
	     var table=true;
        if(this.readyState===4 && this.status === 200) {
	      if(this.responseText==""){
		   alert("cannot add this course");
            table=false;
         }
         if(table){
		  var table2 = document.getElementById("table2");
           var number = table2.rows.length;
	      var course = JSON.parse(this.responseText);           
                row = table2.insertRow(-1);
                var cell0 = row.insertCell(-1);
                cell0.setAttribute("scope","row");
                cell0.innerHTML = number;
                var cell1 = row.insertCell(-1);
                cell1.setAttribute("class","delete");
                cell1.setAttribute("onclick","deleteCourse("+studentId+","+course.id+")");
                cell1.innerHTML='<span class="deletetext">delete </span>'+course.name;
          }
       }
      }
    
    request.onerror = function(){
      console.log(" An error occurred during the transaction");
    }
    request.send();
};

	
function deleteCourse(studentId,courseId){
	
	var request = new XMLHttpRequest();
       
    request.open("GET", "http://localhost:8080/Student/courseDelete?idc="+courseId+"&ids="+studentId,true);
    
    request.onreadystatechange = function() {
	    
        if(this.readyState===4 && this.status === 200) {    
		    var table2 = document.getElementById("table2");
	        var student = JSON.parse(this.responseText);
            table2.innerHTML='';
            var row = table2.insertRow(-1);
            var headerCell0 = document.createElement("TH");
            headerCell0.setAttribute("scope","col");
            headerCell0.innerHTML = '#';
            row.appendChild(headerCell0);
            var headerCell1 = document.createElement("TH");
            headerCell1.setAttribute("scope","col");
            headerCell1.innerHTML = 'MyCourses';
            row.appendChild(headerCell1);
            for (var i = 0; i < student.courses.length; i++) {
            row = table2.insertRow(-1);
                var cell0 = row.insertCell(-1);
                cell0.setAttribute("scope","row");
                cell0.innerHTML = i+1;
                var cell1 = row.insertCell(-1);
                cell1.setAttribute("class","delete");
                cell1.setAttribute("onclick","deleteCourse("+studentId+","+student.courses[i].id+")");
                cell1.innerHTML= '<span class="deletetext"> delete </span>'+student.courses[i].name;
         }
        }
       } 
      request.onerror = function(){
      console.log(" An error occurred during the transaction");
    }
    request.send();
};



