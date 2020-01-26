package com.example.lab4_rss;

public class Post
{
    private String title;
    private String content;
    private String date;
    private String image;
    private String link;
    private String cachedBitmap;
    public boolean isCached = false;

    public Post(String title, String content, String date, String image, String link) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.image = image;
        this.link = link;
    }

    public Post(Post other)
    {
        title = other.title;
        content = other.content;
        date = other.date;
        image = other.image;
        link = other.link;
        cachedBitmap = other.cachedBitmap;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public String getCachedBitmapString() {
        return cachedBitmap;
    }

    public void setCachedBitmapString(String bitmap) {
        cachedBitmap = bitmap;
    }

}