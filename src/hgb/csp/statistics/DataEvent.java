/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.statistics;

import hgb.csp.mvc.Event;

/**
 *
 * @author alexander
 */
public class DataEvent extends Event {
    public static final String DATA_CHANGED = "dataChangedEvent";

    public DataEvent(RunData currentRun) {
        super(DATA_CHANGED);
        this.currentRun = currentRun;
    }
    
    private RunData currentRun;

    public RunData getCurrentRun() {
        return currentRun;
    }

}
