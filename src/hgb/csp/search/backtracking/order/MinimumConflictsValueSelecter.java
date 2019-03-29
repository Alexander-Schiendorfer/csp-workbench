/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hgb.csp.search.backtracking.order;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.search.backtracking.BasicValueSelecter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.naming.ldap.HasControls;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author Alexander
 */
public class MinimumConflictsValueSelecter extends BasicValueSelecter {

    private class ValueComparator implements Comparator<Object>{

        public int compare(Object o1, Object o2) {
            return conflicts.get(o1).compareTo(conflicts.get(o2));
        }
    }

    Map<Object, Integer> conflicts;

    public MinimumConflictsValueSelecter() {
        super();

    }

    @Override
    public void initialize(Variable variable, ListenableGraph<Variable, Constraint> csp) {
        super.initialize(variable, csp);
        // works as a hook here -> order values according to their caused conflicts

        conflicts = new HashMap<Object, Integer>();
        for (Object value : values) {
            variable.setAssignedValue(value);
            int conf = 0;
            for (Constraint c : csp.edgesOf(variable)) {
                if(!c.isConsistent())
                    conf++;
            }
            conflicts.put(value, conf);
        }
        variable.setAssignedValue(null);

        Collections.sort(values, new ValueComparator());
    }
}
