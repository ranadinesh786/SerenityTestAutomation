Feature: ETL Data Validation

  @etl
  Scenario: Validate data transformation
    Given the source data is loaded
    When the ETL job is executed
    Then the target data should match the expected results
    And the Spark job is run on the server with command "your_command" on host "your_host" with user "your_user" and password "your_password"
