/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hgb.csp.problems.graphcoloring;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import java.awt.geom.Rectangle2D;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;

/**
 *
 * @author Alexander
 */
public class GraphLayouter {

    private void positionVertexAt(Object vertex, double x, double y, JGraphModelAdapter<Variable, Constraint> graphModelAdapter) {
        DefaultGraphCell cell = graphModelAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
                new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);
        // TODO: Clean up generics once JGraph goes generic
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        graphModelAdapter.edit(cellAttr, null, null, null);
    }

    public void layout(ListenableGraph<Variable, Constraint> constraintGraph, JGraph jgraph, JGraphModelAdapter<Variable, Constraint> graphModelApapter) {
        int i = 0;
        System.out.println("I am in layout");
        int numberOfVertices = constraintGraph.vertexSet().size();
        double currentAngle = Math.PI/2; // starting at 90°
        double interval = 2*Math.PI/numberOfVertices;
        double radius = 150;
        double zeroX  = 200;
        double zeroY = 150;

        Math.sin(i);
        for (Variable v : constraintGraph.vertexSet()) {
            positionVertexAt(v, zeroX + radius * Math.cos(currentAngle), zeroY - radius * Math.sin(currentAngle), graphModelApapter);
            currentAngle += interval;
        }
    }
}
