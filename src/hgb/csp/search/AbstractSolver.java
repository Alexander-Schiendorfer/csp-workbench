package hgb.csp.search;

import hgb.csp.problems.Problem;
import java.util.Set;

import org.jgrapht.ListenableGraph;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.DomainItem;
import hgb.csp.domain.Variable;
import hgb.csp.log.LogEvent;
import hgb.csp.log.LogWriter;
import hgb.csp.mvc.Handles;
import hgb.csp.search.mvc.SearchEvent;
import hgb.csp.mvc.Subject;
import hgb.csp.mvc.SubjectImpl;
import hgb.csp.utils.ImplementationUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class for search methods to support
 * both constructive backtracking and 
 * initial partial assignment and repair (= min-conflicts) approaches 
 * with the same subset of common operations
 * 
 * Thread management will be done in this base class to separate
 * calculating/search logic from GUI activity
 * 
 * @author Alexander Schiendorfer
 * @param T type of domains of variables
 */
public abstract class AbstractSolver implements Runnable, Subject {
    protected SubjectImpl subject;                     // delegatee implementation of Subject
    private long interval;                             // waiting interval between iterations
    private String name;                               // natural name of solver
    protected String key;                              // Key to identify solver configuration in stats
    private ListenableGraph<Variable, Constraint> csp; // JGraph constraint graph of CSP
    private Problem problem;                           // problem object holds metadata to CSP
    protected boolean wait;                            // flag to indicate if the thread is set to wait
    protected boolean stopped;                         // flag to indicate if solver is stopped
    protected LogWriter logWriter;                     // LogWriter configured for subclasses
    protected Thread worker;
    
    public AbstractSolver() {
        subject = new SubjectImpl();
        logWriter = new LogWriter();
    }

    public abstract void configureSolver(Object configurationData);

    /**
     * Determines if each variable in the constraint graph has an assigned value
     * and no constraint is violated
     * @param csp the constraint graph
     * @return assignment is correct
     */
    protected boolean solutionIsFound(ListenableGraph<Variable, Constraint> csp) {
        for(Variable v : csp.vertexSet()) {
            if(v.getAssignedValue() == null)
                return false;
        }
        for(Constraint c : csp.edgeSet())
            if(!c.isConsistent())
                return false;
        return true;
    }

    /**
     * Convenience methods for concrete solvers
     * raises a SearchEvent.ITERATION_OCCURED event
     * @return
     */
    protected void iterationOccured() {
        subject.raiseEvent(new SearchEvent(SearchEvent.ITERATION_OCCURED,this));
    }

    /**
     * Determines if the currently tried assignment is consistent
     * and can be used further
     * @param csp constraint graph
     * @param x variable to try assignment for
     * @param value inspected variable
     * @return true if value can be assigned to x
     */
    protected boolean assignmentIsConsistent(
            ListenableGraph<Variable, Constraint> csp, Variable x, Object value) {
        SearchEvent se = new SearchEvent(SearchEvent.CONSISTENCY_CHECK, this);
        subject.raiseEvent(se);

        // maybe a better solution might come up here
        if (value == null) 
            return false;

        x.setAssignedValue(value);
        Set<Constraint> constraints = csp.edgesOf(x);
        for (Constraint c : constraints) {
            if (!c.isConsistent()) {
                x.setAssignedValue(null);
                return false;
            }
        }
        x.setAssignedValue(null);
        return true;
    }

    /**
     * Assigns without raising an event or pausing the thread
     * will be needed for complete assignment algorithms
     * @param v
     * @param value
     */
    protected void silentAssign(Variable v, Object value) {
        v.setAssignedValue(value);        
    }

    /**
     * Assigns a value to a variable (without checking for consistency)
     * and dispatches the respective event that's needed by views
     * @param v
     * @param value
     */
    protected void assign(Variable v, Object value) {
        silentAssign(v, value);
        ImplementationUtils.sleep(interval);
        SearchEvent event = new SearchEvent(SearchEvent.ASSIGNMENT_OCCURED);
        event.setSolver(this);
        event.setAssociatedVariable(v);
        subject.raiseEvent(event);
    }

    /**
     * Removes assignment from variable
     * @param v
     * @param value
     */
    protected void undoAssign(Variable v, Object value) {
        v.setAssignedValue(null);
    }
    
    protected void calculateConflicts(ListenableGraph<Variable, Constraint> csp) {
        
        for(Variable v : csp.vertexSet()) {
            if(v.getAssignedValue() == null) {

                // find numbers of involved constraints
                v.setConstraints(csp.edgesOf(v).size());
                int availableDomainItems = 0;

                for(DomainItem di : v.getDomain()) {
                    if(di.isAvailable()) {
                        availableDomainItems++;
                    }
                }
                v.setAvailableDomainItems(availableDomainItems);
            }
        }
    }

    protected int getOverallConflicts(ListenableGraph<Variable, Constraint> csp) {
        int conflicts = 0;
        for(Constraint c : csp.edgeSet())
            if(!c.isConsistent())
                conflicts++;
        return conflicts;
    }

    protected int getConflicts(ListenableGraph<Variable, Constraint> csp, Variable v) {
        int conflicts = 0;
        subject.raiseEvent(new SearchEvent(SearchEvent.CONSISTENCY_CHECK, this));
        
        for(Constraint c : csp.edgesOf(v)) {
            
            if(!c.isConsistent())
                conflicts++;
        }
        return conflicts;
    }

    @Override
    public void run() {
        synchronized(this) {
            wait = false;
            stopped = false;
        }
        logWriter.removeAllObservers();
        logWriter.addObserver(this);
        solveInternal(csp);
        subject.raiseEvent(new SearchEvent(SearchEvent.SEARCH_ENDED));
    }

    protected abstract void solveInternal(ListenableGraph<Variable, Constraint> csp);

    protected void solutionFound(ListenableGraph<Variable, Constraint> csp) {
        subject.raiseEvent(new SearchEvent(SearchEvent.ASSIGNMENT_OCCURED));
        subject.raiseEvent(new SearchEvent(SearchEvent.SOLUTION_FOUND));
        emitStatusMessage("A solution to the csp has been found.");
    }

    public synchronized void setInterval(long interval) {
        this.interval = interval;
    }

    public synchronized long getInterval() {
        return interval;
    }

    public void solve(ListenableGraph<Variable, Constraint> csp) {
        this.csp = csp;
        worker = new Thread(this);
        SearchEvent event = new SearchEvent(SearchEvent.INIT_SEARCH);
        event.setSolver(this);
        event.setProblem(problem);
        subject.raiseEvent(event);
        worker.start();
    }

    public void addObserver(Object observer) {
        subject.addObserver(observer);
    }
    
    public void removeObserver(Object observer) {
        subject.removeObserver(observer);
    }

    public void pause() {
        if(worker!=null)
            synchronized(this){
                wait = true;
                // the correct wait-calls have to be done in
                // subclasses by using checkWait
            }
    }

    public synchronized  void stop() {
        stopped = true;
    }

    @Handles(type=LogEvent.LOG)
    public void logCatched(LogEvent event) {
        emitStatusMessage(event.getMessage());
    }

    protected LogWriter getLogWriter() {
        return logWriter;
    }

    public synchronized void emitStatusMessage(String message) {
      subject.raiseEvent(new SearchEvent(SearchEvent.MESSAGE, message));
    }
    
    /**
     * Convenience method to query the status of stopped
     * in a synchronized context
     * @return
     */
    protected synchronized boolean isStopped() {
        return stopped;
    }

    /**
     * Helping method for subclasses - used at several steps in an
     * algorithm to react on a demanded pause. Implemented by letting
     * the thread wait for a resume call which is done by
     */
    protected void checkWait() {
          synchronized(this) { // looking if we have to wait
            while(wait)
               try {
                   wait();
               } catch (InterruptedException ex) {
                  Logger.getLogger(AbstractSolver.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
    }

    /**
     * Wakes up worker thread after having paused.
     */
    public void resume() {
        if(worker!=null)
            synchronized(this){
                wait = false;
                this.notifyAll();
            }
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeAllObservers() {
        subject.removeAllObservers();
    }

    public void setProblem(Problem currentProblem) {
        problem = currentProblem;
    }

}
