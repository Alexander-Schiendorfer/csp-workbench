package hgb.csp.search.backtracking;

import hgb.csp.log.LogWriter;
import org.jgrapht.ListenableGraph;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.search.SelectUnassignedVariableStrategy;

public class UnorderedVariableSelector implements
		SelectUnassignedVariableStrategy {
        private LogWriter logWriter;

	@Override
	public Variable selectUnassignedVariable(
			ListenableGraph<Variable, Constraint> csp) {
		for (Variable var : csp.vertexSet()){
			if (var.getAssignedValue() == null && var.hasAvailableValues())
				return var;
		}
		return null;
	}

    public boolean isStaticOrder() {
        return true;
    }

    @Override
    public String toString() {
        return "Unordered variable selection";
    }

    public void initialize(ListenableGraph<Variable, Constraint> csp) {
        // nothing necessary here
    }

    public void backtrackVariable() {
        // nothing necessary
    }

    public void setLogWriter(LogWriter writer) {
        this.logWriter = writer;
    }

    public LogWriter getLogWriter() {
        return logWriter;
    }
}
