package sample;

import java.util.ArrayList;
import java.util.List;

// observer pattern
public abstract class Subject {

    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer) {     // UserWindow
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String tweet, Long time) {     // User
        for(Observer observer : observers) {
            observer.update(this, tweet, time);
        }
    }

}
