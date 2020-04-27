<%-- 
    Document   : viewActivityDetails
    Created on : 21 Apr 2020, 17:38:26
    Author     : cooke
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
              integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
              crossorigin=""/>
        <script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
                integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew=="
        crossorigin=""></script>
        <style>
            #map{ 
                height: 300px;
                width: 300px;
            }
            thead input {
                width: 100%;
                padding: 3px;
                box-sizing: border-box;
            }
        </style>


        <title>Activity Details</title>
        <sec:authorize access="hasRole('COACH')">
            <%@include file="navBarCoach.jsp"%>
        </sec:authorize>

        <sec:authorize access="hasRole('PLAYER')">
            <%@include file="navBarPlayer.jsp"%>
        </sec:authorize>
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
        <script>
            $(document).ready(function () {
                var table = $('#tableP').DataTable({
                    orderCellsTop: true,
                    fixedHeader: true,
                    responsive: true
                });
            });

        </script>
    <center>
        <h1>Activity Details</h1>
        <br/>
        <b>Distance</b>: ${activity.distance} Meters
        <br/>
        <b>Time</b>: ${activity.elapsedTime} Mins.Sec
        <br/>
        <b>Total Elevation</b>: ${activity.totalElevationGain} Meters
        <br/>
        <b>Exercise Type</b>: ${activity.type} 
        <br/>
        <b>Date</b>: <fmt:formatDate type="date"  pattern="dd-MMM-yyyy" value="${activity.startDate}"/> 
        <br/>

        <div id="map"></div>
        <br/>
        <b>Average Speed</b>: ${activity.averageSpeed} Km/H
        <br/>
        <b>Max Speed</b>: ${activity.maxSpeed} Km/H
        <br/>
        <b>Average Heart Rate</b>: ${activity.averageHeartrate} BPM
        <br/>
        <b>Max Heart Rate</b>: ${activity.maxHeartrate} BPM
        <br/>
        <b>Calories Burnt</b>: ${activity.calories} 
        <br/>

        <h3>Split Breakdown</h3>




        <table class="display compact hover stripe" id="tableP">
            <thead>
                <tr>
                    <th align="left">Split</th>
                    <th align="left">Distance</th>
                    <th align="left">Time</th>
                    <th align="left">Elavation</th>
                    <th align="left">Average Speed</th>
                    <th align="left">Average Heart Rate</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${splits}" var="activity"> 
                    <tr>
                        <td>${activity.split}</td>
                        <td>${activity.distance} Km</td>
                        <td>${activity.time} min.sec</td>
                        <td>${activity.elevation} Meters</td>
                        <td>${activity.averageSpeed} Km/H</td>
                        <td>${activity.averageHeartrate} BPM</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </center>

    <script type="text/javascript">

        // Original code from:
        //* http://facstaff.unca.edu/mcmcclur/GoogleMaps/EncodePolyline/
        //* (which is down as of december 2014)
        (function () {
            'use strict';

            var defaultOptions = function (options) {
                if (typeof options === 'number') {
                    // Legacy
                    options = {
                        precision: options
                    };
                } else {
                    options = options || {};
                }

                options.precision = options.precision || 5;
                options.factor = options.factor || Math.pow(10, options.precision);
                options.dimension = options.dimension || 2;
                return options;
            };

            var PolylineUtil = {

                decode: function (encoded, options) {
                    options = defaultOptions(options);

                    var flatPoints = this.decodeDeltas(encoded, options);

                    var points = [];
                    for (var i = 0, len = flatPoints.length; i + (options.dimension - 1) < len; ) {
                        var point = [];

                        for (var dim = 0; dim < options.dimension; ++dim) {
                            point.push(flatPoints[i++]);
                        }

                        points.push(point);
                    }

                    return points;
                },

                decodeDeltas: function (encoded, options) {
                    options = defaultOptions(options);

                    var lastNumbers = [];

                    var numbers = this.decodeFloats(encoded, options);
                    for (var i = 0, len = numbers.length; i < len; ) {
                        for (var d = 0; d < options.dimension; ++d, ++i) {
                            numbers[i] = Math.round((lastNumbers[d] = numbers[i] + (lastNumbers[d] || 0)) * options.factor) / options.factor;
                        }
                    }

                    return numbers;
                },

                decodeFloats: function (encoded, options) {
                    options = defaultOptions(options);

                    var numbers = this.decodeSignedIntegers(encoded);
                    for (var i = 0, len = numbers.length; i < len; ++i) {
                        numbers[i] /= options.factor;
                    }

                    return numbers;
                },
                decodeSignedIntegers: function (encoded) {
                    var numbers = this.decodeUnsignedIntegers(encoded);

                    for (var i = 0, len = numbers.length; i < len; ++i) {
                        var num = numbers[i];
                        numbers[i] = (num & 1) ? ~(num >> 1) : (num >> 1);
                    }

                    return numbers;
                },

                decodeUnsignedIntegers: function (encoded) {
                    var numbers = [];

                    var current = 0;
                    var shift = 0;

                    for (var i = 0, len = encoded.length; i < len; ++i) {
                        var b = encoded.charCodeAt(i) - 63;

                        current |= (b & 0x1f) << shift;

                        if (b < 0x20) {
                            numbers.push(current);
                            current = 0;
                            shift = 0;
                        } else {
                            shift += 5;
                        }
                    }

                    return numbers;
                }
            };

            // Export Node module
            if (typeof module === 'object' && typeof module.exports === 'object') {
                module.exports = PolylineUtil;
            }

            // Inject functionality into Leaflet
            if (typeof L === 'object') {
                if (!(L.Polyline.prototype.fromEncoded)) {
                    L.Polyline.fromEncoded = function (encoded, options) {
                        return L.polyline(PolylineUtil.decode(encoded), options);
                    };
                }
                if (!(L.Polygon.prototype.fromEncoded)) {
                    L.Polygon.fromEncoded = function (encoded, options) {
                        return L.polygon(PolylineUtil.decode(encoded), options);
                    };
                }

                var encodeMixin = {
                    encodePath: function () {
                        return PolylineUtil.encode(this.getLatLngs());
                    }
                };
                L.PolylineUtil = PolylineUtil;
            }
        })();
    </script>
    <script type="text/javascript">
        var map = L.map('map').setView([52.472058, -8.507980], 11);
        var coords = '${activity.map}';
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);

        var coordinates = L.Polyline.fromEncoded(coords).getLatLngs();
        L.polyline(
                coordinates,
                {
                    color: "red",
                    weight: 5,
                    opacity: .7,
                    lineJoin: 'round'
                }
        ).addTo(map);
    </script>
</body>
</html>
