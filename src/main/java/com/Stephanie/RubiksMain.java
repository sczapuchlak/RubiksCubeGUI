package com.Stephanie;


public class RubiksMain {
    String thingOrPerson;
    double timeItTook;

    RubiksMain(String person, double time) {
       thingOrPerson = person;
        timeItTook = time;
    }

    @Override
    public String toString() {return "Person or Thing That Beat The Rubik's Cube: " + thingOrPerson +
                "with a time of: " + timeItTook + " seconds";
    }

}


