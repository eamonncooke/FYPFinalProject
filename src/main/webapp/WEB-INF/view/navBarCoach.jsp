<%-- 
    Document   : navBarCoach
    Created on : 28 Mar 2020, 11:47:48
    Author     : cooke
--%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/">Track Your Team</a>
            </div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">Home</a></li>
                <li><a href="\coach">Players</a></li>
                <li><a href="\coach\viewResults">Fitness Test</a></li>
                <li><a href="\coach\viewAllActivity">Activity Log</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>Coach</li>
                <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </nav>