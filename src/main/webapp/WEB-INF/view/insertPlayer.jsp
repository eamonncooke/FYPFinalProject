<%-- 
    Document   : insertPlayer
    Created on : 23 Apr 2020, 15:43:07
    Author     : cooke
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Player</title>
        <%@include file="navBarCoach.jsp"%>
        <form:form method="POST" action="/coach/insertPlayer" modelAttribute="player">
        <center>
        <h2>Insert New Player</h2>
        <table>
            <tr>
                <td><form:input path="playerId" type="hidden" value="${playerID}" readonly="readonly"/></td>
            </tr>
            <tr>
                <td><form:input path="authUserId.authUserId" type="hidden" value="${authUserID}" readonly="readonly"/></td>
            </tr>
            <tr>
                <td><form:label path="authUserId.firstName">Name</form:label></td>
                <td><form:input path="authUserId.firstName"/></td>
                <td style="color:red"><form:errors path="authUserId.firstName"/></td>
            </tr>
            <tr>
                <td><form:label path="authUserId.surname">Surname</form:label></td>
                <td><form:input path="authUserId.surname"/></td>
                <td style="color:red"><form:errors path="authUserId.surname"/></td>
            </tr>
            <tr>
                <td><form:label path="authUserId.email">Email</form:label></td>
                <td><form:input path="authUserId.email"/></td>
                <td style="color:red"><form:errors path="authUserId.email"/></td>
            </tr>
            <tr>
                <td><form:label path="authUserId.password">Password</form:label></td>
                <td><form:input path="authUserId.password"/></td>
                <td style="color:red"><form:errors path="authUserId.password"/></td>
            </tr>
            <tr>
                <td><form:label path="postion">Position</form:label></td>
                <td><form:input path="postion" /></td> 
                <td style="color:red"><form:errors path="postion"/></td>
            </tr>
            <tr>
                <td><form:label path="dob">Date of Birth</form:label></td>
                <fmt:formatDate value="${yourObject.date}" var="dateString" pattern="dd/MM/yyyy" />
                <td><form:input value="${now}" type="date" path="dob"/></td>
                <td style="color:red"><form:errors path="dob"/></td>
            </tr>
            <tr>
                <td><form:label path="weight">Weight(KG)</form:label></td>
                <td><form:input path="weight"/></td>
                <td style="color:red"><form:errors path="weight"/></td>
            </tr>
            <tr>
                <td><form:label path="height">Height(cm)</form:label></td>
                <td><form:input path="height"/></td>
                <td style="color:red"><form:errors path="height"/></td>
            </tr>
            
            <tr>
                <td><form:input path="authUserId.role" type="hidden" value="${role}" readonly="readonly"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit!"/></td>
            </tr>
        </table>
    </form:form>
        </center>
</body>
</html>
