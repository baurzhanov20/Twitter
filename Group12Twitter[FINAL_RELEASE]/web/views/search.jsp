<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

<%@include file="templates/head.jsp"%>
<style>
    img{
        width: 100%;
    }
</style>
</head>

<body>

<%@include file="templates/navbar.jsp"%>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Entries Column -->
        <div class="col-md-8">

            <h1 class="my-4">
                Search result for: "${key}"
            </h1>

            <c:forEach items="${blogs}" var="blog">
                <div class="card mb-4">
                    <div class="card-body">
                        <h2 class="card-title">${blog.title}</h2>
                        <p class="card-text">
                            ${blog.shortContent}
                        </p>
                        <a href="?page=readblog&id=${blog.id}" class="btn btn-primary">Read More &rarr;</a>
                    </div>
                    <div class="card-footer text-muted">
                        Posted on ${blog.postDate} by
                        <a href="#">${blog.author.fullName}</a>
                    </div>
                </div>
            </c:forEach>


            <!-- Pagination -->
            <ul class="pagination justify-content-center mb-4">
                <li class="page-item">
                    <a class="page-link" href="#">&larr; Older</a>
                </li>
                <li class="page-item disabled">
                    <a class="page-link" href="#">Newer &rarr;</a>
                </li>
            </ul>

        </div>

        <%@include file="templates/sidebar.jsp"%>

    </div>
    <!-- /.row -->

</div>
<!-- /.container -->
<%@include file="templates/footer.jsp"%>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
