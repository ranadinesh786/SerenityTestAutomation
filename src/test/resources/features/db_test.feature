Feature: Dummy Oracle DB Test

  Scenario: Querying the Oracle database
    Given I connect to the Oracle database with URL "jdbc:oracle:thin:@localhost:1521:xe", user "username", and password "password"
    When I execute the query "SELECT * FROM DUMMY_TABLE WHERE ID = 1"
    Then the result should contain a column "NAME" with value "John Doe"
    And I close the database connection
