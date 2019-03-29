/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking.order;

import hgb.csp.domain.Variable;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Alexander
 */
public class MostConstrainedComparator implements Comparator<Variable>, Serializable {

    public MostConstrainedComparator() {
    }

    /**
     * Most constrained variable strategy
     * looks by using constraint number
     * @param o1
     * @param o2
     * @return
     */
    public int compare(Variable o1, Variable o2) {
      return  - o1.getConstraints() + o2.getConstraints();
    }

}
