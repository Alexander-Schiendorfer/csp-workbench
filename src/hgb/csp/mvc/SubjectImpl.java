package hgb.csp.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubjectImpl implements Subject {

    protected Collection<Object> observers;

    public SubjectImpl() {
        observers = new ArrayList<Object>();
    }

    @Override
    public void addObserver(Object observer) {
        observers.add(observer);
    }

    public void raiseEvent(Event event) {
        try {
            for (Object observer : observers) {
                Class<? extends Object> c = observer.getClass();
                Method[] allMethods = c.getMethods();
                for (int i = 0; i < allMethods.length; i++) {
                    if (allMethods[i].isAnnotationPresent(Handles.class)) {
                        Handles h = allMethods[i].getAnnotation(Handles.class);
                        if (h.type().equals(event.getType())) {
                            allMethods[i].invoke(observer, event);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void removeObserver(Object observer) {
        observers.remove(observer);
    }

    public void removeAllObservers() {
        observers.clear();
    }
}
