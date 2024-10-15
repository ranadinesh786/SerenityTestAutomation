package com.dellpoc.utils;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JiraUtils {

    private static final String JIRA_URL = "https://your-jira-server";
    private static final String USERNAME = "your-username";
    private static final String PASSWORD = "your-password";

    private JiraRestClient getJiraRestClient() throws URISyntaxException {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(new URI(JIRA_URL), USERNAME, PASSWORD);
    }

    public void updateIssueStatus(String issueKey, String status) throws URISyntaxException, IOException {
        JiraRestClient client = getJiraRestClient();
        Issue issue = client.getIssueClient().getIssue(issueKey).claim();

        // Find the appropriate transition for the status
        Iterable<Transition> transitions = client.getIssueClient().getTransitions(issue).claim();
        Transition transition = null;
        for (Transition t : transitions) {
            if (t.getName().equalsIgnoreCase(status)) {
                transition = t;
                break;
            }
        }

        if (transition != null) {
            client.getIssueClient().transition(issue, new TransitionInput(transition.getId())).claim();
        } else {
            System.out.println("Transition not found for status: " + status);
        }

        client.close();
    }
}
