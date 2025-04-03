package example;

import db.Entity;

public class Human extends Entity {
    public String name ;

    public Human (String name) {
        this.name = name ;
    }


    @Override
    public Human copy () {
        Human copy = new Human(new String(this.name)) ;
        copy.id = this.id ;
        return copy ;
    }

}