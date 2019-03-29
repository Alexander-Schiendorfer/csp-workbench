/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hgb.csp.search.mvc;

import hgb.csp.domain.Variable;
import hgb.csp.problems.Problem;
import hgb.csp.search.AbstractSolver;
import hgb.csp.mvc.Event;

/**
 *
 * @author Alexander
 */
public class SearchEvent extends Event {
   // useful constants to register events

   public static final String ASSIGNMENT_OCCURED = "assignmentOccured";
   public static final String CONSISTENCY_CHECK = "consistencyCheck";
   public static final String INIT_SEARCH = "initSearch";
   public static final String ITERATION_OCCURED = "iterationOccured";
   public static final String MESSAGE = "message";
   public static final String SEARCH_ENDED = "searchEnded";
   public static final String SOLUTION_FOUND = "solutionFound";

   // associated objects that will be necessary for observers
   private Variable associatedVariable;
   private AbstractSolver solver;
   private Problem problem;
   private String message;

   public SearchEvent(String type) {
      super(type);
   }

   public SearchEvent(String type, AbstractSolver solver) {
      this(type);
      this.solver = solver;
   }

   public SearchEvent(String type, String message) {
      this(type);
      this.message = message;
   }

   /**
    * @return the solver
    */
   public AbstractSolver getSolver() {
      return solver;
   }

   /**
    * @param solver the solver to set
    */
   public void setSolver(AbstractSolver solver) {
      this.solver = solver;
   }
   /**
    * @return the message
    */
   public String getMessage() {
      return message;
   }

   /**
    * @return the associatedVariable
    */
   public Variable getAssociatedVariable() {
      return associatedVariable;
   }

   /**
    * @param associatedVariable the associatedVariable to set
    */
   public void setAssociatedVariable(Variable associatedVariable) {
      this.associatedVariable = associatedVariable;
   }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Problem getProblem() {
        return problem;
    }
}
