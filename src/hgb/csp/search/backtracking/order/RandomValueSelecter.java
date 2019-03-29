package hgb.csp.search.backtracking.order;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.search.backtracking.BasicValueSelecter;
import java.util.List;
import java.util.Random;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author Alexander
 */
public class RandomValueSelecter extends BasicValueSelecter{

   @Override
   public void initialize(Variable variable, ListenableGraph<Variable, Constraint> csp) {
      super.initialize(variable, csp);
      shuffle(values);
   }

   private void shuffle(List values) {
      Random rand = new Random();
      Object helper = null;

      for (int i = values.size() - 1; i > 1; i--) {
         int swapIndex = rand.nextInt(i);
         helper = values.get(i);
         values.set(i, values.get(swapIndex));
         values.set(swapIndex, helper);
      }
   }

}
