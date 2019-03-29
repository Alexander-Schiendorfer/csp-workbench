/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking.cp;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.log.Loggable;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author Alexander
 */
public interface ConstraintPropagationCommand extends Loggable {
    void apply(ListenableGraph<Variable, Constraint> csp, Variable v);
    void undo();
}
