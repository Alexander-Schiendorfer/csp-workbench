/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.statistics;

import java.util.Map;
import java.util.TreeMap;

/**
 * Data Bean to hold information about one run i.e.
 * a tuple<p,s,d_i> where p is the respective problem configuration
 * and s the solver configuration to make multiple runs. D_i stands for
 * different attributes that are collected during a run, specified
 * by the keys stored in constants below
 * 
 * @author Alexander Schiendorfer
 */
public class RunData {
    public static final String CONSISTENCY_CHECKS = "ConsistencyChecks";
    public static final String ITERATIONS_COUNT = "iterationsCount";
    public static final String ASSIGNMENTS = "assignments";

    private Map<String, Integer> data;
    private String solverConfigurationKey; 
    private String problemConfigurationKey;
    private long elapsedTime;
    private long initDate;

    public RunData(String solverConfigurationKey, String problemConfigurationKey) {
        this.solverConfigurationKey = solverConfigurationKey;
        this.problemConfigurationKey = problemConfigurationKey;
        data = new TreeMap<String, Integer>();
    }

    public void setCounterFor(String key, Integer value) {
        data.put(key, value);
    }

    public Integer getCounterFor(String key) {
        if(data.containsKey(key))
            return data.get(key);
        else
            return 0;
    }

    public void incrementCounter(String key) {
        if(data.containsKey(key)) {
            data.put(key, data.get(key).intValue()+1);
        }
    }

    public String getProblemConfigurationKey() {
        return problemConfigurationKey;
    }

    public String getSolverConfigurationKey() {
        return solverConfigurationKey;
    }

    public long getElapsedTime() {
        return (elapsedTime = System.currentTimeMillis() - initDate);
    }

    void setInitDate(long currentTimeMillis) {
        initDate = currentTimeMillis;
    }

}
