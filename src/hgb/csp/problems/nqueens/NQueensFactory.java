package hgb.csp.problems.nqueens;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.problems.Problem;
import hgb.csp.problems.ProblemFactory;
import hgb.csp.problems.nqueens.constraints.QueensConstraint;
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
public class NQueensFactory extends ProblemFactory{

   public static final String QUEENS_COUNT = "QUEEN_COUNT";
   
   public NQueensFactory() {
      super("N queens");
      setProblemParameterView(new NQueensParameter());
   }
   
   @Override
   public Problem getProblem(Map<String, Object> parameters) {
        
        int n = 8; // default value
        if (parameters.containsKey(QUEENS_COUNT))
           n = (Integer) parameters.get(QUEENS_COUNT);

        Problem nqueens = new Problem(n+"-Queens");
        nqueens.setName(n+" queens");

        nqueens.setProposedView(new NQueensViewPanel());
        nqueens.setCsp(getNQueens(n));
        return nqueens;
   }

   public ListenableGraph<Variable, Constraint> getNQueens(int n) {
        ListenableGraph<Variable, Constraint> graph = new ListenableUndirectedGraph<Variable, Constraint>(Constraint.class);
        List<Variable> variables = new ArrayList<Variable>(n);
        for(int i = 0; i < n; i++) {
            Variable x = new Variable("X"+ (i+1), (i+1) );
            x.setDomain(ImplementationUtils.getIntegers(n));
            variables.add(x);
            graph.addVertex(x);
        }

        for(int i = 0; i < n; i++) {
            for(int j = i+1; j <n;j++){
                graph.addEdge(variables.get(i), variables.get(j), new QueensConstraint());
            }
        }
        return graph;
    }

}
