/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.localsearch;

/**
 *
 * @author Alexander
 */
public enum NeighborFunction {
   CHANGE_VALUES {
      @Override
      public String toString() {
         return "Change value of one variable";
      }
   }, SWAP_VALUE {
      @Override
      public String toString() {
         return "Swap values of variables (useful with AllDiff constraint)";
      }
   }
}
