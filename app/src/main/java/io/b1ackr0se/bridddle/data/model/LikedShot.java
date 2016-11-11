package io.b1ackr0se.bridddle.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LikedShot {
    @SerializedName("shot")
    @Expose
    private Shot shot;

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }
}
