package hgb.csp.mvc;

import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class TestMVC {
   private SubjectImpl subject;

   /**
    * Used to demonstrate the effect of observers.
    */
   private static class TestHelper {
      private int value;

      public int getValue() {
         return value;
      }

      public void setValue(int value) {
         this.value = value;
      }

   }
   
   public TestMVC() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() {
      subject = new SubjectImpl();
   }

   @After
   public void tearDown() {
   }

   @Test
   public void testHandlesFunctionality() {
      final TestHelper helper = new TestHelper();
      helper.setValue(5);

      Assert.assertEquals(5, helper.getValue());
      Object listener = new Object(){
         @Handles(type="Test")
         public void testMethod(Event e) {
            helper.setValue(1);
            System.out.println("AM I HERE?");
         }
      };

      subject.addObserver(listener);

      subject.raiseEvent(new Event("Test"));
      Assert.assertEquals(1, helper.getValue());

      subject.removeAllObservers();
      helper.setValue(2);

      subject.raiseEvent(new Event("Test"));
      Assert.assertEquals(2, helper.getValue());
   }
}
