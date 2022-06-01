package tec.bd.todo;

import java.util.Date;

public class TodoRecord {

    private String id;

    private String title;

    private String description;

    private Status status;

    private Date startDate;

    private Date endDate;

    public TodoRecord(String title) {
        this(title, null, Status.NEW, new Date(System.currentTimeMillis()), null);
    }

    public TodoRecord(String title, String description) {
        this(title, description, Status.NEW, new Date(System.currentTimeMillis()), null);
    }

    public TodoRecord(String title, String description, Status status, Date startDate, Date endDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
