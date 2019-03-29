/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.problems.nqueens.constraints;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;

/**
 * Concrete implementation of a constraint for the
 * n-queens problem. A queensConstraint is violated if
 * two queens are
 *  1. in the same row (not relevant as each Queen X_i represents
 *     its respective row
 *  2. in the same column (values equal each other)
 *  3. in the same primary or secondary diagonal
 *
 * If a variable is not yet assigned it will not lead to
 * a failure. Serves as an example for any custom constraint
 * implementation
 * @author Alexander Schiendorfer
 */
public class QueensConstraint extends Constraint {

    @Override
    public boolean isConsistent() {
        Variable firstVariable = (Variable)this.getSource();
        Variable secondVariable = (Variable)this.getTarget();

        // null assignment doesn't lead to a violation
        if(firstVariable.getAssignedValue() == null || secondVariable.getAssignedValue() == null)
            return true;

        // violated, if two variables are in the same column
        if(firstVariable.getAssignedValue().equals(secondVariable.getAssignedValue()))
            return false;

        // primary diagonal check
        if(firstVariable.getId() + (Integer) firstVariable.getAssignedValue() == secondVariable.getId() + (Integer) secondVariable.getAssignedValue())
            return false;

        // secondary diagonal check
        if(firstVariable.getId() - (Integer) firstVariable.getAssignedValue() == secondVariable.getId() - (Integer) secondVariable.getAssignedValue())
            return false;
        
        return true;
    }
    
}
