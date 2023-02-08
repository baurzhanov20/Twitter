package com.twitter.db;

import java.util.Date;

public class Comments {

    private Long id;
    private Blogs blog;
    private Users author;
    private String comment;
    private Date postDate;

    public Comments() {
    }

    public Comments(Long id, Blogs blog, Users author, String comment, Date postDate) {
        this.id = id;
        this.blog = blog;
        this.author = author;
        this.comment = comment;
        this.postDate = postDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blogs getBlog() {
        return blog;
    }

    public void setBlog(Blogs blog) {
        this.blog = blog;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
