package DB2025Team08.model;

import java.time.LocalDateTime;

/**
 * 피드백 정보를 나타내는 모델 클래스.
 */
public class Feedback {
    private int feedbackId;
    private int senderId;
    private int receiverId;
    private String content;
    private LocalDateTime createdAt;

    /**
     * 기본 생성자.
     */
    public Feedback() {}

    /**
     * 모든 필드를 초기화하는 생성자.
     *
     * @param feedbackId 고유 피드백 ID
     * @param senderId   피드백을 보낸 사용자 ID
     * @param receiverId 피드백을 받은 사용자 ID
     * @param content    피드백 내용
     * @param createdAt  피드백 작성 시각
     */
    public Feedback(int feedbackId, int senderId, int receiverId, String content, LocalDateTime createdAt) {
        this.feedbackId = feedbackId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createdAt = createdAt;
    }

    /**
     * 피드백 ID를 반환한다.
     *
     * @return 피드백 ID
     */
    public int getFeedbackId() {
        return feedbackId;
    }

    /**
     * 피드백 ID를 설정한다.
     *
     * @param feedbackId 피드백 ID
     */
    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    /**
     * 피드백을 보낸 사용자 ID를 반환한다.
     *
     * @return 발신자 ID
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * 피드백을 보낸 사용자 ID를 설정한다.
     *
     * @param senderId 발신자 ID
     */
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    /**
     * 피드백을 받은 사용자 ID를 반환한다.
     *
     * @return 수신자 ID
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * 피드백을 받은 사용자 ID를 설정한다.
     *
     * @param receiverId 수신자 ID
     */
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * 피드백 내용을 반환한다.
     *
     * @return 피드백 내용
     */
    public String getContent() {
        return content;
    }

    /**
     * 피드백 내용을 설정한다.
     *
     * @param content 피드백 내용
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 피드백 작성 시각을 반환한다.
     *
     * @return 생성 시각 (LocalDateTime)
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * 피드백 작성 시각을 설정한다.
     *
     * @param createdAt 생성 시각 (LocalDateTime)
     */
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
