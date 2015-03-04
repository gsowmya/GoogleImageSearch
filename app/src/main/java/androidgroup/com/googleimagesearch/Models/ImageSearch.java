package androidgroup.com.googleimagesearch.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sowmya on 2/28/15.
 */
public class ImageSearch {

    public String tbUrl;
    public String url;
    public String title;


    ImageSearch(JSONObject jsonObject){
        try {
            this.title = jsonObject.getString("title");
            this.url = jsonObject.getString("url");
            this.tbUrl = jsonObject.getString("tbUrl");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<ImageSearch> getImages(JSONArray jsonArray){
        List<ImageSearch> images = new ArrayList<ImageSearch>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                images.add(new ImageSearch(jsonArray.getJSONObject(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return images;
    }



}
