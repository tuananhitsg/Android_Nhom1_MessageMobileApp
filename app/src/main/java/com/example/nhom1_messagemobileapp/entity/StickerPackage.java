package com.example.nhom1_messagemobileapp.entity;


import java.io.Serializable;

public class StickerPackage implements Serializable {
    private String id;
    private String name;
    private String sprite;
    private String url;
    private int itemCount;

    public StickerPackage(String id) {
        this.id = id;
    }

    public StickerPackage(String id, String name, String url, String strike, int itemCount) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.sprite = strike;
        this.itemCount = itemCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }


    @Override
    public String toString() {
        return "StickerPackage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sprite='" + sprite + '\'' +
                ", url='" + url + '\'' +
                ", itemCount=" + itemCount +
                '}';
    }
}
