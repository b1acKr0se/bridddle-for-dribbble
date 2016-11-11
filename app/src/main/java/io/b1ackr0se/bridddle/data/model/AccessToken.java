package io.b1ackr0se.bridddle.data.model;


import com.google.gson.annotations.SerializedName;

public class AccessToken {
    public String scope;

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("token_type")
    public String tokenType;

}
