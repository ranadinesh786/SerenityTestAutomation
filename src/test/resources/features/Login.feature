Feature: Login

  Scenario: Login with valid credentials
    Given I open the login page
    When I login with valid credentials
    Then I should see the dashboard
