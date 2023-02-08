<!DOCTYPE html>
<html lang="en">

<head>

    <%@include file="templates/head.jsp"%>
</head>

<body>
<%@include file="templates/navbar.jsp"%>
<!-- Page Content -->
<div class="container" style="min-height: 700px;">
    <div class="row">
        <!-- Blog Entries Column -->
        <div class="col-md-6 offset-3">

            <h1 class="my-4">
            </h1>
            <div class="alert alert-danger" style="display:none;" role="alert" id = "message">
            </div>
            <form action="" method="post" id = "login_form">
                <input type="hidden" value="auth" name="act">
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label">Email</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" placeholder="Email" name="email" id="email">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label">Password</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" placeholder="Password" name="password" id = "password">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-12">
                        <button type="button" class="btn btn-success float-right" id = "login">LOGIN</button>
                    </div>
                </div>
            </form>

        </div>

    </div>
    <!-- /.row -->

</div>
<!-- /.container -->
<%@include file="templates/footer.jsp"%>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {

        $("#login").click(function () {

            var ok = true;

            if($("#email").val()==""){
                ok = false;
                $("#message").html("Complete email!");
            }

            if($("#password").val()==""){
                ok = false;
                $("#message").html("Complete password!");
            }

            if(ok){

                validate();

            }else{

                $("#message").fadeIn();

            }

        });

        function validate(){
            
            $.get("/ajax", {

                act: "auth",
                email: $("#email").val(),
                password: $("#password").val()

            }, function (data) {

                var json = JSON.parse(data);

                if(json.result=="error"){

                    $("#message").html("Incorrect email or password");
                    $("#message").fadeIn();
                }else{
                    $("#login_form").submit();
                }



            });
            
        }

    });
</script>

</body>

</html>
