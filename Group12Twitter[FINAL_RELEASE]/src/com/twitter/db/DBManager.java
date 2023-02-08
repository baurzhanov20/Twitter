package com.twitter.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class DBManager {

    private Connection connection;

    public void connect() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/group12twitter?useUnicode=true&serverTimezone=UTC","root", ""
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Users getUser(String email){

        Users user = null;

        try{

            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM users WHERE email = ?");

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                user = new Users(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("full_name")
                );

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;

    }

    public Users getUser(String email, String password){

        Users user = null;

        try{

            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = SHA1(?)");

            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                user = new Users(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("full_name")
                );

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;

    }

    public void addUser(Users user){
        try{

            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "INSERT INTO users (email, password, full_name) " +
                            "VALUES (?,SHA1(?),?)");

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addBlog(Blogs blog){
        try{

            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "INSERT INTO blogs (id, title, short_content, content, user_id, post_date) " +
                            "VALUES (NULL, ?, ?, ?, ?, NOW())");

            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getShortContent());
            statement.setString(3, blog.getContent());
            statement.setLong(4, blog.getAuthor().getId());

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveBlog(Blogs blog){
        try{

            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "UPDATE blogs " +
                            "SET " +
                            "title = ?, " +
                            "short_content = ?, " +
                            "content = ? " +
                            "WHERE id = ? AND user_id = ? ");

            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getShortContent());
            statement.setString(3, blog.getContent());
            statement.setLong(4, blog.getId());
            statement.setLong(5, blog.getAuthor().getId());


            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteBlog(Long id, Long userId){
        try{

            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM blogs " +
                            "WHERE id =? AND user_id = ?");

            statement.setLong(1, id);
            statement.setLong(2, userId);

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Blogs> getAllBlogs(){

        ArrayList<Blogs> blogs = new ArrayList<>();

        try{

            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "SELECT b.id, b.title, b.short_content, b.content, b.user_id, b.post_date, u.full_name, u.email " +
                            "FROM blogs b " +
                            "LEFT OUTER JOIN users u ON u.id = b.user_id ORDER BY b.post_date DESC");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                Long id = resultSet.getLong("id");
                Long userId = resultSet.getLong("user_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String shortContent = resultSet.getString("short_content");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                Date postDate = resultSet.getDate("post_date");

                blogs.add(new Blogs(id, title, shortContent, content, new Users(userId, email, null, fullName), postDate));

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return blogs;

    }
    public Blogs getBlog(Long id){

        Blogs blog = null;

        try{

            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "SELECT b.id, b.title, b.short_content, b.content, b.user_id, b.post_date, u.full_name, u.email " +
                            "FROM blogs b " +
                            "LEFT OUTER JOIN users u ON u.id = b.user_id " +
                            "WHERE b.id = ?");

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){

                Long userId = resultSet.getLong("user_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String shortContent = resultSet.getString("short_content");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                Date postDate = resultSet.getDate("post_date");

               blog = new Blogs(id, title, shortContent, content, new Users(userId, email, null, fullName), postDate);

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return blog;

    }

    public ArrayList<Blogs> searchBlogs(String key){

        key = key.toLowerCase();

        ArrayList<Blogs> blogs = new ArrayList<>();

        try{

            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "SELECT DISTINCT b.id, b.title, b.short_content, b.content, b.user_id, b.post_date, u.email, u.full_name " +
                            "FROM blogs b " +
                            "LEFT OUTER JOIN users u ON u.id = b.user_id " +
                            "WHERE (LOWER(b.title) LIKE ?) OR (LOWER(b.short_content) LIKE ?) OR " +
                            "(LOWER(b.content) LIKE ?) ");

            statement.setString(1, "%"+key+"%");
            statement.setString(2, "%"+key+"%");
            statement.setString(3, "%"+key+"%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                Long id = resultSet.getLong("id");
                Long userId = resultSet.getLong("user_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String shortContent = resultSet.getString("short_content");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                Date postDate = resultSet.getDate("post_date");

                blogs.add(new Blogs(id, title, shortContent, content, new Users(userId, email, null, fullName), postDate));

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return blogs;

    }

    public void addComment(Comments comment){
        try{

            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "INSERT INTO comments (id, user_id, blog_id, comment, post_date) " +
                            "VALUES (NULL, ?, ?, ?, NOW())");

            statement.setLong(1, comment.getAuthor().getId());
            statement.setLong(2, comment.getBlog().getId());
            statement.setString(3, comment.getComment());

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Comments> getComments(Long blogId){

        ArrayList<Comments> comments = new ArrayList<>();

        try{

            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "SELECT c.id, c.user_id authorId, u.full_name, u.email, c.blog_id, c.comment, c.post_date " +
                            "FROM comments c " +
                            "LEFT OUTER JOIN users u ON u.id = c.user_id " +
                            "WHERE c.blog_id = ? " +
                            "ORDER BY c.post_date DESC ");

            statement.setLong(1, blogId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                Long id = resultSet.getLong("id");
                Long authorId = resultSet.getLong("authorId");
                String comment = resultSet.getString("comment");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                Date postDate = resultSet.getDate("post_date");

                comments.add(new Comments(id, new Blogs(blogId, null, null, null, null, null), new Users(authorId, email, null, fullName), comment, postDate));

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return comments;

    }

}
