/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.log;

import hgb.csp.mvc.Event;

/**
 *
 * @author alexander
 */
public class LogEvent extends Event{
    String message;
    public static final String LOG = "log";

    public LogEvent(String message) {
        super(LOG);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
