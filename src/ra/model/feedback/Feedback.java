package ra.model.feedback;

import java.io.Serializable;

public class Feedback implements Serializable {
    private int feedbackId;
    private String feedbackContent;
    private int userId;
    private int orderId;
    public Feedback() {
    }

    public Feedback(int feedbackId, String feedbackContent, int userId, int orderId) {
        this.feedbackId = feedbackId;
        this.feedbackContent = feedbackContent;
        this.userId = userId;
        this.orderId = orderId;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return
                "Feedback ID: " + feedbackId + "\n"+
                "Feedback content: " + feedbackContent ;

    }
}
