package hgb.csp.problems;

import java.awt.Component;
import java.util.Map;

/**
 * Abstract base class for problem factories
 * primary task is to generate problems according
 * to a given parameter map
 * @author Alexander Schiendorfer
 */
public abstract class ProblemFactory {
   private String name;
   private Component problemParameterView;

   public ProblemFactory() {

   }

   public ProblemFactory(String name) {
      this.name = name;
   }

   /**
    * Factory method to create problems.
    * @param parameters key-value pairs that are understood by subclasses
    * @return new problem instance
    */
   public abstract Problem getProblem(Map<String, Object> parameters);

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return name;
   }

   public Component getProblemParameterView() {
      return problemParameterView;
   }

   public void setProblemParameterView(Component problemParameterView) {
      this.problemParameterView = problemParameterView;
   }
}
