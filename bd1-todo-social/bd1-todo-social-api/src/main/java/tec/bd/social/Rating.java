package tec.bd.social;

import java.util.Date;

public class Rating {

    private int ratingId;
    private int ratingValue;
    private String todoId;
    private String clientId;
    private Date createdAt;

    public Rating(int ratingId, int ratingValue, String todoId, String clientId, Date createdAt) {
        this.ratingId = ratingId;
        this.ratingValue = ratingValue;
        this.todoId = todoId;
        this.clientId = clientId;
        this.createdAt = createdAt;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId1) {
        this.clientId = clientId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
