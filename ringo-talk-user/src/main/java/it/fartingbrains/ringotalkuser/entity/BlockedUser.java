package it.fartingbrains.ringotalkuser.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class BlockedUser {
    @Id
    @GeneratedValue
    private long id;
    private long userId;
    private long blockedUserId;
    private LocalDateTime createDate;

    public BlockedUser() { }

    public BlockedUser(long id, long userId, long blockedUserId, LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.blockedUserId = blockedUserId;
        this.createDate = createDate;
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

    public long getBlockedUserId() {
        return blockedUserId;
    }

    public void setBlockedUserId(long blockedUserId) {
        this.blockedUserId = blockedUserId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "BlockedUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", blockedUserId=" + blockedUserId +
                ", createDate=" + createDate +
                '}';
    }
}
