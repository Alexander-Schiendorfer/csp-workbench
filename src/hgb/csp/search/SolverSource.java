/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search;

import hgb.csp.search.backtracking.BacktrackingSolver;
import hgb.csp.search.backtracking.BasicValueSelecter;
import hgb.csp.search.backtracking.UnorderedVariableSelector;
import hgb.csp.search.backtracking.view.BacktrackingPanel;
import hgb.csp.search.localsearch.LocalSearchSolver;
import hgb.csp.search.localsearch.view.LocalSearchPanel;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Alexander
 */
public class SolverSource {
 
    public static List<SolverPair> getAllSolvers() {
        List<SolverPair> solvers = new ArrayList<SolverPair>();
        solvers.add(new SolverPair(new BacktrackingPanel(), new BacktrackingSolver()));
        solvers.add(new SolverPair(new LocalSearchPanel(), new LocalSearchSolver()));
        return solvers;
    }
}
