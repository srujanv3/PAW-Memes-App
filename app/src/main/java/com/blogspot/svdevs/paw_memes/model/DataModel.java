package com.blogspot.svdevs.paw_memes.model;

public class DataModel  {

    String imageUrl;
    String id; // not used in this project, added for further modifications

    public DataModel(String imageUrl, String id) {
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
