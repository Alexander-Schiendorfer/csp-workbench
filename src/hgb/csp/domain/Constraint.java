package hgb.csp.domain;

import org.jgrapht.graph.DefaultEdge;

public class Constraint extends DefaultEdge {

   /**
    *
    */
   private static final long serialVersionUID = 775013351893595014L;

   public boolean isConsistent() {
      // directly implementing not equals constraint
      return !getSource().equals(getTarget());
   }
}
