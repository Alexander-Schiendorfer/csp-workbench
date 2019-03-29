/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hgb.csp.importer;

import com.sun.org.apache.bcel.internal.util.Class2HTML;
import hgb.csp.domain.Constraint;
import hgb.csp.domain.Variable;
import hgb.csp.utils.ImplementationUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.events.StartElement;
import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Alexander
 */
public class XmlImporter {

  private static class CSPContentHandler extends DefaultHandler {

    private Map<String, List<Variable>> variablesPerDomain;  // necessary to keep track of variables and their domains
    private ListenableGraph<Variable, Constraint> graph;
    private Map<String, Variable> variables;                 // fast access at variables by name

    public CSPContentHandler(ListenableGraph<Variable, Constraint> graph) {
      this.graph = graph;
      variablesPerDomain = new HashMap<String, List<Variable>>();
      variables = new HashMap<String, Variable>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (localName.equals("Variable")) {
        String name = attributes.getValue("name");
        String id = attributes.getValue("ID");
        String domain = attributes.getValue("domain");

        Variable v = new Variable(name, Integer.parseInt(id));
        graph.addVertex(v);
        variables.put(name, v);

        if (!variablesPerDomain.containsKey(domain)) {
          variablesPerDomain.put(domain, new ArrayList<Variable>());
        }
        variablesPerDomain.get(domain).add(v);
      } else if (localName.equals("Domain")) {
        String domainName = attributes.getValue("name");
        String type = attributes.getValue("type");

        if (variablesPerDomain.containsKey(domainName)) {
          for (Variable v : variablesPerDomain.get(domainName)) {
            if (type.equals("integers")) { // others have to be implemented
              String value = attributes.getValue("value");
              int n = Integer.parseInt(value);

              v.setDomain(ImplementationUtils.getIntegers(n));
            }
          }
        }
      } else if (localName.equals("Constraint")) {
        String firstName = attributes.getValue("first");
        String secondName = attributes.getValue("second");
        String classString = attributes.getValue("class");

        Variable first = variables.get(firstName);
        Variable second = variables.get(secondName);

        if (classString != null) {

          try {
            // instantiate constraint with class
            Class c = Class.forName(classString);
            graph.addEdge(first, second, (Constraint) c.newInstance());
          } catch (InstantiationException ex) {
            Logger.getLogger(XmlImporter.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IllegalAccessException ex) {
            Logger.getLogger(XmlImporter.class.getName()).log(Level.SEVERE, null, ex);
          } catch (ClassNotFoundException ex) {
            Logger.getLogger(XmlImporter.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          graph.addEdge(first, second);
        }
      }
    }
  }

  public ListenableGraph<Variable, Constraint> read(String filename) {
    FileInputStream fis = null;
    ListenableGraph<Variable, Constraint> csp = null;

    try {
      fis = new FileInputStream(filename);
      csp = read(fis);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(XmlImporter.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException ex) {
          Logger.getLogger(XmlImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    return csp;
  }

  public ListenableGraph<Variable, Constraint> read(InputStream input) {
    XMLReader reader;
    ListenableGraph<Variable, Constraint> graph = new ListenableUndirectedGraph<Variable, Constraint>(Constraint.class);
    try {
      reader = XMLReaderFactory.createXMLReader();
      reader.setContentHandler(new CSPContentHandler(graph));
      reader.parse(new InputSource(input));
    } catch (IOException ex) {
      Logger.getLogger(XmlImporter.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SAXException ex) {
      Logger.getLogger(XmlImporter.class.getName()).log(Level.SEVERE, null, ex);
    }
    return graph;
  }
}
