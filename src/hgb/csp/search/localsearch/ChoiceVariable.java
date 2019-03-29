package hgb.csp.search.localsearch;

/**
 * Defines the strategy for picking variables.
 * In a hill climbing oriented procedure, variables are changed
 * to gain a new solution from the current neighborhood
 * 
 * @author Alexander Schiendorfer
 */
public enum ChoiceVariable {
    /**
     * any variable from CSP will be chosen to find a new value
     * mainly for demonstration purposes, involves also 0-conflict variables
     */
    ANY {

    @Override
    public String toString() {
      return "Any variable in CSP";
    }
  },
    /**
     * Picks one of the conflicted variables randomly.
     * Usually considered to be most the effective in iterated hill climbing
     */
    RANDOM_CONFLICTED {

    @Override
    public String toString() {
      return "Random conflicted variable";

    }
  },

    /**
     * Picks the maximally conflicted variable and tries to find a value
     * that leads to a reduction of conflicts
     */
    MAX_CONFLICTED {

    @Override
    public String toString() {
      return "Maximally conflicted variable";
    }
  }
}
