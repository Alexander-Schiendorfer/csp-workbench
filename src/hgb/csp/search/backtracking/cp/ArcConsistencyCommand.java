/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking.cp;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.log.LogWriter;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author alexander
 */
public class ArcConsistencyCommand implements ConstraintPropagationCommand {
    private LogWriter logWriter;

    public ArcConsistencyCommand() {
    }

    public void apply(ListenableGraph<Variable, Constraint> csp, Variable v) {
        // TODO nothing implemented yet
    }

    public void undo() {
        // TODO nothing implemented yet
    }

    public void setLogWriter(LogWriter writer) {
        this.logWriter = writer;
    }

    public LogWriter getLogWriter() {
        return logWriter;
    }

}
