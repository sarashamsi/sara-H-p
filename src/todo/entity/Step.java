package todo.entity;

import db.Entity;

public class Step extends Entity {

    private String title ;
    private Status status ;
    private int taskRef ;

    public enum Status {
        NotStarted , Completed
    }

    public Step (String title , int taskRef){
       this.title = title ;
       this.taskRef = taskRef ;
       this.status = Status.NotStarted ;
    }

    public String getTitle(){
        return title ;
    }

    public void setTitle(String title){
        this.title = title ;
    }

    public Status getStatus(){
        return status ;
    }

    public void setStatus(Status status){
        this.status = status ;
    }

    public int getTaskRef(){
        return taskRef ;
    }

    public void setTaskRef(int taskRef){
        this.taskRef = taskRef ;
    }

    // Entity methods
    @Override
    public Step copy() {
        Step copy = new Step(this.title , this.taskRef) ;
        copy.id = this.id ;
        copy.status = this.status ;
        return copy ;
    }

    @Override
    public int getEntityCode() {
        return 2;
    }

}
