package todo.service;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.util.ArrayList;
import java.util.List;


public class StepService  {

    public static Step createStep(String title , int taskRef) throws EntityNotFoundException, InvalidEntityException {
        if (title == null || title.trim().isEmpty()){
            throw new InvalidEntityException("Title cannot be 'Null' or 'Empty' !");
        }

        Entity entity = Database.get(taskRef) ;
        if (entity == null || entity.getEntityCode() != 1){
            throw new EntityNotFoundException(taskRef);
        }

        Step step = new Step(title,taskRef) ;
        Database.add(step);


        Task parentTask = (Task) entity;
        if ( parentTask.getStatus() == Task.Status.NotStarted || parentTask.getStatus() == Task.Status.Completed) {
            parentTask.setStatus(Task.Status.InProgress);
            Database.update(parentTask);
        }

        return step ;
    }

    public static void updateStepField(int stepID , String fieldName , String newValue)throws EntityNotFoundException , InvalidEntityException{
        Step step = (Step) Database.get(stepID);

        switch (fieldName.toLowerCase()){
            case "title":
                step.setTitle((String) newValue);
                break;
            case "status":
                Step.Status newStatus = Step.Status.valueOf(newValue);
                step.setStatus(newStatus);
                break;

            default:throw new IllegalArgumentException("No field with name '"+ fieldName +"' found !") ;
        }

        Database.update(step);

        if (fieldName.equals("status")){
            changeTaskStatus(step.getTaskRef());
        }
    }




    private static void changeTaskStatus(int taskID) throws EntityNotFoundException, InvalidEntityException {

        Task task = (Task) Database.get(taskID);

        List<Step> steps = StepService.getStepsByTaskId(taskID);

        boolean allCompleted = true;
        for (Step step : steps) {
            if (step.getStatus() != Step.Status.Completed) {
                allCompleted = false;
                break;
            }
        }

        if (allCompleted) {
            task.setStatus(Task.Status.Completed);
            Database.update(task);
        } else if (task.getStatus() == Task.Status.NotStarted) {

            task.setStatus(Task.Status.InProgress);
            Database.update(task);
        }
    }

    public static List<Step> getStepsByTaskId(int taskId) {
        List<Step> steps = new ArrayList<>();

        for (Entity entity : Database.getAll(2)) {
            Step step = (Step) entity;
            if (step.getTaskRef() == taskId) {
                steps.add(step);
            }
        }
        return steps;
    }

    public static Step getStep(int stepId) throws EntityNotFoundException {
        return (Step) Database.get(stepId);
    }

}







