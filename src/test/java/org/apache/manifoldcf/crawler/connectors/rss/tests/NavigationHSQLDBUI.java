/* $Id$ */

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.manifoldcf.crawler.connectors.rss.tests;

import org.junit.*;

import org.openqa.selenium.By;

/** Basic UI navigation tests */
public class NavigationHSQLDBUI extends ConnectorBaseUIHSQLDB
{
  @Test
  public void createConnections() throws Exception
  {
    tester.gotoUrl("http://localhost:8345/mcf-crawler-ui/index.jsp");

    //Login
    tester.waitForElementWithName("loginform");
    tester.setValue("userID","admin");
    tester.setValue("password","admin");
    tester.clickButton("Login");
    tester.verifyHeader("Welcome to Apache ManifoldCF™");
    tester.navigateTo("List output connections");
    tester.clickButton("Add a new output connection");

    // Fill in a name
    tester.waitForElementWithName("connname");
    tester.setValue("connname","Null Output Connection");

    //Goto to Type tab
    tester.clickTab("Type");

    // Select a type
    tester.waitForElementWithName("classname");
    tester.selectValue("classname","org.apache.manifoldcf.agents.output.nullconnector.NullConnector");
    tester.clickButton("Continue");

    // Visit the Throttling tab
    tester.clickTab("Throttling");

    // Go back to the Name tab
    tester.clickTab("Name");

    // Now save the connection.
    tester.clickButton("Save");

    // Define a repository connection via the UI
    tester.navigateTo("List repository connections");
    tester.clickButton("Add new connection");

    tester.waitForElementWithName("connname");
    tester.setValue("connname","RSS Repository Connection");

    // Select a type
    tester.clickTab("Type");
    tester.selectValue("classname","org.apache.manifoldcf.crawler.connectors.rss.RSSConnector");
    tester.clickButton("Continue");

    // Visit the Throttling tab
    tester.clickTab("Throttling");

    // Visit the rest of the tabs - Email first
    tester.clickTab("Email");
    tester.setValue("email","kishore@apache.org");

    // Robots
    tester.clickTab("Robots");
    tester.selectValue("robotsusage","none");

    // Bandwidth
    tester.clickTab("Bandwidth");

    // Proxy
    tester.clickTab("Proxy");

    // Go back to the Name tab
    tester.clickTab("Name");
    tester.clickButton("Save");

    // Create a job
    tester.navigateTo("List jobs");
    //Add a job
    tester.clickButton("Add a new job");
    tester.waitForElementWithName("description");
    //Fill in a name
    tester.setValue("description","RSS Job");
    tester.clickTab("Connection");

    // Select the connections
    tester.selectValue("output_connectionname","Null Output Connection");
    tester.selectValue("output_precedent","-1");
    tester.clickButton("Add output",true);
    tester.waitForElementWithName("connectionname");
    tester.selectValue("connectionname","RSS Repository Connection");
    
    tester.clickButton("Continue");

    // Visit all the tabs.  Scheduling tab first
    tester.clickTab("Scheduling");
    tester.selectValue("dayofweek","0");
    tester.selectValue("hourofday","1");
    tester.selectValue("minutesofhour","30");
    tester.selectValue("monthofyear","11");
    tester.selectValue("dayofmonth","none");
    tester.setValue("duration","120");
    tester.clickButton("Add Scheduled Time",true);
    tester.waitForElementWithName("editjob");

    //URLs tab
    tester.clickTab("URLs");
    tester.setValue("s0_rssurls","https://www.cnn.com");

    // Canonicalization tab
    tester.clickTab("Canonicalization");
    tester.clickButton("Add",true);

    // URL Mappings tab
    tester.clickTab("URL Mappings");
    //Time values tab
    tester.clickTab("Time Values");
    //Security tab
    tester.clickTab("Security");
    // Dechromed Content tab
    tester.clickTab("Dechromed Content");

    // Save the job
    tester.clickButton("Save");

    tester.waitUntilPresenceOfElementLocated(By.id("job"));
    String jobID = tester.getAttributeValueById("job","jobid");
    System.out.println("JobId: " + jobID);

    //Delete the job
    tester.clickButton("Delete");
    tester.acceptAlert();

    //Wait for the job to go away
    tester.navigateTo("Manage jobs");
    tester.waitForElementWithName("liststatuses");
    while (tester.exists(By.cssSelector("span[jobid=\"" + jobID + "\"]")))
    {
      tester.clickButton("Refresh");
      tester.waitForElementWithName("liststatuses");
      //Let us wait for a second.
      Thread.sleep(1000);
    }

    // Delete the repository connection
    tester.navigateTo("List repository connections");
    tester.clickButtonByTitle("Delete RSS Repository Connection");
    tester.acceptAlert();

    // Delete the output connection
    tester.navigateTo("List output connections");
    tester.clickButtonByTitle("Delete Null Output Connection");
    tester.acceptAlert();
  }

}
