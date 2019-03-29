/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backjumping;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.search.AbstractSolver;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author alexander
 */
public class BackjumpingSolver extends AbstractSolver {

    @Override
    public void configureSolver(Object configurationData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void solveInternal(ListenableGraph<Variable, Constraint> csp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
