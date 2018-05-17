package me.soulyana.bullhorn;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser implements Serializable{

    @Transient
    PasswordEncoder encoder;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @NotNull
    private String displayName;

    @Lob
    private String displayImg;

    @Lob
    public ArrayList<AppUser> followingUsers;

    @Lob
    public ArrayList<AppUser> followedByUsers;

    @OneToMany(mappedBy = "appUser")
    public Set<Post> posts;

    @OneToMany(mappedBy = "appUser")
    public Set<Comment> comments;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<AppRole> roles;

    public AppUser() {
        this.roles = new HashSet<>();
        this.followingUsers = new ArrayList<>();
        this.followedByUsers = new ArrayList<>();
        this.posts = new HashSet<>();
        this.comments = new HashSet<>();

        encoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //Encode the password to be saved
        this.password = encoder.encode(password);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayImg() {
        return displayImg;
    }

    public void setDisplayImg(String displayImg) {
        this.displayImg = displayImg;
    }


    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppRole> roles) {
        this.roles = roles;
    }

    public ArrayList<AppUser> getFollowingUsers() {
        return followingUsers;
    }

    public void setFollowingUsers(ArrayList<AppUser> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public ArrayList<AppUser> getFollowedByUsers() {
        return followedByUsers;
    }

    public void setFollowedByUsers(ArrayList<AppUser> followedByUsers) {
        this.followedByUsers = followedByUsers;
    }
}
