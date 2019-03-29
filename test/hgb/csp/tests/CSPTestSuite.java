/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hgb.csp.tests;

import hgb.csp.mvc.TestMVC;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Alexander
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestMVC.class})
public class CSPTestSuite {

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() throws Exception {
   }

   @After
   public void tearDown() throws Exception {
   }

}