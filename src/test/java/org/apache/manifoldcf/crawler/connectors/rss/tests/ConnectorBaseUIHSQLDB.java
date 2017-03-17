package org.apache.manifoldcf.crawler.connectors.rss.tests;

import org.apache.manifoldcf.core.tests.SeleniumTester;

import org.junit.*;

/**
 * Created by kishore on 3/18/17.
 */
public class ConnectorBaseUIHSQLDB
{
  protected SeleniumTester tester = null;
  
  @Before
  public void setupHTMLTester()
    throws Exception
  {
    tester = new SeleniumTester();
    tester.setup();
  }
  
  @After
  public void teardownHTMLTester()
    throws Exception
  {
    if(tester != null)
    {
      tester.teardown();
      tester = null;
    }
  }
}
