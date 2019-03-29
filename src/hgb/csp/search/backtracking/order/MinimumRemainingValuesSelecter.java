/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking.order;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.log.LogWriter;
import hgb.csp.search.SelectUnassignedVariableStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author Alexander Schiendorfer
 */
public class MinimumRemainingValuesSelecter implements SelectUnassignedVariableStrategy {
    private String name;
    private LogWriter logWriter;

    public MinimumRemainingValuesSelecter(String name) {
        this.name = name;
    }

    public Variable selectUnassignedVariable(ListenableGraph<Variable, Constraint> csp) {
        if(csp == null || csp.vertexSet().isEmpty())
            return null;

        List<Variable> unassignedVariables = new ArrayList<Variable>(csp.vertexSet().size());
        for(Variable v : csp.vertexSet())
            if(v.hasAvailableValues() && v.getAssignedValue() == null)
                unassignedVariables.add(v);

        if(unassignedVariables.isEmpty())
            return null;
        return Collections.min(unassignedVariables, new MinimumRemainingValuesComparator());
    }

    public boolean isStaticOrder() {
        return false;
    }

      public void setLogWriter(LogWriter writer) {
        this.logWriter = writer;
    }

    public LogWriter getLogWriter() {
        return logWriter;
    }
    
    @Override
    public String toString() {
        return name;
    }

    public void initialize(ListenableGraph<Variable, Constraint> csp) {
       // nothing to do yet
    }

    public void backtrackVariable() {
        // nothing to do yet
    }
}
