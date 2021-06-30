package learn.capstone.models;

public class Comment {
    private int commentId;
    private String text;
    private int userId;
    private int vacationId;

    public Comment() {
    }

    public Comment(int commentId, String text, int userId, int vacationId) {
        this.commentId = commentId;
        this.text = text;
        this.userId = userId;
        this.vacationId = vacationId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }
}