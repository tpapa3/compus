<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="<c:url value="/css/secretary.css"/>" >
<script src="https:///code.jquery.com//jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/secretary.js" />"></script>
<title>Welcome to our university</title>
</head>
  
<body>

 <nav class="navbar navbar-inverse ">
  <div class="container-fluid">
   <div class="navbar-header">
    <p class="navbar-brand" >${secretary.getName()} ${secretary.getSurname()}</p>
    </div>
      <ul class="nav navbar-nav navbar-right">
        <li>
        <a class="glyphicon glyphicon-user" href="Secretary/update?id=${secretary.getId()}">Personal Information</a>
        </li>
        <li>
        <a class=" glyphicon glyphicon-log-out" href="Secretary/logout">Logout</a>
        </li>
       </ul>
      </div>    
  </nav>
     <h1 align="center">Welcome to SecretaryPage it's time to work</h1>
   
  <div class="row">
   <div class="column">   
         <h2>Select teacher</h2>
     <select id="teacherlist">
      <c:forEach var="teacher" items="${teachers}">
        <option value="${teacher.id}">${teacher.name}-${teacher.surname}
        </option>
     </c:forEach>
    </select>  
    <br/>
    <br/>
    <input type="text" id="teacherinput" >
   </div>
       
   <div class="column">
     <h2>Select course</h2>
    <select id="courselist">
     <c:forEach var="course" items="${courses}">
         <option value="${course.id}">${course.name}
         </option>
     </c:forEach>
    </select>
    <br/>
    <br/>
     <input type="text" id="courseinput">
     <br/>
    <br/>
   </div>
       
   <div class="column">
   <h2>Select student</h2>
     <select id="studentlist">
      <c:forEach var="student" items="${students}">
        <option value="${student.id}">${student.name}-${student.surname}
        </option>
     </c:forEach>
    </select>   
      <br/>
    <br/>
    <input type="text" id="studentinput" >
   </div>
  </div> 
  <div class="row">
    <div class="col-6 d-flex justify-content-center"><button type="button" id="teacher-course">teacher-course</button></div>
    <div class="col-6 "><button type="button" id="student-course">student-course</button></div> 
  </div>
  
 <p>Work Hours</p>
 <p>Monday 10:00-12:00</p>
 <p>Wednesday 10:00-12:00</p>
 <p>Friday 10:00-12:00</p>
 
        <jsp:include page="footer.jsp"/>
     
</body>
</html>