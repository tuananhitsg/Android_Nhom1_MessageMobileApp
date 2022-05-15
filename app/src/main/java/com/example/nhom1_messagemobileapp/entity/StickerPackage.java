package com.example.nhom1_messagemobileapp.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StickerPackage implements Serializable {
    private String id;
    private String name;
    private String sprite;
    private String url;
    private int itemCount;
    private List<String> stickers = new ArrayList<>();

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

    public List<String> getStickers() {
        return stickers;
    }

    public void setStickers(List<String> stickers) {
        this.stickers = stickers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StickerPackage that = (StickerPackage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
