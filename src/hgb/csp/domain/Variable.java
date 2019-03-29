package hgb.csp.domain;

import java.util.List;

/**
 * Represents one variable instance in a CSP
 * variable is designed polymorphically
 * @author Alexander Schiendorfer
 *
 */
public class Variable {

    private List<DomainItem> domain;
    private Object assignedValue;
    private String name; // might be used for display
    private int id; // the index X_i -> useful for display as well
    private int numberOfConstraints;
    private int availableDomainItems;
    
    public Variable(String name) {
        this(name,-1);
    }

    public Variable(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // ============ business area
    public boolean hasAvailableValues() {
        for (DomainItem di : domain) {
            if (di.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    // ============ getter and setter
    public void setAssignedValue(Object assignedValue) {
        this.assignedValue = assignedValue;
    }

    public Object getAssignedValue() {
        return assignedValue;
    }

    public void setDomain(List<DomainItem> domain) {
        this.domain = domain;
    }

    public List<DomainItem> getDomain() {
        return domain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // used for algorithm tuning
    public int getConstraints() {
        return numberOfConstraints;
    }

    public void setConstraints(int numberOfConstraints) {
        this.numberOfConstraints = numberOfConstraints;
    }

    public int getAvailableDomainItems() {
        int i = 0;
        for(DomainItem d : getDomain())
            if(d.isAvailable())
                i++;
        return i;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Variable) {
            Variable other = (Variable) obj;
            if (this.assignedValue != null && other.assignedValue != null) {
                return this.assignedValue.equals(other.assignedValue);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * id;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void setAvailableDomainItems(int legalValues) {
        availableDomainItems = legalValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
