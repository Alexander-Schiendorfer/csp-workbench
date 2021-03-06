/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BacktrackingPanel.java
 *
 * Created on Dec 25, 2010, 8:52:49 PM
 */

package hgb.csp.search.localsearch.view;

import hgb.csp.search.localsearch.ChoiceVariable;
import hgb.csp.search.localsearch.LocalSearchData;
import hgb.csp.search.backtracking.KeyStrategyPair;
import hgb.csp.search.localsearch.NeighborFunction;
import hgb.csp.view.SolverConfigurationPanel;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Alexander
 */
public class LocalSearchPanel extends javax.swing.JPanel implements SolverConfigurationPanel {

    /** Creates new form BacktrackingPanel */
    public LocalSearchPanel() {
        initComponents();
        stepTextField.setText(Integer.toString(stepSlider.getValue()));
        choiceVariableComboBox.setModel(new DefaultComboBoxModel(ChoiceVariable.values()));
        neighborFunction.setModel(new DefaultComboBoxModel(NeighborFunction.values()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel1 = new javax.swing.JLabel();
      stepTextField = new javax.swing.JTextField();
      stepSlider = new javax.swing.JSlider();
      jLabel2 = new javax.swing.JLabel();
      choiceVariableComboBox = new javax.swing.JComboBox();
      jLabel3 = new javax.swing.JLabel();
      neighborFunction = new javax.swing.JComboBox();

      jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
      jLabel1.setText("Choose the maximal steps");

      stepTextField.setEditable(false);
      stepTextField.setText("1000");

      stepSlider.setMajorTickSpacing(100);
      stepSlider.setMaximum(2000);
      stepSlider.setMinimum(100);
      stepSlider.setMinorTickSpacing(100);
      stepSlider.setPaintTicks(true);
      stepSlider.setSnapToTicks(true);
      stepSlider.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            stepSliderStateChanged(evt);
         }
      });

      jLabel2.setFont(new java.awt.Font("Verdana", 1, 12));
      jLabel2.setText("Variable choice strategy");

      jLabel3.setFont(new java.awt.Font("Verdana", 1, 12));
      jLabel3.setText("Neighbor function");

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addContainerGap(98, Short.MAX_VALUE))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(jLabel2)
                  .addContainerGap(110, Short.MAX_VALUE))
               .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                     .addComponent(choiceVariableComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .addComponent(stepSlider, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                  .addComponent(stepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(19, 19, 19))
               .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                     .addComponent(neighborFunction, 0, 200, Short.MAX_VALUE))
                  .addGap(70, 70, 70))))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(stepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(stepSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(choiceVariableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(neighborFunction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(113, Short.MAX_VALUE))
      );
   }// </editor-fold>//GEN-END:initComponents

    private void stepSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stepSliderStateChanged
        stepTextField.setText(Integer.toString(stepSlider.getValue()));
    }//GEN-LAST:event_stepSliderStateChanged

    @Override
    public void setEnabled(boolean enabled) {
        stepSlider.setEnabled(enabled);
    }

    public Object getSolverData() {
        LocalSearchData data = new LocalSearchData();
        data.setMaxSteps(stepSlider.getValue());
        data.setChoiceVariable((ChoiceVariable) choiceVariableComboBox.getSelectedItem());
        data.setNeighborFunction((NeighborFunction) neighborFunction.getSelectedItem());
        return data;
    }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JComboBox choiceVariableComboBox;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JComboBox neighborFunction;
   private javax.swing.JSlider stepSlider;
   private javax.swing.JTextField stepTextField;
   // End of variables declaration//GEN-END:variables

    private ListCellRenderer getCellRenderer() {
       return new DefaultListCellRenderer(){
             @Override
           public Component getListCellRendererComponent(
                   JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
               super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
               if (value instanceof KeyStrategyPair<?>) {
                   KeyStrategyPair<?> mec = (KeyStrategyPair<?>)value;
                   setText(mec.getKey());
               }
               return this;
           }
        };
    }

}
