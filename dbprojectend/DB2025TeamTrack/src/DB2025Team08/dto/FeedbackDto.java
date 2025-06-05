package DB2025Team08.dto;

/**
 * 피드백 정보를 전달하는 DTO 클래스.
 */
public class FeedbackDto {
    private int senderId;
    private int receiverId;
    private String content;

    /**
     * 피드백 객체를 생성한다.
     *
     * @param senderId   피드백을 보낸 사용자 ID
     * @param receiverId 피드백을 받을 사용자 ID
     * @param content    피드백 내용
     */
    public FeedbackDto(int senderId, int receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    /**
     * 피드백을 보낸 사용자 ID를 반환한다.
     *
     * @return 발신자 ID
     */
    public int getSenderId() { return senderId; }

    /**
     * 피드백을 받을 사용자 ID를 반환한다.
     *
     * @return 수신자 ID
     */
    public int getReceiverId() { return receiverId; }

    /**
     * 피드백 내용을 반환한다.
     *
     * @return 피드백 내용
     */
    public String getContent() { return content; }
}
