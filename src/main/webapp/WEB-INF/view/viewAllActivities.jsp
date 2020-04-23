<%-- 
    Document   : viewAllActivities
    Created on : 22 Apr 2020, 11:00:01
    Author     : cooke
--%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Test Results</title>

    <%@include file="navBarCoach.jsp"%>



    <style>
        thead input {
            width: 100%;
            padding: 3px;
            box-sizing: border-box;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script> 
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/plug-ins/1.10.20/sorting/date-dd-MMM-yyyy.js"></script> 
    <script>
        $(document).ready(function () {
            $('#tableP').DataTable({
                columnDefs: [
                    {type: 'date-dd-mmm-yyyy', targets: 3}
                ]
            });
        });

        function checkDelete() {
            return confirm('Are you sure?');
        }
    </script>
    <center><h1>List of Recorded Training for ${activity.playerID.authUserId.firstName}  ${activity.playerID.authUserId.surname}</h1>





        <table class="display compact hover stripe" id="tableP">
            <thead>
                <tr>
                    <th align="left">Name</th>
                    <th align="left">Date</th>
                    <th align="left">Distance</th>
                    <th align="left">Time</th>
                    <th align="left">Type</th>
                    <th align="left">Average Speed</th>
                    <th align="left">Max Speed</th>
                    <th align="left">Average Heart Rate</th>
                    <th align="left">Max Heart Rate</th>
                    <th align="left">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${trainingList}" var="activity"> 
                    <tr>
                        <td>${activity.playerId.authUserId.firstName} ${activity.playerId.authUserId.surname}</td>
                        <td><fmt:formatDate type="date"  pattern="dd-MMM-yyyy" value="${activity.date}"/></td>
                        <td>${activity.distance} Km</td>
                        <td>${activity.movingTime} min.sec</td>
                        <td>${activity.type}</td>
                        <td>${activity.averageSpeed} Km/H</td>
                        <td>${activity.maxSpeed} Km/H</td>
                        <td>${activity.averageHeartrate} BPM</td>
                        <td>${activity.maxHeartrate} BPM</td>
                        <td><a href="\coach\viewIndividualActivity?id=${activity.activityId}">View Activity Details</a></td>
                        
                    </tr>
                </c:forEach>
            </tbody>
        </table></center>
</html>
