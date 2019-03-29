/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hgb.csp.problems;

import hgb.csp.problems.graphcoloring.GraphColoringFactory;
import hgb.csp.problems.mapcoloring.MapColoringFactory;
import hgb.csp.problems.nqueens.NQueensFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public final class ProblemsSource {

    private static List<Problem> problems;
    private static List<ProblemFactory> problemFactories;
    
    static {
        problemFactories = new ArrayList<ProblemFactory>(5);
        problemFactories.add(new GraphColoringFactory());
        problemFactories.add(new NQueensFactory());
        problemFactories.add(new MapColoringFactory());

    }

    private ProblemsSource() { };

    public static List<ProblemFactory> getProblemFactories() {
      return problemFactories;
    }



}
