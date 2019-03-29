/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.view;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author Alexander
 */
public interface ProblemVisualization  {

    public void bind(ListenableGraph<Variable, Constraint> csp);

}
