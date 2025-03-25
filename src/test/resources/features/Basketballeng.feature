Feature: User Registration on Basketball England

  Scenario: Successful user registration
    Given the user is on the registration page
    When they enter valid registration details
    And they accept the terms and conditions and they press i am over 18
    And they accept code of ethics
    And they submit the form
    Then the account should be created successfully

  Scenario: Missing last name
    Given the user is on the registration page
    When they enter valid details except last name
    And they accept the terms and conditions and they press i am over 18
    And they submit the form
    Then an error message should be displayed for missing last name

  Scenario: Passwords do not match
    Given the user is on the registration page
    When they enter valid details but mismatched passwords
    And they accept the terms and conditions and they press i am over 18
    And they submit the form
    Then an error message should be displayed for password mismatch

  Scenario: Terms and conditions not accepted
    Given the user is on the registration page
    When they enter valid registration details
    And they do not accept the terms and conditions
    And they submit the form
    Then an error message should be displayed for unaccepted terms