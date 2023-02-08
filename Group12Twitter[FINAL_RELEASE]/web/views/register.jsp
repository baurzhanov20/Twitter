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
            <form action="" method="post" id = "register_form">
                <input type="hidden" value="add_user" name="act">
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
                    <label class="col-sm-4 col-form-label">Re-Password</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" placeholder="Re-Password" name="re_password" id = "re_password">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label">Full Name</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" placeholder="Full Name" name="full_name" id = "full_name">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-12">
                        <button type="button" class="btn btn-success float-right" id = "register">REGISTER</button>
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

        $("#register").click(function () {

            var ok = true;

            if($("#email").val()==""){
                ok = false;
                $("#message").html("Complete email!");
            }

            if($("#password").val()!=$("#re_password").val()){
                ok = false;
                $("#message").html("Passwords are not equal!");
            }

            if($("#password").val()==""){
                ok = false;
                $("#message").html("Complete password!");
            }
            if($("#re_password").val()==""){
                ok = false;
                $("#message").html("Complete re-password!");
            }

            if($("#full_name").val()==""){
                ok = false;
                $("#message").html("Complete full name!");
            }

            if(ok){

                validate();

            }else{

                $("#message").fadeIn();

            }

        });

        function validate(){
            
            $.get("/ajax", {

                act: "validate",
                email: $("#email").val()

            }, function (data) {

                var json = JSON.parse(data);

                if(json.result=="error"){
                    $("#message").html("User already exists");
                    $("#message").fadeIn();
                }else{
                    $("#register_form").submit();
                }



            });
            
        }

    });
</script>

</body>

</html>
