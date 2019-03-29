package hgb.csp.search.backtracking;

import hgb.csp.domain.Constraint;
import hgb.csp.log.LogWriter;
import java.util.ArrayList;
import java.util.List;

import hgb.csp.domain.DomainItem;
import hgb.csp.domain.Variable;
import hgb.csp.log.Loggable;
import hgb.csp.search.SelectNextValueStrategy;
import org.jgrapht.ListenableGraph;

/**
 * Default implementation of value selection strategy.
 * returns all values regardless of their influence only
 * based on sequential position in the domain list
 *
 * @author Alexander Schiendorfer
 */
public class BasicValueSelecter implements SelectNextValueStrategy, Loggable {

  protected Variable variable;
  protected List values;
  protected int currentIndex;
  protected LogWriter logger;

  @Override
  public boolean hasAvailableValues() {
    if (values == null) // not initialized yet
    {
      return false;
    }
    return currentIndex < values.size();
  }

  @Override
  public void initialize(Variable variable, ListenableGraph<Variable, Constraint> csp) {
    this.variable = variable;
    values = new ArrayList(variable.getDomain().size());
    currentIndex = 0;
    for (DomainItem item : variable.getDomain()) {
      if (item.isAvailable()) {
        values.add(item.getValue());
      }
    }

  }

  /**
   * Responsible for choosing next guess.
   * --> least constraining value will be a useful application
   * @param x the current variable
   * @return preferable value of x
   */
  @Override
  public Object selectNextValue(Variable x) {
    if (!hasAvailableValues()) {
      return null;
    }
    return values.get(currentIndex++);
  }

    public void setLogWriter(LogWriter writer) {
        logger = writer;
    }

    public LogWriter getLogWriter() {
        return logger;
    }
}
