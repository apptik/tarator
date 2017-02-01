@skipWS @skipAngular
Feature: Agency page

  Scenario: The user sees an email contact button
    When the user navigates to the Agency page "#Website"
    Then the user should see the email agency button icon

  Scenario: The user sees a phone contact button if there is a phone info
    Given there is an agency with at least one phone number
    When the user navigates to the Agency page
    Then the user should see the phone agency button icon

  Scenario: The user shouldn't see a phone contact button if there is no phone info
    Given there is an agency that doesn't have any phone numbers
    When the user navigates to the Agency page
    Then the user should not see the phone agency button icon

  Scenario: The user sees a share button
    When the user navigates to the Agency page
    Then the user should see the share agency button icon

  Scenario: The user sees a map section with all the properties of the agency
    When the user navigates to the Agency page
    Then the user should see a map section of the location of the agency and all its properties
