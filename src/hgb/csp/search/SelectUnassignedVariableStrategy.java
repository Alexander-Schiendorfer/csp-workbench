package hgb.csp.search;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.log.Loggable;

import org.jgrapht.ListenableGraph;

public interface SelectUnassignedVariableStrategy extends Loggable {
	/**
	 * Responsible for selecting a unassigned in each backtracking step
	 * thereby defining the ordered constraint graph
	 * --> using Most Constrained First heuristic will take place here
	 * @param csp constraint graph
	 * @return the next variable to try
	 */
	Variable selectUnassignedVariable(ListenableGraph<Variable, Constraint> csp);

        /**
         * Determines if the currently used order is static i.e. constant during the
         * whole search process. If the variables are instantiated dynamically (like the minimum
         * remaining values heuristic does) comparison has to be done each time - otherwise the
         * order can be precalculated
         * @return
         */
        boolean isStaticOrder();

        /**
         * Interface method for initializing the selecter with the CSP graph
         * -> orders can be precalculated 
         * @param csp
         */
        void initialize(ListenableGraph<Variable, Constraint> csp);

        /**
         * Has to be called if all values for the most recently selected variable failed 
         */
        void backtrackVariable();
}
