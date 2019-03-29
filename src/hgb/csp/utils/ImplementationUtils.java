package hgb.csp.utils;

import hgb.csp.domain.DomainItem;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Utility class containing logic that is needed
 * at different places in the application
 * @author Alexander Schiendorfer
 */
public final class ImplementationUtils {

   /**
    * Provides a standard linear algorithm to shuffle arrays.
    * useful for random strategies
    * @param domainItems the array to be shuffled
    */
   public static void shuffle(final Object[] domainItems) {
      if (domainItems == null) {
         return;
      }
      
      Object helper;
      final Random rand = new SecureRandom();
      for (int j = domainItems.length - 1; j > 1; j--) {
         final int other = rand.nextInt(j - 1);
         helper = domainItems[other];
         domainItems[other] = domainItems[j];
         domainItems[j] = helper;
      }
   }

    public static boolean containsEqualObject(Collection<Object> takenObjects, Object value) {
        for(Object o:takenObjects)
            if(o.equals(value))
                return true;
        return false;
    }

  private ImplementationUtils() { };
  
  /**
   * Performs a Thread.sleep() operation and
   * deals with the interrupted exception
   * @param milliseconds to wait
   */
    public static void sleep(final long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // nothing necessary
        }
    }

    /**
     * Returns a list of integers serving as the domain items.
     * @param n represents the last integer
     * @return a list of DomainItems of integers ranging from 1 to n
     */
    public static List getIntegers(int n) {
        List ints = new ArrayList();
        for (int i = 0; i < n; i++) {
            ints.add(new DomainItem(i + 1));
        }
        return ints;
    }
}