/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.problems;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.view.ProblemVisualization;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author Alexander
 */
public class Problem {
    private Class viewClass;
    private String name;
    private ListenableGraph<Variable, Constraint> csp;
    private ProblemVisualization proposedView;
    protected String key; // has to be set by problem factories

    public Problem() {

    }

    public Problem(String key) {
        this.key = key;
    }
    
    public Class getViewClass() {
        return viewClass;
    }

    public void setViewClass(Class viewClass) {
        this.viewClass = viewClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListenableGraph<Variable, Constraint> getCsp() {
        return csp;
    }

    public void setCsp(ListenableGraph<Variable, Constraint> csp) {
        this.csp = csp;
    }

    @Override
    public String toString() {
        return name;
    }

    public ProblemVisualization getProposedView() {
        return proposedView;
    }

    public void setProposedView(ProblemVisualization proposedView) {
        this.proposedView = proposedView;
    }

    public String getKey() {
        return key;
    }


}
