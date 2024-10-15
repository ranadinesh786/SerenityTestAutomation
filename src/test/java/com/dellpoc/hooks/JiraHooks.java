package com.dellpoc.hooks;

import com.dellpoc.utils.JiraUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.IOException;
import java.net.URISyntaxException;

public class JiraHooks {

    private JiraUtils jiraUtils = new JiraUtils();

    @Before
    public void beforeScenario(Scenario scenario) {
        // You can add any setup code here if needed
    }

    @After
    public void afterScenario(Scenario scenario) {
        String issueKey = getIssueKeyFromTags(scenario);
        if (issueKey != null) {
            String status = scenario.isFailed() ? "Failed" : "Passed";
            try {
                jiraUtils.updateIssueStatus(issueKey, status);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getIssueKeyFromTags(Scenario scenario) {
        for (String tag : scenario.getSourceTagNames()) {
            if (tag.startsWith("@")) {
                return tag.substring(1); // Remove the '@' character
            }
        }
        return null;
    }
}
