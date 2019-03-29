/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.log;

/**
 *
 * @author alexander
 */
public interface Loggable {
    void setLogWriter(LogWriter writer);
    LogWriter getLogWriter();
}
