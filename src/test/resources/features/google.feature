Feature: Google Search

  @smoke
  Scenario: Search for Serenity BDD
    Given I open the browser
    When I navigate to "https://parabank.parasoft.com/parabank/index.html"
    And I get the page title
    Then I close the browser

