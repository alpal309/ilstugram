package com.ilstugram.model;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "thumbnails", schema = "ilstugram")
public class Thumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int enabled;

    private String username;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")

    private String fileType;

    @Lob
    private byte[] data;

    public Thumbnail(){}

    public Thumbnail(String username, String fileName, String fileType, byte[] data){
        this.username = username;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.enabled = 1;
    }

    @Override
    public String toString() {
        return "Thumbnail{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", username='" + username + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
