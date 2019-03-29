package hgb.csp.events;

import hgb.csp.mvc.Event;

/**
 *
 * @author Alexander
 */
public class UICommandEvent extends Event {
   public static final String PROBLEM_SELECTED = "problemSelected";

   private Object attachedObject;

   public UICommandEvent(String eventType) {
      super(eventType);
   }

   public UICommandEvent(String eventType, Object attachedProperty) {
      super(eventType);
      this.attachedObject = attachedProperty;
   }

   public Object getAttachedObject() {
      return attachedObject;
   }

   public void setAttachedObject(Object attachedObject) {
      this.attachedObject = attachedObject;
   }
}
