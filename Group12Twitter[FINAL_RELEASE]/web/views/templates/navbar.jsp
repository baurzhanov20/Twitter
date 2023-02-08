<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-info fixed-top">
    <div class="container">
        <a class="navbar-brand" href="?">Twitter</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <c:choose>
            <c:when test="${userOnline}">
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="?page=home">${userProfile.fullName}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="?page=addblog">Add Blog</a>
                        </li>
                        <li class="nav-item">
                            <a href="JavaScript:void(0)" class="nav-link" onclick="logout()">Logout</a>
                        </li>
                    </ul>
                    <form action="" method="post" id = "logout_form">
                        <input type="hidden" value="logout" name="act">
                    </form>
                    <script type="text/javascript">
                        function logout(){
                            document.getElementById("logout_form").submit();
                        }
                    </script>
                </div>
            </c:when>
            <c:otherwise>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="?page=home">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="?page=login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="?page=register">Register</a>
                        </li>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</nav>