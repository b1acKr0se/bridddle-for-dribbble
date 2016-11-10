package io.b1ackr0se.bridddle.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class User implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("buckets_count")
    @Expose
    private Integer bucketsCount;
    @SerializedName("comments_received_count")
    @Expose
    private Integer commentsReceivedCount;
    @SerializedName("followers_count")
    @Expose
    private Integer followersCount;
    @SerializedName("followings_count")
    @Expose
    private Integer followingsCount;
    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;
    @SerializedName("likes_received_count")
    @Expose
    private Integer likesReceivedCount;
    @SerializedName("projects_count")
    @Expose
    private Integer projectsCount;
    @SerializedName("rebounds_received_count")
    @Expose
    private Integer reboundsReceivedCount;
    @SerializedName("shots_count")
    @Expose
    private Integer shotsCount;
    @SerializedName("teams_count")
    @Expose
    private Integer teamsCount;
    @SerializedName("can_upload_shot")
    @Expose
    private Boolean canUploadShot;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("pro")
    @Expose
    private Boolean pro;
    @SerializedName("buckets_url")
    @Expose
    private String bucketsUrl;
    @SerializedName("followers_url")
    @Expose
    private String followersUrl;
    @SerializedName("following_url")
    @Expose
    private String followingUrl;
    @SerializedName("likes_url")
    @Expose
    private String likesUrl;
    @SerializedName("shots_url")
    @Expose
    private String shotsUrl;
    @SerializedName("teams_url")
    @Expose
    private String teamsUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @param htmlUrl
     * The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     *
     * @return
     * The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     *
     * @param avatarUrl
     * The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     *
     * @return
     * The bio
     */
    public String getBio() {
        return bio;
    }

    /**
     *
     * @param bio
     * The bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The bucketsCount
     */
    public Integer getBucketsCount() {
        return bucketsCount;
    }

    /**
     *
     * @param bucketsCount
     * The buckets_count
     */
    public void setBucketsCount(Integer bucketsCount) {
        this.bucketsCount = bucketsCount;
    }

    /**
     *
     * @return
     * The commentsReceivedCount
     */
    public Integer getCommentsReceivedCount() {
        return commentsReceivedCount;
    }

    /**
     *
     * @param commentsReceivedCount
     * The comments_received_count
     */
    public void setCommentsReceivedCount(Integer commentsReceivedCount) {
        this.commentsReceivedCount = commentsReceivedCount;
    }

    /**
     *
     * @return
     * The followersCount
     */
    public Integer getFollowersCount() {
        return followersCount;
    }

    /**
     *
     * @param followersCount
     * The followers_count
     */
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    /**
     *
     * @return
     * The followingsCount
     */
    public Integer getFollowingsCount() {
        return followingsCount;
    }

    /**
     *
     * @param followingsCount
     * The followings_count
     */
    public void setFollowingsCount(Integer followingsCount) {
        this.followingsCount = followingsCount;
    }

    /**
     *
     * @return
     * The likesCount
     */
    public Integer getLikesCount() {
        return likesCount;
    }

    /**
     *
     * @param likesCount
     * The likes_count
     */
    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    /**
     *
     * @return
     * The likesReceivedCount
     */
    public Integer getLikesReceivedCount() {
        return likesReceivedCount;
    }

    /**
     *
     * @param likesReceivedCount
     * The likes_received_count
     */
    public void setLikesReceivedCount(Integer likesReceivedCount) {
        this.likesReceivedCount = likesReceivedCount;
    }

    /**
     *
     * @return
     * The projectsCount
     */
    public Integer getProjectsCount() {
        return projectsCount;
    }

    /**
     *
     * @param projectsCount
     * The projects_count
     */
    public void setProjectsCount(Integer projectsCount) {
        this.projectsCount = projectsCount;
    }

    /**
     *
     * @return
     * The reboundsReceivedCount
     */
    public Integer getReboundsReceivedCount() {
        return reboundsReceivedCount;
    }

    /**
     *
     * @param reboundsReceivedCount
     * The rebounds_received_count
     */
    public void setReboundsReceivedCount(Integer reboundsReceivedCount) {
        this.reboundsReceivedCount = reboundsReceivedCount;
    }

    /**
     *
     * @return
     * The shotsCount
     */
    public Integer getShotsCount() {
        return shotsCount;
    }

    /**
     *
     * @param shotsCount
     * The shots_count
     */
    public void setShotsCount(Integer shotsCount) {
        this.shotsCount = shotsCount;
    }

    /**
     *
     * @return
     * The teamsCount
     */
    public Integer getTeamsCount() {
        return teamsCount;
    }

    /**
     *
     * @param teamsCount
     * The teams_count
     */
    public void setTeamsCount(Integer teamsCount) {
        this.teamsCount = teamsCount;
    }

    /**
     *
     * @return
     * The canUploadShot
     */
    public Boolean getCanUploadShot() {
        return canUploadShot;
    }

    /**
     *
     * @param canUploadShot
     * The can_upload_shot
     */
    public void setCanUploadShot(Boolean canUploadShot) {
        this.canUploadShot = canUploadShot;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The pro
     */
    public Boolean getPro() {
        return pro;
    }

    /**
     *
     * @param pro
     * The pro
     */
    public void setPro(Boolean pro) {
        this.pro = pro;
    }

    /**
     *
     * @return
     * The bucketsUrl
     */
    public String getBucketsUrl() {
        return bucketsUrl;
    }

    /**
     *
     * @param bucketsUrl
     * The buckets_url
     */
    public void setBucketsUrl(String bucketsUrl) {
        this.bucketsUrl = bucketsUrl;
    }

    /**
     *
     * @return
     * The followersUrl
     */
    public String getFollowersUrl() {
        return followersUrl;
    }

    /**
     *
     * @param followersUrl
     * The followers_url
     */
    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    /**
     *
     * @return
     * The followingUrl
     */
    public String getFollowingUrl() {
        return followingUrl;
    }

    /**
     *
     * @param followingUrl
     * The following_url
     */
    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    /**
     *
     * @return
     * The likesUrl
     */
    public String getLikesUrl() {
        return likesUrl;
    }

    /**
     *
     * @param likesUrl
     * The likes_url
     */
    public void setLikesUrl(String likesUrl) {
        this.likesUrl = likesUrl;
    }

    /**
     *
     * @return
     * The shotsUrl
     */
    public String getShotsUrl() {
        return shotsUrl;
    }

    /**
     *
     * @param shotsUrl
     * The shots_url
     */
    public void setShotsUrl(String shotsUrl) {
        this.shotsUrl = shotsUrl;
    }

    /**
     *
     * @return
     * The teamsUrl
     */
    public String getTeamsUrl() {
        return teamsUrl;
    }

    /**
     *
     * @param teamsUrl
     * The teams_url
     */
    public void setTeamsUrl(String teamsUrl) {
        this.teamsUrl = teamsUrl;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.bio);
        dest.writeString(this.location);
        dest.writeValue(this.bucketsCount);
        dest.writeValue(this.commentsReceivedCount);
        dest.writeValue(this.followersCount);
        dest.writeValue(this.followingsCount);
        dest.writeValue(this.likesCount);
        dest.writeValue(this.likesReceivedCount);
        dest.writeValue(this.projectsCount);
        dest.writeValue(this.reboundsReceivedCount);
        dest.writeValue(this.shotsCount);
        dest.writeValue(this.teamsCount);
        dest.writeValue(this.canUploadShot);
        dest.writeString(this.type);
        dest.writeValue(this.pro);
        dest.writeString(this.bucketsUrl);
        dest.writeString(this.followersUrl);
        dest.writeString(this.followingUrl);
        dest.writeString(this.likesUrl);
        dest.writeString(this.shotsUrl);
        dest.writeString(this.teamsUrl);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.username = in.readString();
        this.htmlUrl = in.readString();
        this.avatarUrl = in.readString();
        this.bio = in.readString();
        this.location = in.readString();
        this.bucketsCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.commentsReceivedCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.followersCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.followingsCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.likesCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.likesReceivedCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.projectsCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reboundsReceivedCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shotsCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.teamsCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.canUploadShot = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.type = in.readString();
        this.pro = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.bucketsUrl = in.readString();
        this.followersUrl = in.readString();
        this.followingUrl = in.readString();
        this.likesUrl = in.readString();
        this.shotsUrl = in.readString();
        this.teamsUrl = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}