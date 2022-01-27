<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<title>Welcome to our university</title>
</head>
<body>
<nav class="navbar navbar-inverse ">
  <div class="container-fluid">
   <div class="navbar-header">
    <p class="navbar-brand" >${teacher.name} ${teacher.surname}</p>
    </div>
      <ul class="nav navbar-nav navbar-right">
        <li>
        <a class="glyphicon glyphicon-user" href="/Teacher/update?id=${teacher.id}">Personal Information</a>
        </li>
        <li>
        <a class=" glyphicon glyphicon-log-out" href="/Teacher/logout">Logout</a>
        </li>
       </ul>
      </div>    
  </nav>
     <c:forEach var="course" items="${courses}">
  <div id="accordion">
    <div class="card">
      <div class="card-header">
        <a class="btn" data-bs-toggle="collapse" href="${'#collapse'+=course.id}">
          <c:out value="${course.name}"></c:out>
        </a>
      </div>
      <div id="${'collapse' +=course.id}" class="collapse" data-bs-parent="#accordion">
        <div class="card-body">
            <c:out value="${course.content}"></c:out>
        </div>
      </div>
    </div>
</div>
   </c:forEach>
   
   <br/>
 <div class="container">
  <div class="row">
    <div class="col">   
 <p>Secretary's Hours</p>
 <p>Monday 10:00-12:00</p>
 <p>Wednesday 10:00-12:00</p>
 <p>Friday 10:00-12:00</p>
    </div>
    <div class="col">  
 <p>Teacher's Hours</p>
 <p>Tuesday 11:00-12:00</p>
 <p>Thursday 11:00-12:00</p>
    </div>
  </div>
 </div>
<br/>
      <jsp:include page="footer.jsp"/>
</body>
</body>
</html>