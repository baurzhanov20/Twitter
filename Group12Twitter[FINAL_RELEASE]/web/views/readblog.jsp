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
                ${blog.title}
            </h1>
            <p>
                ${blog.shortContent}
            </p>
            <p>
                ${blog.content}
            </p>
            <b>
                Posted on ${blog.postDate} by
                <a href="#">${blog.author.fullName}</a>
            </b>
            <br>
            <c:choose>
                <c:when test="${userOnline&&blog.author.id==userProfile.id}">
                    <br>
                    <a href="?page=editblog&id=${blog.id}" class="btn btn-success btn-sm">Edit Blog</a>
                </c:when>
            </c:choose>
            <br>
            <c:choose>
                <c:when test="${userOnline}">
                    <div class="row mt-3">
                        <div class="col-12">
                            <textarea rows="2" class="form-control" id = "comment"></textarea>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-12">
                            <button type="button" id="add_comment_button" class="btn btn-info">Add Comment</button>
                        </div>
                    </div>
                </c:when>
            </c:choose>
            <br>
            <div id = "comments_list">
            </div>
            <br><br>
        </div>


        <%@include file="templates/sidebar.jsp"%>

    </div>

</div>
<!-- /.container -->
<%@include file="templates/footer.jsp"%>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript">

    function loadComments(){
        $.get("ajax", {

            act: "load_comments",
            blog_id : ${param.id}

        }, function(data){

            var comments = JSON.parse(data);
            var comList = "";

            for(var i = 0;i<comments.length;i++){
                comList+="<div class=\"alert alert-secondary\" role=\"alert\">"+comments[i].comment+" by <a href = \"#\">"+comments[i].author.fullName+"</a> at " +comments[i].postDate+"</div>";
            }

            $("#comments_list").html(comList);

        });
    }

    $(document).ready(function(){

        loadComments();

        <c:choose>
            <c:when test="${userOnline}">
                $("#comment").click(function(){
                    $("#comment").attr("rows", 4);
                });

                $("#add_comment_button").click(function(){
                    if($("#comment").val()!=""){
                        $.post("ajax", {

                            blog_id: ${param.id},
                            comment: $("#comment").val(),
                            act: "add_comment"

                        }, function(data){
                            $("#comment").val("");
                            $("#comment").attr("rows", "2");
                            loadComments();
                        });
                    }
                });
            </c:when>
        </c:choose>
    });
</script>


</body>

</html>
