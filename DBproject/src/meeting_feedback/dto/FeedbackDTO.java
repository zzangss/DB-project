package meeting_feedback.dto;

public class FeedbackDTO {
    private int senderId;
    private int receiverId;
    private String content;

    public FeedbackDTO(int senderId, int receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public int getSenderId() { return senderId; }
    public int getReceiverId() { return receiverId; }
    public String getContent() { return content; }
}