Feature: basic feature (do not leave this first line empty)
  some more description
  and yet some more lines

  Scenario: some rules
    Given there is a rule
    When we tap on a button
    Then we should see the rule

  @skipAndroid
  Scenario: we gonna skip this one for android
    Given whatever setup
    When whatever event
    Then whatever assert