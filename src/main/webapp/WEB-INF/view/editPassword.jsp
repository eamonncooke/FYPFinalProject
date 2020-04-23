<%-- 
    Document   : editPassword
    Created on : 23 Apr 2020, 11:27:43
    Author     : cooke
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Password</title>
        <%@include file="navBarCoach.jsp"%>
        <script>
            function checkEdit() {
                return confirm('Are you sure?');
            }
            function required()
            {
                var empt = document.form1.text1.value;
                if (empt === "")
                {
                    alert("Please input a Value");
                    return false;
                } else
                {
                    alert('Password updated');
                    return true;
                }
            }
        </script>
    <center>
        <h3>Change ${user.firstName}'s Password</h3>

        <form:form name="form1" method="POST" action="/coach/insertPassword" modelAttribute="user" onsubmit="required()">

            <table>
                <tr>
                    <td><form:input path="authUserId" type="hidden" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td><form:input path="firstName" type="hidden" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td><form:input path="surname" type="hidden" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td><form:input path="email" type="hidden" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td><form:label path="password">Password</form:label></td>
                    <td><form:input name="text1" path="password"/></td>
                    <td style="color:red"><form:errors path="password"/></td>
                </tr>
                <tr>
                    <td><form:input path="role" type="hidden" readonly="readonly"/></td>
                </tr>


                <tr>
                    <td><input type="submit" value="Change Password" onclick="return checkEdit()"/></td>
                </tr>
            </table>
        </form:form>
    </center>
</body>
</html>

