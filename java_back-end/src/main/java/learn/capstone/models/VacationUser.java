package learn.capstone.models;

public class VacationUser {

    private int vacation_id;

    private int user_id;

    private String identifier;

    private Vacation vacation;

    private User user;

    public int getVacation_id() {
        return vacation_id;
    }

    public void setVacation_id(int vacation_id) {
        this.vacation_id = vacation_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}