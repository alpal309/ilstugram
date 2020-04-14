package com.ilstugram.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "image_comments", schema = "ilstugram")
public class Comment {

    private int enabled;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @Expose
    @Column(name = "image_id")
    private String imageId;

    @Expose
    private String username, description;

    @Expose
    private Date date;

    @ManyToOne
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    Image image;

    public Comment() {
    }

    public Comment(String imageId, String username, String description, Date date){
        this.imageId = imageId;
        this.username = username;
        this.description = description;
        this.date = date;
        this.enabled = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", imageId='" + imageId + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
