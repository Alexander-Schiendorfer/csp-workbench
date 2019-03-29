/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hgb.csp.search.localsearch;

/**
 *
 * @author Alexander
 */
public class LocalSearchData {

  private int maxSteps;
  private ChoiceVariable choiceVariable;
  private NeighborFunction neighborFunction;

  public int getMaxSteps() {
    return maxSteps;
  }

  public void setMaxSteps(int maxSteps) {
    this.maxSteps = maxSteps;
  }

  public ChoiceVariable getChoiceVariable() {
    return choiceVariable;
  }

  public void setChoiceVariable(ChoiceVariable choiceVariable) {
    this.choiceVariable = choiceVariable;
  }

   public NeighborFunction getNeighborFunction() {
      return neighborFunction;
   }
   
   public void setNeighborFunction(NeighborFunction neighborFunction) {
      this.neighborFunction = neighborFunction;
   }
}
