package todo.service;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;


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
    }
    public static void deleteStep(int stepID){
        Database.delete(stepID);
    }

    public static Step getStep(int stepId) throws EntityNotFoundException {
        return (Step) Database.get(stepId);
    }

}







