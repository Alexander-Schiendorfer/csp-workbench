package hgb.csp.search.backtracking;

import hgb.csp.search.backtracking.cp.ArcConsistencyCommand;
import hgb.csp.search.backtracking.cp.ConstraintPropagationCommand;
import org.jgrapht.ListenableGraph;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.search.AbstractSolver;
import hgb.csp.search.SelectNextValueStrategy;
import hgb.csp.search.SelectUnassignedVariableStrategy;
import hgb.csp.search.backtracking.cp.ForwardCheckingCommand;
import hgb.csp.search.mvc.SearchEvent;
import java.util.ArrayList;
import java.util.Collection;

public class BacktrackingSolver extends AbstractSolver {

    private Class<? extends SelectNextValueStrategy> valueSelecterClass;
    private Collection<ConstraintPropagationCommand> constraintPropagationCommands;
    private SelectUnassignedVariableStrategy variableSelecter; // contains strategy for selecting variables, one per solver

    public BacktrackingSolver() {
        super();
        setName("Backtracking Solver");
        constraintPropagationCommands = new ArrayList<ConstraintPropagationCommand>();
    }

    @Override
    protected void solveInternal(ListenableGraph<Variable, Constraint> csp) {
        variableSelecter.initialize(csp);
        if (solveRec(csp, 0)) {
            subject.raiseEvent(new SearchEvent(SearchEvent.SOLUTION_FOUND));
        } else {
            if(isStopped()) {
                for(Variable v : csp.vertexSet()) {
                    v.setAssignedValue(null);
                    subject.raiseEvent(new SearchEvent(SearchEvent.ASSIGNMENT_OCCURED));
                }
            }
        }
    }

    @Override
    public void configureSolver(Object configurationData) {
        if(! (configurationData instanceof BacktrackingData))
            throw new UnsupportedOperationException("Incompatible type in configure of backtracking "+configurationData.getClass());
        BacktrackingData data = (BacktrackingData) configurationData;
        if( data.getConstraintPropagationLevel() == ContraintPropagationLevel.FORWARD_CHECKING) {
            ForwardCheckingCommand cmd = new ForwardCheckingCommand();
            cmd.setLogWriter(getLogWriter());
            constraintPropagationCommands.add(cmd);
        }
        else if(data.getConstraintPropagationLevel() == ContraintPropagationLevel.ARC_CONSISTENCY)
            constraintPropagationCommands.add(new ArcConsistencyCommand());
        
        variableSelecter = data.getVariableSelecter();
        valueSelecterClass = data.getSelectNextValueStrategy();
    }

    private boolean solveRec(ListenableGraph<Variable, Constraint> csp, int i) {
        SelectNextValueStrategy valueSelecter; // local variables as they are needed in each call

        checkWait();
        if(isStopped())
            return false;

        iterationOccured();
        if (solutionIsFound(csp)) { // search complete
            solutionFound(csp);
            return true;
        } else {
            Variable x = variableSelecter.selectUnassignedVariable(csp);

            if (x == null) // no available variable found
            {
                return false;
            }

            calculateConflicts(csp);
            valueSelecter = initializeValueSelecter(x, csp);

            while (valueSelecter.hasAvailableValues()) {
               
                Object value = valueSelecter.selectNextValue(x);
                if (assignmentIsConsistent(csp, x, value)) {
                    assign(x, value);
                    // apply constraint propagation commands such as forward checking, AC3
                    for(ConstraintPropagationCommand command : constraintPropagationCommands)
                        command.apply(csp, x);

                   if (solveRec(csp, i + 1)) {
                        return true;
                    } else {
                        if(isStopped())
                            return false;
                        undoAssign(x, value);
                        for(ConstraintPropagationCommand command : constraintPropagationCommands)
                            command.undo();
                    }
                }
            }
            variableSelecter.backtrackVariable();
            return false;
        }
    }

    private SelectNextValueStrategy initializeValueSelecter(Variable x, ListenableGraph<Variable,Constraint> csp) {
        try {
            SelectNextValueStrategy valueSelecter;
            valueSelecter = valueSelecterClass.newInstance();
            valueSelecter.initialize(x, csp);
            return valueSelecter;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setValueSelecterClass(Class<? extends SelectNextValueStrategy> valueSelecterClass) {
        this.valueSelecterClass = valueSelecterClass;
    }

    public Class<? extends SelectNextValueStrategy> getValueSelecterClass() {
        return valueSelecterClass;
    }

}
