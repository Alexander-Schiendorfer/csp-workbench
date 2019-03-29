/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.search.backtracking;

/**
 *
 * @author Alexander
 */
public class KeyStrategyPair<T> {
    private String key;
    private Class<? extends T> strategyClass;

    public KeyStrategyPair(String key, Class<? extends T> strategyClass) {
        this.key = key;
        this.strategyClass = strategyClass;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class<? extends T> getStrategyClass() {
        return strategyClass;
    }

    public void setStrategyClass(Class<? extends T> strategyClass) {
        this.strategyClass = strategyClass;
    }
}
