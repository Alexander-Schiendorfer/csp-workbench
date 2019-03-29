package hgb.csp.search;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.log.Loggable;
import org.jgrapht.ListenableGraph;

public interface SelectNextValueStrategy extends Loggable {
	void initialize(Variable variable, ListenableGraph<Variable, Constraint> csp);
	boolean hasAvailableValues();
	Object selectNextValue(Variable x);

}
