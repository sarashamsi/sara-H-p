package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskService {

    public static Task getTask(int taskID) throws EntityNotFoundException{
        return (Task) Database.get(taskID);
    }

    public static void setAsCompleted(int taskId) throws EntityNotFoundException , InvalidEntityException {

        Task task = (Task) Database.get(taskId) ;

        if (task.getEntityCode() != 1){
            throw new EntityNotFoundException(taskId) ;
        }

        task.setStatus(Task.Status.Completed);
        task.setLastModificationDate(new Date());
        Database.update(task);

        completeAllStepsForTask(taskId);
    }



    public static Task CreatTask(String title , String description , Date dueDate) throws InvalidEntityException {

        if (title == null || title.trim().isEmpty()){
            throw new  InvalidEntityException("Title cannot be 'Null' or 'Empty' ! ") ;
        }

        if (dueDate == null || title.trim().isEmpty()){
            throw new InvalidEntityException("Due date cannot be 'Null' or 'Empty'!....");
        }

        Task task = new Task(title , description , dueDate) ;
        Database.add(task) ;
        return task ;
    }

    public static void updateTaskField(int taskID , String fieldName , String newValue) throws EntityNotFoundException , InvalidEntityException {
       Task task = (Task) Database.get(taskID) ;

       switch (fieldName.toLowerCase()){
           case "title":
               task.setTitle(newValue);
               break;
           case "description":
               task.setDescription(newValue);
               break;
           case "status":
               Task.Status newStatus = Task.Status.valueOf(newValue) ;
               task.setStatus(newStatus);
               if (newStatus == Task.Status.Completed){
                   completeAllStepsForTask(taskID);
               }
               break;
           default: throw new IllegalArgumentException("No field with name '"+ fieldName +"' found !") ;
       }
       task.setLastModificationDate(new Date());
       Database.update(task);

    }

    public static void deleteTask(int taskID) throws EntityNotFoundException {
        // Delete all steps of task :
        ArrayList<Step> steps = getStepsOfTask(taskID) ;
        for (Step step : steps){
            try {
                Database.delete(step.id);
            } catch (EntityNotFoundException e){
                System.out.println("Warning: Step with ID = " + step.id + " not found !");
            }
        }

        // Delete task:
        Database.delete(taskID);
    }

    public static ArrayList<Step> getStepsOfTask(int taskID) {

        ArrayList<Step> steps = new ArrayList<>() ;
        for (Entity entity : Database.getAll(2)){
            Step step = (Step) entity ;
            if (step.getTaskRef() == taskID){
                steps.add(step) ;
            }
        }
        return steps ;
    }
    public static ArrayList<Task> getAllTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            for (Entity entity : Database.getAll(1)) {
                tasks.add((Task) entity);
            }
            tasks.sort((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()));
        } catch (Exception e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }


    private static void completeAllStepsForTask(int taskId) throws EntityNotFoundException, InvalidEntityException {
        for (Step step : getStepsForTask(taskId)) {
            if (step.getStatus() != Step.Status.Completed) {
                step.setStatus(Step.Status.Completed);
                Database.update(step);
            }
        }
    }

    public static ArrayList<Step> getStepsForTask(int taskId) {
        ArrayList<Step> steps = new ArrayList<>();
        for (Entity entity : Database.getAll(2)) {
            Step step = (Step) entity;
            if (step.getTaskRef() == taskId) {
                steps.add(step);
            }
        }
        return steps;
    }

    public static ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        for (Task task : getAllTasks()) {
            if (task.getStatus() != Task.Status.Completed) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }


}


