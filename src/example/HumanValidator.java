package example;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human)){
            throw new IllegalArgumentException("Input should be type of 'Human' !") ;
        }

        Human human = (Human) entity ;
        if (human.name == null || human.name.trim().isEmpty()) {
            throw new InvalidEntityException("Human name cannot be 'Empty' or 'Null' !") ;
        }

        if (human.age < 0) {
            throw new InvalidEntityException("Age must be 'Positive' integer !") ;
        }
    }
}