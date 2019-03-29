/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hgb.csp.search.localsearch;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.DomainItem;
import hgb.csp.domain.Variable;
import hgb.csp.search.AbstractSolver;
import hgb.csp.search.mvc.SearchEvent;
import hgb.csp.utils.ImplementationUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.jgrapht.ListenableGraph;

/**
 * Implements an iterative hill-climber using
 * the min-conflicts heuristic
 * @author Alexander Schiendorfer
 */
public class LocalSearchSolver extends AbstractSolver {

   private int maxSteps = 100;
   private int localOptimaTolerance = 3;
   private ChoiceVariable choiceVariable;
   private NeighborFunction neighborFunction;

   public LocalSearchSolver() {
      super();
      setName("Local Search Solver");
   }

   @Override
   public void configureSolver(Object configurationData) {
      if (!(configurationData instanceof LocalSearchData)) {
         throw new UnsupportedOperationException("Incompatible type in configure of hillclimber " + configurationData.getClass());
      }
      LocalSearchData data = (LocalSearchData) configurationData;

      maxSteps = data.getMaxSteps();
      choiceVariable = data.getChoiceVariable();
      neighborFunction = data.getNeighborFunction();
   }

   protected void initialAssignment(ListenableGraph<Variable, Constraint> csp) {
      if(neighborFunction == NeighborFunction.CHANGE_VALUES) {
         System.out.println("Change values");
         for (Variable v : csp.vertexSet()) {
            silentAssign(v, getRandomElement(v.getDomain()));
         }
      } else if (neighborFunction == NeighborFunction.SWAP_VALUE) { // use different domain items
         Set<Object> takenObjects = new HashSet<Object>();
         System.out.println("Swapping values");
         for(Variable v : csp.vertexSet()) {
            silentAssign(v, getRandomElement(v.getDomain(), takenObjects));
         }
      }
      subject.raiseEvent(new SearchEvent(SearchEvent.ASSIGNMENT_OCCURED));
   }

   @Override
   protected void solveInternal(final ListenableGraph<Variable, Constraint> csp) {
      // first just generate an inital assignment
      initialAssignment(csp);

      int stepCounter = 0;
      int localOptima = 0;
      String message = null;
      int maxConflicts = 0;
      int curConflicts = 0;
      Variable mostConflicted = null;
      Variable changingVariable = null;
      int changingVariableConflicts = 0;
      List<Variable> allConflicteds;
      final Random coinToss = new Random(1337);
      List<Variable> allVariables = new ArrayList<Variable>(csp.vertexSet());

      do {
         // find the most conflicted variable
         checkWait();
         if (isStopped()) {
            return;
         }

         // iterating through all variables, storing conflicteds and max conflicted
         maxConflicts = 0;
         curConflicts = 0;
         mostConflicted = null;
         changingVariable = null;
         changingVariableConflicts = 0;

         allConflicteds = new ArrayList<Variable>();
         iterationOccured();

         for (Variable v : csp.vertexSet()) {
            curConflicts = getConflicts(csp, v);
            if (curConflicts > 0) {
               allConflicteds.add(v);
            }

            if (curConflicts > maxConflicts) {
               mostConflicted = v;
               maxConflicts = curConflicts;
            }
         }

         if (maxConflicts == 0) {
            solutionFound(csp);
            return;
         } else {

            // one way to break local optima is not always to use the same conflicted variable
            if (choiceVariable == ChoiceVariable.RANDOM_CONFLICTED || localOptima > 0) {
               message = "Using one of the conflicted variables -> ";
               changingVariable = allConflicteds.get(coinToss.nextInt(allConflicteds.size()));
               changingVariableConflicts = getConflicts(csp, mostConflicted);

               if (choiceVariable != ChoiceVariable.RANDOM_CONFLICTED) {
                  message += "(because of a local optimum): ";
               }
            } else if (choiceVariable == ChoiceVariable.MAX_CONFLICTED) {
               message = "Using the maximally conflicted variable -> ";
               changingVariable = mostConflicted;
               changingVariableConflicts = maxConflicts;
            } else {
               changingVariable = allVariables.get(coinToss.nextInt(allVariables.size()));
               message = "Using any variable in CSP -> ";
            }

            message = String.format("%s%s (%d conflicts)", message, changingVariable, changingVariableConflicts);
            emitStatusMessage(message);
            final Object originalValue = changingVariable.getAssignedValue();
            Object newValue = null;
            int minConflicts = changingVariableConflicts;

            for (DomainItem di : mostConflicted.getDomain()) {
               if (!di.isAvailable()) {
                  continue;
               }

               if (!di.getValue().equals(changingVariable.getAssignedValue())) {
                  changingVariable.setAssignedValue(di.getValue());
                  if ((curConflicts = getConflicts(csp, changingVariable)) < minConflicts) {
                     minConflicts = curConflicts;
                     newValue = di.getValue();
                  }
               }
            }

            if (newValue == null) { // nothing better could be found -> local optimum
               StringBuilder sb = new StringBuilder("Stuck in a local optimum as no better value could be found for " + changingVariable);
               changingVariable.setAssignedValue(originalValue);
               localOptima++;

               sb.append("(" + localOptima + " times)");
               emitStatusMessage(sb.toString());

               sb.delete(0, sb.capacity());
               sb.append("Escape strategy: ");

               if (neighborFunction == NeighborFunction.CHANGE_VALUES && coinToss.nextBoolean()) {
                  Variable v = allVariables.get(coinToss.nextInt(allVariables.size()));

                  if (v.hasAvailableValues()) {
                     DomainItem di = v.getDomain().get(coinToss.nextInt(v.getDomain().size()));
                     v.setAssignedValue(di.getValue());

                     sb.append("Random assignment of " + di.getValue() + " to " + v);
                  }
               } else {
                  sb.append("Hoping for a better choice of a conflicted variable the next time.");
               }
               emitStatusMessage(sb.toString());

               if (localOptima >= localOptimaTolerance) {
                  emitStatusMessage("Tolerance of " + localOptima + " local optima without progress reached. Trying new initial assignment.");
                  initialAssignment(csp);
               }
            } else { // found some improvement to apply
               if (neighborFunction == NeighborFunction.CHANGE_VALUES) {
                  assign(changingVariable, newValue);
               } else if (neighborFunction == NeighborFunction.SWAP_VALUE) {
                  // find value containing new value
                  for(Constraint c : csp.edgesOf(changingVariable)) {
                     Variable other = csp.getEdgeSource(c);
                     if(other == changingVariable)
                        other = csp.getEdgeTarget(c);

                     if(newValue.equals(other.getAssignedValue()) && ! (newValue.equals(originalValue))) {
                        // found swap partner
                        silentAssign(other, originalValue);
                        assign(changingVariable, newValue);
                        emitStatusMessage("Swapping values of variable "+other + "("+other.getAssignedValue()+") and " + changingVariable + "("+changingVariable.getAssignedValue()+")");
                     }
                  }
               }
               localOptima = 0;

            }
         }
         stepCounter++;
      } while (stepCounter < maxSteps);

      if (isStopped()) {
         for (Variable v : csp.vertexSet()) {
            v.setAssignedValue(null);
            for (DomainItem di : v.getDomain()) {
               di.setAvailable(true);
            }
         }
         subject.raiseEvent(new SearchEvent(SearchEvent.ASSIGNMENT_OCCURED));
      }
   }

   private Object getRandomElement(List<DomainItem> domain) {
      Random r = new Random();
      return domain.get(r.nextInt(domain.size())).getValue();
   }

   private Object getRandomElement(List<DomainItem> domain, Set<Object> takenObjects) {
      Object[] domainItems = domain.toArray();
      ImplementationUtils.shuffle(domainItems);
      for(int i = 0; i < domainItems.length; i++)
      {
         DomainItem di = (DomainItem) domainItems[i];
         if(!ImplementationUtils.containsEqualObject(takenObjects, di.getValue())) {
            takenObjects.add(di.getValue());
            return di.getValue();
         }
      }
      return ((DomainItem) domainItems[0]).getValue();
   }

   private Variable findConflicteds(List<Variable> allConflicteds) {
      throw new UnsupportedOperationException("Not yet implemented");
   }
}
