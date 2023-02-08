package com.twitter.servlets;

import com.google.gson.Gson;
import com.twitter.db.Blogs;
import com.twitter.db.Comments;
import com.twitter.db.DBManager;
import com.twitter.db.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(value = "/ajax")
public class AjaxServlet extends HttpServlet {

    private DBManager dbManager;

    public void init(){
        dbManager = new DBManager();
        dbManager.connect();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String act = request.getParameter("act");
        PrintWriter out = response.getWriter();
        Users user = (Users) request.getSession().getAttribute("SESSION_USER");
        boolean userOnline = user!=null;

        if(act!=null){

            if(act.equals("add_comment")&&userOnline){

                try {

                    Long blogId = Long.parseLong(request.getParameter("blog_id"));
                    String comment = request.getParameter("comment");
                    Blogs temp = new Blogs();
                    temp.setId(blogId);
                    dbManager.addComment(new Comments(null,temp,user, comment,null));

                }catch (Exception e){
                    out.print("{\"error\":\"1\"}");
                }
            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String act = request.getParameter("act");
        PrintWriter out = response.getWriter();

        if(act!=null){
            if(act.equals("validate")){

                String email = request.getParameter("email");
                Users user = dbManager.getUser(email);

                if(user==null){

                    out.print("{\"message\":\"user_added\", \"result\": \"success\"}");

                }else{

                    out.print("{\"message\":\"user_exists\", \"result\": \"error\"}");

                }

            }else if(act.equals("auth")){

                String email = request.getParameter("email");
                String password = request.getParameter("password");

                Users user = dbManager.getUser(email, password);

                if(user!=null){

                    out.print("{\"message\":\"user_auth\", \"result\": \"success\"}");

                }else{

                    out.print("{\"message\":\"incorrect_login_password\", \"result\": \"error\"}");

                }

            }else if(act.equals("load_comments")){

                try{

                    Long blogId = Long.parseLong(request.getParameter("blog_id"));

                    ArrayList<Comments> comments = dbManager.getComments(blogId);
                    Gson gson = new Gson();
                    String jsonValue = gson.toJson(comments);
                    out.print(jsonValue);

                }catch (Exception e){

                    out.print("{\"error\": \"1\"}");

                }
            }
        }

    }
}
