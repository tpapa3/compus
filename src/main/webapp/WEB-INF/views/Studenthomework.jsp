<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https:///code.jquery.com//jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/studenthomework.js" />"></script>
<style>
.divCourse{
   border: 2px outset blue;
   background-color: yellow;
}
.divFile{
  border:2px outset green;
}
</style>
<title>Welcome to our university</title>
</head>
 <body>

 <nav class="navbar navbar-inverse ">
  <div class="container-fluid">
   <div class="navbar-header">
    <p class="navbar-brand" >${student.name} ${student.surname}</p>
    </div>
      <ul class="nav navbar-nav navbar-right">
        <li>
        <a class="glyphicon glyphicon-user" href="http://localhost:8080/Student/update?id=${student.id}">Personal Information</a>
        </li>
        <li>
        <a href="http://localhost:8080/Student/courses?id=${student.id}">Courses</a>
        </li>
        <li>
        <a class=" glyphicon glyphicon-log-out" href="http://localhost:8080/Student/logout">Logout</a>
        </li>
       </ul>
      </div>    
  </nav>
  <div class="divCourse">
  <c:set var="name" value="${course.name }"/>
  <c:forEach  items="${courses}" var="courseAll">
  <c:if test="${courseAll.name==name}">
  <h2>${course.name}</h2>
  <p>A few words about ${course.name} , so what is?</p>
  <div>
  <p>${course.content}</p>
  </div>
  </c:if>
  </c:forEach>
  </div>
  <br/>
  
  <h3>Exercises for this week.You must upload a single file</h3>
  
  <br/>
    <div class="divFile">
  	<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
		        <label>File to Upload:</label>
				<input type="file" name="file" />
				<br/>
				<br/>
				<p>Are you ready to send your file?</p>
				<input type="submit" value="Send" name="buttonSend" id="${course.id }" class="${student.id }"/>
		</form>
	</div>
 </body>
 </html>