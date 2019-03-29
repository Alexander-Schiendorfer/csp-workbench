/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.log;

import hgb.csp.mvc.Subject;
import hgb.csp.mvc.SubjectImpl;

/**
 *
 * @author alexander
 */
public class LogWriter implements Subject {

    protected SubjectImpl impl;

    public LogWriter() {
        impl = new SubjectImpl();
    }

    public void emitMessage(String message) {
        impl.raiseEvent(new LogEvent(message));
    }

    public void addObserver(Object observer) {
        impl.addObserver(observer);
    }

    public void removeAllObservers() {
        impl.removeAllObservers();
    }

    public void removeObserver(Object observer) {
        impl.removeObserver(observer);
    }


}
