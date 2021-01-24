package com.example.wallpaperapp;

public class WallpaperModel {
    private int id;
    private String originalUrl, mediumUrl;
    private String nextPage;

    public WallpaperModel() {

    }

    public WallpaperModel(int id, String originalUrl, String mediumUrl, String nextPage) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.mediumUrl = mediumUrl;
        this.nextPage=nextPage;

    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }
}
