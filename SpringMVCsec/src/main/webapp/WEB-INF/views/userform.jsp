<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
  <html>
<head>
    <title>Add user Form</title>
</head>
  <body>
         <h2>Add new User detail</h2> 
       <form:form method="post" action="save" modelAttribute="user" >    
        <table >    
         <tr>    
          <td>Name : </td>   
          <td><form:input path="name"  /></td>  
         </tr>    
         <tr>    
          <td>Email :</td>    
          <td><form:input path="email" /></td>  
         </tr>   
       
         <tr>    
          <td> </td>    
          <td><input type="submit" value="Save" /></td>    
         </tr>    
        </table>    
       </form:form>    
       </body>
       </html>