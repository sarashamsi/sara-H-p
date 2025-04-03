package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>() ;
    private static int nextID = 1 ;

    // برای جلوگیری از ایجاد نمونه کلاس Database
    private Database() {}

    public static void add(Entity e) {
        e.id = nextID ++ ;
        entities.add(e) ;
    }

    public static Entity get( int id ) throws EntityNotFoundException {
        for (Entity e : entities) {
            if (e.id == id){
                return e ;
            }
        }
        throw new EntityNotFoundException(id) ;
    }

    public static void delete (int id) throws EntityNotFoundException{
        Entity e = get(id) ;
        entities.remove(e) ;
    }

    public static void update (Entity e) throws EntityNotFoundException {
        Entity existEntity = get(e.id) ;
        entities.set(entities.indexOf(existEntity) , e) ;
    }
}
