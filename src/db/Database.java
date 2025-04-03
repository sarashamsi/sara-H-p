package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>() ;
    private static int nextID = 1 ;
    private static HashMap<Integer, Validator> validators = new HashMap<>() ;

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Validator for entity code "+ entityCode +"already exists !") ;
        }

        validators.put(entityCode, validator);
    }

    // برای جلوگیری از ایجاد نمونه کلاس Database
    private Database() {}


    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null){
            validator.validate(e);
        }
        Entity copy = e.copy();
        copy.id = nextID ++ ;
        entities.add(copy) ;
        // update ID
        e.id = copy.id ;

    }

    public static Entity get( int id ) throws EntityNotFoundException {
        for (Entity e : entities) {
            if (e.id == id){

                return e.copy() ;

                return e ;

            }
        }
        throw new EntityNotFoundException(id) ;
    }

    public static void delete (int id) throws EntityNotFoundException{
        Entity e = get(id) ;
        entities.remove(e) ;
    }


    public static void update (Entity e) throws EntityNotFoundException , InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode()) ;
        if (validator != null) {
            validator.validate(e);
        }

        Entity existingEntity = get(e.id) ;
        Entity copy = e.copy() ;
        entities.set(entities.indexOf(existingEntity) , copy) ;

    }

}
