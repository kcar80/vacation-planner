package learn.capstone.models;

public class Comment {
    private int comment_id;
    private String text;
    private int user_id;
    private int vacation_id;

    public Comment() {
    }

    public Comment(int comment_id, String text, int user_id, int vacation_id) {
        this.comment_id = comment_id;
        this.text = text;
        this.user_id = user_id;
        this.vacation_id = vacation_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVacation_id() {
        return vacation_id;
    }

    public void setVacation_id(int vacation_id) {
        this.vacation_id = vacation_id;
    }
}