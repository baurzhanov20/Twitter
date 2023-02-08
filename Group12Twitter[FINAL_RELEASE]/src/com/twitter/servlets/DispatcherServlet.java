package com.twitter.servlets;

import com.twitter.db.Blogs;
import com.twitter.db.DBManager;
import com.twitter.db.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/act")
public class DispatcherServlet extends HttpServlet {

    private DBManager dbManager;

    public void init(){
        dbManager = new DBManager();
        dbManager.connect();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Users sessionUser = (Users)request.getSession().getAttribute("SESSION_USER");

        boolean userOnline = sessionUser!=null;

        String uri = "";
        String act = request.getParameter("act");

        if(act!=null){
            if(act.equals("add_user")){

                uri = "?error=1";

                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String rePassword = request.getParameter("re_password");
                String fullName = request.getParameter("full_name");

                if(email!=null&&!email.equals("")&&password!=null&&!password.equals("")&&rePassword!=null&&fullName!=null&&!fullName.equals("")){

                    if(password.equals(rePassword)){

                        Users user = dbManager.getUser(email);
                        if(user==null){

                            dbManager.addUser(new Users(null, email, password, fullName));
                            uri = "?success=1";
                        }
                    }

                }

            }else if(act.equals("auth")){

                uri = "?error=1";

                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if(email!=null&&!email.equals("")&&password!=null&&!password.equals("")){

                    Users user = dbManager.getUser(email, password);

                    if(user!=null){

                        request.getSession().setAttribute("SESSION_USER", user);
                        uri = "?";

                    }

                }

            }else if(act.equals("logout")&&userOnline){

                request.getSession().removeAttribute("SESSION_USER");

            }else if(act.equals("addblog")&&userOnline){

                String title = request.getParameter("title");
                String shortContent = request.getParameter("short_content");
                String content = request.getParameter("content");

                dbManager.addBlog(new Blogs(null, title, shortContent, content, sessionUser, null));

            }else if(act.equals("saveblog")&&userOnline){

                String title = request.getParameter("title");
                String shortContent = request.getParameter("short_content");
                String content = request.getParameter("content");
                Long blogId = Long.parseLong(request.getParameter("blog_id"));

                dbManager.saveBlog(new Blogs(blogId, title, shortContent, content, sessionUser, null));

                uri = "?page=readblog&id="+blogId;

            }else if(act.equals("deleteblog")&&userOnline){

                Long blogId = Long.parseLong(request.getParameter("blog_id"));

                dbManager.deleteBlog(blogId, sessionUser.getId());

                uri = "?";

            }
        }

        response.sendRedirect("/"+uri);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = request.getParameter("page");
        String jspPage = "index";

        Users sessionUser = (Users) request.getSession().getAttribute("SESSION_USER");
        boolean userOnline = false;
        if(sessionUser!=null){
            Users userProfile = dbManager.getUser(sessionUser.getEmail());
            if(userProfile!=null&&userProfile.getPassword().equals(sessionUser.getPassword())){
                userOnline = true;
                request.setAttribute("userProfile", userProfile);
            }else{
                request.getSession().removeAttribute("SESSION_USER");
            }
        }

        request.setAttribute("userOnline", userOnline);

        if(page==null){
            page = "home";
        }

        if(page.equals("home")){

            ArrayList<Blogs> blogs = dbManager.getAllBlogs();
            request.setAttribute("blogs", blogs);
            jspPage = "index";

        }else if(page.equals("register")&&!userOnline){

            jspPage = "register";

        }else if(page.equals("login")&&!userOnline){

            jspPage = "login";

        }else if(page.equals("addblog")&&userOnline){

            jspPage = "addblog";

        }else if(page.equals("readblog")){

            jspPage = "404";
            String id = request.getParameter("id");

            Long longId = null;
            Blogs blog = null;

            try {

                longId = Long.parseLong(id);

            }catch (Exception e){

            }

            if(longId!=null){
                blog = dbManager.getBlog(longId);
                if(blog!=null){
                    request.setAttribute("blog", blog);
                    jspPage = "readblog";
                }
            }
        }else if(page.equals("editblog")){

            jspPage = "404";
            String id = request.getParameter("id");

            Long longId = null;
            Blogs blog = null;

            try {

                longId = Long.parseLong(id);

            }catch (Exception e){

            }

            if(longId!=null){
                blog = dbManager.getBlog(longId);
                if(blog!=null&&blog.getAuthor().getId()==sessionUser.getId()){
                    request.setAttribute("blog", blog);
                    jspPage = "editblog";
                }
            }
        }else if(page.equals("search")){

            String key = request.getParameter("key");
            if(key!=null) {
                ArrayList<Blogs> blogs = dbManager.searchBlogs(key);
                request.setAttribute("blogs", blogs);
                request.setAttribute("key", key);
            }
            jspPage = "search";

        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        request.setAttribute("year", formatter.format(date));
        request.getRequestDispatcher("views/"+jspPage+".jsp").forward(request, response);

    }
}
