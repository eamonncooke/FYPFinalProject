<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <sec:authorize access="hasRole('COACH')">
            <%@include file="navBarCoach.jsp"%>
        </sec:authorize>

        <sec:authorize access="hasRole('PLAYER')">
            <%@include file="navBarPlayer.jsp"%>
        </sec:authorize>

    <center>
        <h1>Welcome ${user.firstName}</h1>
        <sec:authorize access="hasRole('PLAYER')">
            <br>
            <h2>Site Navigation</h2>
            <br>
            To view past fitness test results click on the fitness test tab at the top of the screen
            <br>
            Beside that is the activity tab. This will display the activities you have recorded. Contact your coach if it is not up to date
            <br>
        </sec:authorize>

        <sec:authorize access="hasRole('COACH')">
            <h2>Coaches site</h2>
            <br/>
            <form action="<c:url value="\coach\updateRecordedTrainings" />" method="GET">
                <input type="submit" name="action" value="Upload new activities from connected Strava Accounts" />
            </form>
        </sec:authorize>

    </center>

</body>
</html>