package com.ilstugram.model;

import com.google.gson.annotations.Expose;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="images", schema="ilstugram")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @Expose
    private int enabled;

    @Expose
    private String username;

    @Column(name="upload_date")
    @Expose
    private Date uploadDate;

    @Column(name="file_name")
    @Expose
    private String fileName;

    @Column(name="file_type")
    @Expose
    private String fileType;

    @Column(name = "file_path")
    @Expose
    private String filePath;

    @Column(name = "download_path")
    @Expose
    private String downloadPath;

    @Expose
    @OneToMany(mappedBy = "image")
    List<Comment> comments;

    @Expose
    private String description;

    public Image(){}

    public Image(String username, String fileName, String fileType, String filePath){
        this.username = username;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.downloadPath = "/uploads/"+username+"/"+fileName;
        this.uploadDate = new Date();
        this.enabled = 1;
    }

    public Image(String username, String fileName, String fileType, String filePath, String description){
        this(username, fileName, fileType, filePath);
        this.description = description;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getUploadDate(){
        return this.uploadDate;
    }

    public void setUploadDate(){
        this.uploadDate = new Date();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Thumbnail{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", username='" + username + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", filePath='" + filePath + '\'' +
                ", downloadPath='" + downloadPath + '\'' +
                ", description='" + description + '\'' +
                ", uploadDate='" + uploadDate + "'}";
    }
}