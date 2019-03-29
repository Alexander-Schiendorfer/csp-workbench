/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking.order;

import hgb.csp.domain.Variable;
import java.util.Comparator;

/**
 *
 * @author alexander
 */
class MinimumRemainingValuesComparator implements Comparator<Variable> {

    private boolean useMostConstrainedTieBreaker;

    public MinimumRemainingValuesComparator() {
    }

    public int compare(Variable v1, Variable v2) {
        int res = v1.getAvailableDomainItems() - v2.getAvailableDomainItems();
        if(res == 0 && isUseMostConstrainedTieBreaker())
            return new MostConstrainedComparator().compare(v1, v2);
        return res;
    }

    public void setUseMostConstrainedTieBreaker(boolean useMostConstrainedTieBreaker) {
        this.useMostConstrainedTieBreaker = useMostConstrainedTieBreaker;
    }

    public boolean isUseMostConstrainedTieBreaker() {
        return useMostConstrainedTieBreaker;
    }
}
