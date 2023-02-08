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
<script src="https://cdn.ckeditor.com/ckeditor5/12.0.0/classic/ckeditor.js"></script>

<body>

<%@include file="templates/navbar.jsp"%>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Entries Column -->
        <div class="col-md-8">
            <h1 class="my-4">
            </h1>

            <form action="" method="post" id = "edit_form_id">
                <input type="hidden" name="act" value="saveblog" id = "act">
                <input type="hidden" name="blog_id" value="${blog.id}">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">Title</label>
                    <div class="col-sm-9">
                        <input type="text" name="title" class="form-control"placeholder="Title" value = "${blog.title}">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">Short Content</label>
                    <div class="col-sm-9">
                        <textarea name="short_content" id = "short_content">${blog.shortContent}</textarea>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">Content</label>
                    <div class="col-sm-9">
                        <textarea name="content" id = "content">${blog.content}</textarea>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-12">
                        <button type="submit" class="btn btn-primary float-right">SAVE BLOG</button>
                        <button type="button" class="btn btn-danger float-right mr-2" onclick="deleteBlog()">DELETE</button>
                    </div>
                </div>
            </form>

        </div>


        <%@include file="templates/sidebar.jsp"%>

    </div>
    <script>
        ClassicEditor
            .create( document.querySelector( '#content' ) )
            .catch( error => {
            console.error( error );
        } );
        ClassicEditor
            .create( document.querySelector( '#short_content' ) )
            .catch( error => {
            console.error( error );
        } );

    </script>
</div>
<!-- /.container -->
<%@include file="templates/footer.jsp"%>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script>
    function deleteBlog(){
        var conf = confirm("Are you sure?");
        if(conf){
            $("#act").val("deleteblog");
            $("#edit_form_id").submit()
        }
    }
</script>

</body>

</html>
