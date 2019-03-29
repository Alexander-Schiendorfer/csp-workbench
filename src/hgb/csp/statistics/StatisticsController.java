/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.statistics;

import hgb.csp.mvc.Handles;
import hgb.csp.mvc.Subject;
import hgb.csp.mvc.SubjectImpl;
import hgb.csp.search.mvc.SearchEvent;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author alexander
 */
public class StatisticsController implements Subject {
    private SubjectImpl impl;            // delegation
    private RunData currentRun;
    private Collection<RunData> allRuns; // stores all runs in no particular order

    public StatisticsController() {
        impl = new SubjectImpl();
        allRuns = new ArrayList<RunData>(40);
    }

    private void dataChanged() {
        DataEvent de = new DataEvent(currentRun);
        impl.raiseEvent(de);
    }

    @Handles(type=SearchEvent.ITERATION_OCCURED)
    public void iterationOccured(SearchEvent event) {
        currentRun.incrementCounter(RunData.ITERATIONS_COUNT);
        dataChanged();
    }

    @Handles(type=SearchEvent.CONSISTENCY_CHECK)
    public void consistencyCheckHandler(SearchEvent event) {
        currentRun.incrementCounter(RunData.CONSISTENCY_CHECKS);
        dataChanged();
    }

    @Handles(type=SearchEvent.ASSIGNMENT_OCCURED)
    public void assignmentHandler(SearchEvent event) {
        currentRun.incrementCounter(RunData.ASSIGNMENTS);
        dataChanged();
    }

    @Handles(type=SearchEvent.INIT_SEARCH)
    public void initSearchHandler(SearchEvent event) {
        currentRun = new RunData(event.getSolver().getKey(), event.getProblem().getKey());
        allRuns.add(currentRun);
        currentRun.setCounterFor(RunData.ASSIGNMENTS, 0);
        currentRun.setCounterFor(RunData.CONSISTENCY_CHECKS, 0);
        currentRun.setCounterFor(RunData.ITERATIONS_COUNT, 0);
        currentRun.setInitDate(System.currentTimeMillis());
    }

    public void addObserver(Object observer) {
        impl.addObserver(observer);
    }

    public void removeObserver(Object observer) {
        impl.removeObserver(observer);
    }

    public void removeAllObservers() {
        impl.removeAllObservers();
    }
}
