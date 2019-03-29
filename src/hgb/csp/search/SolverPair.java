/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search;

import hgb.csp.view.SolverConfigurationPanel;

/**
 *
 * @author Alexander
 */
public class SolverPair {
    private SolverConfigurationPanel panel;
    private AbstractSolver solver;

    public SolverPair() {

    }

    public SolverPair(SolverConfigurationPanel panel, AbstractSolver solver) {
        this.panel = panel;
        this.solver = solver;
    }

    public SolverConfigurationPanel getPanel() {
        return panel;
    }

    public void setPanel(SolverConfigurationPanel panel) {
        this.panel = panel;
    }

    public AbstractSolver getSolver() {
        return solver;
    }

    public void setSolver(AbstractSolver solver) {
        this.solver = solver;
    }

    @Override
    public String toString() {
        return solver.getName();
    }
}
