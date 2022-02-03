<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="studentweb.compus.entity.Secretary,studentweb.compus.entity.Teacher,studentweb.compus.entity.Student" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Welcome to our university</title>
</head>
<body>
        
        <div class="container">
		<h2 class="form-signin-heading">Welcome to University of Macedonia, please register</h2>
		   <c:choose>
		   <c:when test="${secretary.getRole()=='SECRETARY'}">
		   <c:set  var="formAction" value="/Secretary/changeData?id=${secretary.id}"/>
		    </c:when>
		   <c:when test="${student.getRole()=='STUDENT'}">
		     <c:set  var="formAction" value="/Student/changeData?id=${student.id}"/>
		   </c:when>
		   <c:when test="${teacher.getRole()=='TEACHER'}">
		     <c:set  var="formAction" value="/Teacher/changeData?id=${teacher.id}"/>
		   </c:when>
		   <c:otherwise>
		     <c:set  var="formAction" value="/"/>
		   </c:otherwise>
		   </c:choose> 
	
		   <form method="POST" action="${formAction}" >
			<p>
				<label for="firstname" class="sr-only">Firstname</label>
				<c:choose>
				<c:when test="${(secretary!=null) || (teacher!=null) || (student!=null)}">
				<sec:authorize access="hasRole('SECRETARY')"> 
				<input type="text" id="firstname" name="firstname" class="form-control"
					value="${secretary.getName()}"  required/>
				</sec:authorize>
				<sec:authorize access="hasRole('TEACHER')"> 
				<input type="text" id="firstname" name="firstname" class="form-control"
					value="${teacher.getName()}"  required/>
				</sec:authorize>
				<sec:authorize access="hasRole('STUDENT')"> 
				<input type="text" id="firstname" name="firstname" class="form-control"
					value="${student.getName()}"  required/>
				</sec:authorize>
				</c:when>
				<c:otherwise>
				<input type="text" id="firstname" name="firstname" class="form-control"
					placeholder="Firstname"  required/>
				</c:otherwise>
				</c:choose>
			</p>
			<p>
				<label for="lastname" class="sr-only">Lastname</label> 
				<c:choose>
				<c:when test="${(secretary!=null) || (teacher!=null) || (student!=null)}">
				<sec:authorize access="hasRole('SECRETARY')"> 
				<input type="text" id="lastname" name="lastname" class="form-control"
					value="${secretary.getSurname()}" required/>
				</sec:authorize>
				<sec:authorize access="hasRole('TEACHER')"> 
				<input type="text" id="lastname" name="lastname" class="form-control"
					value="${teacher.getSurname()}"  required/>
				</sec:authorize>
				<sec:authorize access="hasRole('STUDENT')"> 
				<input type="text" id="lastname" name="lastname" class="form-control"
					value="${student.getSurname()}" required/>
				</sec:authorize>
				</c:when>
				<c:otherwise>
				<input type="text" id="lastname" name="lastname" class="form-control"
					placeholder="Lastname" required/>
				</c:otherwise>	
				</c:choose>	
			</p>
			<p>
				<label for="email" class="sr-only">Email</label> 
				<c:choose>
				<c:when test="${(secretary!=null) || (teacher!=null) || (student!=null)}">
				<sec:authorize access="hasRole('SECRETARY')"> 
				<input type="email" id="email" name="email" class="form-control"
					value="${secretary.getEmail()}" required/>
				</sec:authorize>
				<sec:authorize access="hasRole('TEACHER')"> 
				<input type="email" id="email" name="email" class="form-control"
					value="${teacher.getEmail()}" required/>
				</sec:authorize>
				<sec:authorize access="hasRole('STUDENT')"> 
				<input type="email" id="email" name="email" class="form-control"
					value="${student.getEmail()}" required/>
				</sec:authorize>
				</c:when>
				<c:otherwise>
				<input type="email" id="email" name="email" class="form-control"
					placeholder="Email" required/>
			    </c:otherwise>
				</c:choose>
			</p>
			<p>
				<label for="username" class="sr-only">Username</label> 
				<c:choose>
				<c:when test="${(secretary!=null) || (teacher!=null) || (student!=null)}">
				<sec:authorize access="hasRole('SECRETARY')"> 
				<input type="text" id="username" name="username" class="form-control"
					value="${secretary.getUsername()}" required/>
				</sec:authorize>
				<sec:authorize access="hasRole('TEACHER')"> 
				<input type="text" id="username" name="username" class="form-control"
					value="${teacher.getUsername()}" required/>
				</sec:authorize>
				<sec:authorize access="hasRole('STUDENT')"> 
				<input type="text" id="username" name="username" class="form-control"
					value="${student.getUsername()}" required/>
				</sec:authorize>
				</c:when>
				<c:otherwise>
				<input type="text" id="username" name="username" class="form-control"
					placeholder="Username" required/>
				</c:otherwise>
			   </c:choose>
			</p>
			
			<p>
				<label for="password" class="sr-only">Password</label> 
				<c:choose>
				<c:when test="${(secretary!=null) || (teacher!=null) || (student!=null)}">
				<sec:authorize access="hasRole('SECRETARY')"> 
				<input type="password" id="password" name="password" class="form-control"
					value="${secretary.password }" required/>
				</sec:authorize>
				<sec:authorize access="hasRole('TEACHER')"> 
				<input type="password" id="password" name="password" class="form-control"
					value="${teacher.password}" required/>
				</sec:authorize>
				<sec:authorize access="hasRole('STUDENT')"> 
				<input type="password" id="password" name="password" class="form-control"
					value="${student.password}" required/>
				</sec:authorize>
				</c:when>
				<c:otherwise>
				<input type="password" id="password" name="password" class="form-control"
					placeholder="Password" required/>
				</c:otherwise>
				</c:choose>
			</p>
			<p>
				<label for="role" class="sr-only">Role</label> 
			<c:choose>
				<c:when test="${(secretary!=null) || (teacher!=null) || (student!=null)}">
				<sec:authorize access="hasRole('SECRETARY')"> 
				<input type="hidden" id="role" name="role" class="form-control"
					value="${secretary.getRole()}" required/>
				</sec:authorize>
				<sec:authorize access="hasRole('TEACHER')"> 
				<input type="hidden" id="role" name="role" class="form-control"
					value="${teacher.getRole()}"required/>
				</sec:authorize>
				<sec:authorize access="hasRole('STUDENT')"> 
				<input type="hidden" id="role" name="role" class="form-control"
					value="${student.getRole()}" required/>
				</sec:authorize>
				</c:when>
				<c:otherwise>
				<label for="role" class="sr-only">Role</label> 
				<input type="text" id="role" name="role" class="form-control"
					placeholder="Role" required/>
				</c:otherwise>	
			</c:choose>
			</p>
			<c:choose>
		    <c:when test="${secretary.getRole()=='SECRETARY'}">
			<button class="btn btn-lg btn-primary btn-block" type="submit">Update-Secretary</button>
			</c:when>
		    <c:when test="${teacher.getRole()=='TEACHER'}">
			<button class="btn btn-lg btn-primary btn-block" type="submit">Update-Teacher</button>
			</c:when>
			<c:when test="${student.getRole()=='STUDENT'}">
			<button class="btn btn-lg btn-primary btn-block" type="submit">Update-Student</button>
			</c:when>	
			<c:otherwise>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
			</c:otherwise>
			</c:choose>
		</form>
	</div>
         
</body>
</html>