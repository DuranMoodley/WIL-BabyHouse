package lalucia.babyhouse.babyhouse;

public class Blog
{
    private String blogHeading;
    private String blogBody;
    //********************************************************************
    public Blog(String bHeading, String bBody)
    {
        blogHeading = bHeading;
        blogBody = bBody;
    }
    //********************************************************************
    public String getBlogBody() {
        return blogBody;
    }
    //********************************************************************
    public String getBlogHeading() {
        return blogHeading;
    }
}
