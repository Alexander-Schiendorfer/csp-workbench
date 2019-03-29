/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking.cp;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.DomainItem;
import hgb.csp.domain.Variable;
import hgb.csp.log.LogWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import org.jgrapht.ListenableGraph;

/**
 * Implements forward checking for use in backtracking algorithms
 * stores all changes done after one apply call
 * @author Alexander Schiendorfer
 */
public class ForwardCheckingCommand implements ConstraintPropagationCommand{
    private LogWriter logWriter;
    Stack<Set<DomainItem>> changeSetStack;

    public ForwardCheckingCommand() {
        changeSetStack = new Stack<Set<DomainItem>>();
    }
    /**
     * Iterates over all neighbors of chronically last assigned variable v
     * and eliminates all values that are not consistent with the current assignment
     * @param csp the constraint graph
     * @param v last assigned variable
     */
    public void apply(ListenableGraph<Variable, Constraint> csp, Variable v) {
        Set<DomainItem> changeSet = new  HashSet<DomainItem>();

        for(Constraint c : csp.edgesOf(v)) {
            // bidirectional detection due to representation of undirected graph
            Variable connectedVariable = csp.getEdgeSource(c);
            if(connectedVariable == v)
                connectedVariable = csp.getEdgeTarget(c);

            Object currentAssignment  = connectedVariable.getAssignedValue();
            for(DomainItem di : connectedVariable.getDomain()) {
                if(di.isAvailable() && !di.getValue().equals(currentAssignment)) {
                    connectedVariable.setAssignedValue(di.getValue());
                    if(!c.isConsistent()) { // remove di.getValue() from domain
                        logWriter.emitMessage("Removing value "+di.getValue() +
                                   " from domain of "+connectedVariable +
                                   " because "+currentAssignment +
                                   " is not consistent with it.");
                        di.setAvailable(false);
                        changeSet.add(di);
                    }
                }
            }
            connectedVariable.setAssignedValue(currentAssignment);
        }
        changeSetStack.push(changeSet);
    }

    public void undo() {
        Set<DomainItem> changeSet = changeSetStack.pop();
        if(changeSet != null)
            for (DomainItem di : changeSet)
                di.setAvailable(true);
    }

      public void setLogWriter(LogWriter writer) {
        this.logWriter = writer;
    }

    public LogWriter getLogWriter() {
        return logWriter;
    }

}
