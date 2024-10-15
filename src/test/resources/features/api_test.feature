Feature: Dummy API Test

  @APITest
  Scenario: Get request to dummy API
    Given I set the base URI to "https://jsonplaceholder.typicode.com"
    When I send a GET request to "/posts/1"
    Then the status code should be 200
    And the response should contain "userId" with value "1"
