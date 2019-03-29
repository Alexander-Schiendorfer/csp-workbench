/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking.order;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.log.LogWriter;
import hgb.csp.search.SelectUnassignedVariableStrategy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import org.jgrapht.ListenableGraph;

/**
 * Base class for implementing any static variable order
 * order is precalculated once. The respective comparator has
 * to be provided by the subclass
 * @author Alexander Schiendorfer
 */
public class StaticVariableOrder implements SelectUnassignedVariableStrategy {
    private Variable[] variables;
    private String name;
    private Comparator<Variable> comparator;
    private int currentIndex;
    private LogWriter logWriter;

    public StaticVariableOrder(Comparator<Variable> comparator) {
        this.comparator = comparator;
    }

    public StaticVariableOrder(String name, Comparator<Variable> comparator) {
        this(comparator);
        this.name = name;
    }

    public boolean isStaticOrder() {
        return true;
    }

    public Variable selectUnassignedVariable(ListenableGraph<Variable, Constraint> csp) {
        if(currentIndex >= variables.length || currentIndex < 0)
            return null;
        return variables[currentIndex++];
    }

    public void setVariables(Collection<Variable> variableCollection) {
        if(variableCollection == null)
            return;

        this.variables = new Variable[variableCollection.size()];
        int i = 0;
        for(Variable v:variableCollection)
            variables[i++] = v;

        // sorting according to chosen order
        Arrays.sort(variables, comparator);
        currentIndex = 0;
    }

    public void setComparator(Comparator<Variable> comparator) {
        this.comparator = comparator;
    }

    public void backtrackVariable() {
        currentIndex--;
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
       setVariables(csp.vertexSet());
    }
}
