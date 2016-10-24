/*
Blog.java
Object class that contains blog details
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import java.util.Date;

public class Blog
{
    private String blogHeading;
    private String blogBody;
    private Date blogDate;
    //********************************************************************
    public Blog(String bHeading, String bBody, Date date)
    {
        blogHeading = bHeading;
        blogBody = bBody;
        blogDate = date;
    }
    //********************************************************************
    public String getBlogBody() {
        return blogBody;
    }
    //********************************************************************
    public String getBlogHeading() {
        return blogHeading;
    }
    //********************************************************************
    public Date getBlogDate() {
        return blogDate;
    }
}
