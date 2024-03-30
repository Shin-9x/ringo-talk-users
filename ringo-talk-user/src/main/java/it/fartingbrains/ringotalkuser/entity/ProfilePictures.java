package it.fartingbrains.ringotalkuser.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ProfilePictures {
    @Id
    @GeneratedValue
    private long id;
    private long userId;
    private String path;
    private String name;
    private String mimeType;
    private String size;
    private float width;
    private float height;
    private boolean active;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public ProfilePictures() { }

    public ProfilePictures(
            long id, long userId, String path, String name, String mimeType, String size, float width, float height,
            boolean active, LocalDateTime createDate, LocalDateTime modifiedDate
    ) {
        this.id = id;
        this.userId = userId;
        this.path = path;
        this.name = name;
        this.mimeType = mimeType;
        this.size = size;
        this.width = width;
        this.height = height;
        this.active = active;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "ProfilePictures{" +
                "id=" + id +
                ", userId=" + userId +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", size='" + size + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", active=" + active +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
