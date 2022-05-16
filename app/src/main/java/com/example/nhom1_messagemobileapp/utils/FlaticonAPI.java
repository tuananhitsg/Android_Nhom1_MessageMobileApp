package com.example.nhom1_messagemobileapp.utils;

import android.content.Context;

import com.example.nhom1_messagemobileapp.entity.StickerPackage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FlaticonAPI {
    public static List<StickerPackage> packages = new ArrayList<>();
    public static final String API_KEY = "2b1ff89850d75a7208a0803e049188c7cea5ec18";
    private final String URL_BASE = "https://api.flaticon.com/v3/";
    private Context context;

    public FlaticonAPI(){

    }

    public FlaticonAPI(Context context){
        this.context = context;
    }

    public static String mapToParameters(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString();
    }

    public Response request(String api, String method, String params){
        api = URL_BASE + api;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(api)
                .method(method, body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response request(String url){

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response getToken(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("apikey", FlaticonAPI.API_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Response res = this.request("app/authentication", "POST", jsonObj.toString());
        return res;
    }

    public Response getStickers(){
        Response res = this.request("https://www.flaticon.com/stickers");
        System.out.println(res);
        try {
            System.out.println(res.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<StickerPackage> getPackages(){
//        List<String> packages = Arrays.asList("love-364", "love-371", "wedding-424",
//                "cave-man", "monk", "influencer-66", "meditation-9", "lineman");
        List<String> packages = Arrays.asList("stay-at-home-93", "christmas-531", "animals-139", "food-283");
        List<StickerPackage> result = new ArrayList<>();

        for(int i=0; i<packages.size(); i++){
            String url = "https://www.flaticon.com/stickers-pack/"+packages.get(i);
            Response res = this.request(url);
            try {
//                System.out.println(res.body().string());
                Document doc = Jsoup.parse(res.body().string());
                Element li = doc.select("li.icon--item").first();
                String id = li.attr("data-pack_id");
                String name = li.attr("data-pack_name");
                int item_count = 0;
                try{
                    item_count = Integer.parseInt(li.attr("data-pack_items"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                String sprite = li.attr("data-pack_sprite");

                StickerPackage stickerPackage = new StickerPackage(id, name, url, sprite, item_count);
                result.add(stickerPackage);

                System.out.println(stickerPackage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FlaticonAPI.packages = result;
        return result;
    }

    public List<String> getStickers(StickerPackage stickerPackage){
        Response res = this.request(stickerPackage.getUrl());
        List<String> result = new ArrayList<>();

        try {
            Document doc = null;
            doc = Jsoup.parse(res.body().string());
            Elements stickers = doc.select("li.icon--item");
            for(Element sticker: stickers){
                String id = sticker.attr("data-id");
                String link = sticker.attr("data-png");
                if(id.equals("") || link.equals(""))
                    continue;

                result.add(link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public void downloadPackage(StickerPackage stickerPackage, List<ImageCard> stickerList){
//        String path = InternalStorage.IMAGE_STICKER_DIR+"/"+stickerPackage.getId();
//        saveImage(stickerPackage.getSprite(),
//                path+"/cover",
//                "cover.png");
//
//        for (ImageCard sticker: stickerList){
//            saveImage(sticker.getSrc(),
//                    path,
//                    sticker.getStickerID()+".png");
//        }
//    }
//
//    public void saveImage(String url, String dir, String filename){
//        InternalStorage internalStorage = InternalStorage.getInstance(context);
//        Target target = internalStorage.getTarget(dir, filename);
//        Picasso.get()
//                .load(url)
//                .into(target);
//    }
}
