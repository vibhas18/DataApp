package in.co.dineout.xoppin.dineoutcollection.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by prateek.aggarwal on 5/13/16.
 */
public class ImageStatusModel implements Serializable {

    private int id;
    private int type;
    private String restaurantId;
    private String imageURI;
    @SerializedName("image_name")
    @Expose private String image_name;
    @SerializedName("image_caption")
    @Expose private String image_caption;
    private String key;
    @SerializedName("image_state")
    @Expose private String image_state = "new";
    @SerializedName("gil_id")
    @Expose private int gil_id = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getRemotePath() {

        String path = "";
        if(!TextUtils.isEmpty(image_name)){
            path = image_name;

        }
        else if(!TextUtils.isEmpty(key)){

           path = "http://d3tfancs2fcmmi.cloudfront.net" + "/"+key;




        }

        if(!(path.contains(".jpg") || path.contains(".jpeg")))
            path += ".jpg";
        return path.contains(".jpg.jpeg")?path.replace(".jpg.jpeg",".jpg"):path;
    }

    public void setRemotePath(String remotePath) {
        this.image_name = remotePath;
    }

    public String getCaption() {
        return image_caption;
    }

    public void setCaption(String caption) {
        this.image_caption = caption;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {

        if(!TextUtils.isEmpty(key))
        return key;

        if(!TextUtils.isEmpty(getRemotePath())){

            String url = "http://d3tfancs2fcmmi.cloudfront.net/";
            String path = getRemotePath().replaceAll(url,"");
            this.key = path.replaceAll(".jpg", "");
            return key;
        }


        return "";
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage_state() {
        return this.image_state;
    }

    public void setImage_state(String image_state) {
        this.image_state = image_state;
    }

    public int getGil_id() {
        return this.gil_id;
    }

    public void setGil_id(int gil_id) {
        this.gil_id = gil_id;
    }
}

