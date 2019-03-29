package hgb.csp.problems.mapcoloring;

import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.importer.XmlImporter;
import hgb.csp.problems.Problem;
import hgb.csp.problems.ProblemFactory;
import hgb.csp.utils.ImplementationUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.ListenableGraph;

/**
 *
 * @author Alexander
 */
public class MapColoringFactory extends ProblemFactory {

   public static final String SVG_FILE = "SVG_FILE";
   public static final String NUMBER_COLORS = "NUMBER_COLORS";

   public MapColoringFactory() {
      super("Map coloring");
      setProblemParameterView(new MapColoringPropertyPanel());
   }

   @Override
   public Problem getProblem(Map<String, Object> parameters) {
      Problem problem = new Problem("USA-Coloring");
      problem.setName("USA map coloring");
      MapColoringViewPanel mvp = new MapColoringViewPanel();

      FileInputStream fi = null;
      try {
         fi = new FileInputStream("usa.svg");
         mvp.readSvgFromInput(fi, "MapUsa");
      } catch (Exception e) {
         System.out.println(e.getMessage());
      } finally {
         try {
            if (fi != null) {
               fi.close();
            }
         } catch (IOException ex) {
            Logger.getLogger(MapColoringFactory.class.getName()).log(Level.SEVERE, null, ex);
         }
      }

      if (parameters.containsKey(MapColoringFactory.NUMBER_COLORS)) {
         int n = (Integer) parameters.get(MapColoringFactory.NUMBER_COLORS);
         problem.setCsp(getMapGraph());
         for (Variable v : problem.getCsp().vertexSet()) {
            v.setDomain(ImplementationUtils.getIntegers(n));
         }
      }
      problem.setProposedView(mvp);
      return problem;
   }

   private ListenableGraph<Variable, Constraint> getMapGraph() {
      XmlImporter importer = new XmlImporter();
      ListenableGraph<Variable, Constraint> graph = importer.read("usa.xml");

      return graph;
   }
}
