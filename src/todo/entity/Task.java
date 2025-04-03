package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {

    private String title ;
    private String description ;
    private Date dueDate ;
    private Status status ;
    private Date creationDate;
    private Date lastModificationDate;

    public enum Status {
        NotStarted , InProgress , Completed
    }


    public Task (String title , String description , Date dueDate){
        this.title = title ;
        this.description = description ;
        this.dueDate = dueDate ;
        this.status = Status.NotStarted ;
    }

    public String getTitle(){
        return title ;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Entity methods
    @Override
    public Task copy() {
        Task copy = new Task(this.title , this.description , this.dueDate) ;
        copy.id = this.id ;
        copy.status = this.status ;
        copy.creationDate = this.creationDate ;
        copy.lastModificationDate = this.lastModificationDate ;
        return copy;
    }

    @Override
    public int getEntityCode() {
        return 1 ;
    }

    // Trackable methods
    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date ;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date ;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

}
