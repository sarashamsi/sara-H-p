package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.TaskService;

import java.util.Date;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step)){
            throw new IllegalArgumentException("Input must be of type Step !") ;
        }
        Step step = (Step) entity ;

        if (step.getTitle() == null || step.getTitle().trim().isEmpty()){
            throw new InvalidEntityException("Title cannot be 'Null' or 'Empty' !") ;
        }

        /// /////////////////////////////////////
        try {
            Database.get(step.getTaskRef());
        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException("Cannot find task with ID= " + step.getTaskRef());
        }
    }

}
