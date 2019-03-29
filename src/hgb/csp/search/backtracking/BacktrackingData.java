/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking;

import hgb.csp.search.SelectNextValueStrategy;
import hgb.csp.search.SelectUnassignedVariableStrategy;

/**
 *
 * @author Alexander
 */
public class BacktrackingData {
    private Class<? extends SelectNextValueStrategy> selectNextValueStrategy;
    private SelectUnassignedVariableStrategy variableSelecter;
    private ContraintPropagationLevel constraintPropagationLevel;

    public Class<? extends SelectNextValueStrategy> getSelectNextValueStrategy() {
        return selectNextValueStrategy;
    }

    public void setSelectNextValueStrategy(Class<? extends SelectNextValueStrategy> selectNextValueStrategy) {
        this.selectNextValueStrategy = selectNextValueStrategy;
    }

    public ContraintPropagationLevel getConstraintPropagationLevel() {
        return constraintPropagationLevel;
    }

    public void setConstraintPropagationLevel(ContraintPropagationLevel constraintPropagationLevel) {
        this.constraintPropagationLevel = constraintPropagationLevel;
    }

    public SelectUnassignedVariableStrategy getVariableSelecter() {
        return variableSelecter;
    }

    public void setVariableSelecter(SelectUnassignedVariableStrategy variableSelecter) {
        this.variableSelecter = variableSelecter;
    }
}
