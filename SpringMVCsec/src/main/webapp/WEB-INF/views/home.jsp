<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
 <h3>user details are</h3>
 <table>
 <th>USERNAME    </th>                 <th>         EMAIL  </th><br>
<c:forEach var = "i" items = "${Ituser}">
<tr>
<td>name</td><td><c:out value = "${i.getName()}"/></td>  <td>email</td><td><c:out value = "${i.getEmail()}"/></td><br> 
</tr>
 </c:forEach>
 </table>
</body>
</html>