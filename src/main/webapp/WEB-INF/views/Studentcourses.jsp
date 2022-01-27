<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/js/student.js" />"></script>
<link rel="stylesheet" href="<c:url value="/css/student.css"/>" >
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
        <a class="glyphicon glyphicon-user" href="http://localhost:8080/Student/update?id=${student.getId()}">Personal Information</a>
        </li>
        <li>
        <a class=" glyphicon glyphicon-log-out" href="http://localhost:8080/Student/logout">Logout</a>
        </li>
       </ul>
      </div>    
  </nav>

 <div class="row">
  <div class="col d-flex justify-content-center">
     <table class="table" id="table1">
      <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Courses</th>
    </tr>
  </thead>
     <tbody>
     <c:forEach items="${courses}" var= "course" varStatus="i">
     <tr>
       <th scope="row">${i.count}</th>
       <td class="add" onclick="addCourse(${student.id},${course.id})"><span class="addtext">add</span>
       ${course.name}</td>
     </tr>
     </c:forEach>
     </tbody>
     </table>
  </div>
  <div class="col d-flex justify-content-center">
         <table class="table" id="table2">
      <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">MyCourses</th>
    </tr>
  </thead>
     <tbody>
     <c:forEach items="${stdCourses}" var= "stdcourse" varStatus="i">
     <tr>
       <th scope="row">${i.count}</th>
       <td  class="delete" onclick="deleteCourse(${student.id},${stdcourse.id})"><span class="deletetext"> delete</span>
       ${stdcourse.name}</td>
     </tr>
     </c:forEach>
     </tbody>
     </table>
  </div>
 </div>
     <br/>    
     <h2 align="center">Announcement</h2>
     <p align="center">The exams will be  cancelled beacause of snow , so the exams will begin in two weeks in monday at 9 am-11 am in class 203.<p>
     <p align="center">The courses statement ends of 3 February 2022 at 11:59 pm.<p>
     <br/>
          <jsp:include page="footer.jsp"/>
</body>
</html>       