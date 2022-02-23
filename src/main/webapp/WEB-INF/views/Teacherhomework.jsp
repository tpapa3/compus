<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Welcome to our university</title>
<style>
.divCourse{
   border: 2px outset blue;
   background-color: yellow;
}
</style>
</head>
 <body>

 <nav class="navbar navbar-inverse ">
  <div class="container-fluid">
   <div class="navbar-header">
    <p class="navbar-brand" >${teacher.name} ${teacher.surname}</p>
    </div>
      <ul class="nav navbar-nav navbar-right">
        <li>
        <a class="glyphicon glyphicon-user" href="http://localhost:8080/Student/update?id=${teacher.id}">Personal Information</a>
        </li>
        <li>
        <a class=" glyphicon glyphicon-log-out" href="http://localhost:8080/Teacher/logout">Logout</a>
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
  <p>The files have been uploaded until now</p>  
  
   <table class="table" id="table1">
      <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">FileName</th>
      <th scope="col">DownLoad Link</th>
      <th scope="col">StudentName</th>
      <th scope="col">StudentLastName</th>
    </tr>
  </thead>
  </tbody>
  <c:forEach var="file" items="${files}">
   <tr>
     <td>${file.id}</td>
     <td>${file.docName}</td>
     <td><a href="http://localhost:8080/Teacher/downloadfile/${file.id}">Download</a></td>
     <td>${file.student.getName()}</td>
     <td>${file.student.getSurname()}</td>
   </tr>
  </c:forEach>
  </tbody>
  </table>
  </body>
 </html> 