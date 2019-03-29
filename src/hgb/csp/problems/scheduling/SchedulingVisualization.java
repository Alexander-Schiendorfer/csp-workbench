/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.problems.scheduling;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.mvc.Handles;
import hgb.csp.search.mvc.SearchEvent;
import hgb.csp.view.ProblemVisualization;
import javax.swing.JPanel;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author alexander
 */
public class SchedulingVisualization extends JPanel implements ProblemVisualization{

    private ListenableGraph<Variable, Constraint> constraintGraph;

    public void bind(ListenableGraph<Variable, Constraint> csp) {
        this.constraintGraph = csp;
        // draw initial rectangles representing the lanes
        // store references to SVG objects in this class
    }

    @Handles(type=SearchEvent.ASSIGNMENT_OCCURED)
    public synchronized void assignmentHappened(SearchEvent searchEvent) {
        final Variable affectedVariable = searchEvent.getAssociatedVariable();
        if(affectedVariable == null) { // not a single variable has been changed
            // but more variables -> occuring during local search
            for (Variable v : constraintGraph.vertexSet()) {
               // update lane of variable
            }
        }
        else { // one variable has been changed
            // update lanes to match affectedVariable's time
        }
    }
}
