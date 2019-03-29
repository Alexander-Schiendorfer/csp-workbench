/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking.order;

import hgb.csp.domain.Variable;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author alexander
 */
public class RandomVariableComparator implements Comparator<Variable> {
    Random r;

    public RandomVariableComparator() {
        r = new Random();
    }

    public int compare(Variable o1, Variable o2) {
        return -1 + r.nextInt(3);
    }

}
