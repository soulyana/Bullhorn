package me.soulyana.bullhorn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    private String message;

    private String postedAt;

    @Lob
    private ArrayList<AppUser> likedPost;

    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "post")
    public Set<Comment> comments;


    public Post() {

        this.comments = new HashSet<>();
        this.likedPost = new ArrayList<>();
    }

    public Post(java.lang.String message, java.lang.String postedAt) {
        this.message = message;
        this.postedAt = postedAt;
        this.likedPost = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    public ArrayList<AppUser> getLikedPost() {
        return likedPost;
    }

    public void setLikedPost(ArrayList<AppUser> likedPost) {
        this.likedPost = likedPost;
    }

    //Add user to post
    public void addAppUser(AppUser user) {
        likedPost.add(user);
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }
}
