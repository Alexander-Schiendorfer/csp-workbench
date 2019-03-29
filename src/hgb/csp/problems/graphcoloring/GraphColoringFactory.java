package hgb.csp.problems.graphcoloring;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.problems.Problem;
import hgb.csp.problems.ProblemFactory;
import hgb.csp.utils.ImplementationUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;

/**
 *
 * @author Alexander
 */
public class GraphColoringFactory extends ProblemFactory{

   public GraphColoringFactory() {
      super("Graph coloring example");
   }
   
   @Override
   public Problem getProblem(Map<String, Object> parameters) {
       Problem testCsp = new Problem("Example-CSP");
        testCsp.setName("A small test CSP");
        testCsp.setCsp(getTestGraph());
        testCsp.setProposedView(new GraphColoringViewPanel());
        return testCsp;
   }

     public static ListenableGraph<Variable, Constraint> getTestGraph() {
        ListenableGraph<Variable, Constraint> graph = new ListenableUndirectedGraph<Variable, Constraint>(Constraint.class);
        List<Variable> variables;
        final Variable first = new Variable("X1");
        final Variable sec = new Variable("X2");
        final Variable third = new Variable("X3");
        final Variable fourth = new Variable("X4");
        final Variable fifth = new Variable("X5");
        final Variable sixth = new Variable("X6");
        final Variable seventh = new Variable("X7");
        final Variable eighth = new Variable("X8");
        final Variable nineth = new Variable("X9");
        final Variable tenth = new Variable("X10");

        variables = new ArrayList<Variable>(5);
        variables.add(first);
        variables.add(sec);
        variables.add(third);
        variables.add(fourth);
        variables.add(fifth);
        variables.add(sixth);
        variables.add(seventh);
        variables.add(eighth);
        variables.add(nineth);
        variables.add(tenth);

        for (Variable v : variables) {
            v.setDomain(ImplementationUtils.getIntegers(3));
            graph.addVertex(v);
        }

        graph.addEdge(first, sec);
        graph.addEdge(first, third);
        graph.addEdge(sec, third);
        graph.addEdge(sec, fourth);
        /*	graph.addEdge(sec, fifth);
        graph.addEdge(sec, sixth);
        graph.addEdge(sixth, seventh)
         */
        graph.addEdge(nineth, seventh);
        graph.addEdge(first, fourth);
        graph.addEdge(fourth, nineth);
        graph.addEdge(nineth, tenth);
        graph.addEdge(first, eighth);

        // 10 -3 -6
        graph.addEdge(tenth, sixth);
        graph.addEdge(tenth, third);

        return graph;
    }
}
