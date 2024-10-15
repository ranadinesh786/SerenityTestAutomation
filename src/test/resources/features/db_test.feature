Feature: Database Testing

  Scenario: Execute SQL query and verify result
    Given I connect to the database
    When I execute the query "SELECT name FROM users WHERE id=1"
    Then the result should contain "John Doe"
