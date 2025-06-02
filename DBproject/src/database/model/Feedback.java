package database.model;

import java.time.LocalDateTime;

public class Feedback {
    private int feedbackId;
    private int senderId;
    private int receiverId;
    private String content;
    private LocalDateTime createdAt;

    // 기본 생성자
    public Feedback() {}

    // 전체 필드 생성자
    public Feedback(int feedbackId, int senderId, int receiverId, String content, LocalDateTime createdAt) {
        this.feedbackId = feedbackId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // getters & setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Feedback{" +
               "feedbackId=" + feedbackId +
               ", senderId=" + senderId +
               ", receiverId=" + receiverId +
               ", content='" + content + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}