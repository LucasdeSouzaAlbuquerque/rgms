@i9n
Feature: news
  As research group member in RMGS system
  I want to publish, remove and modify news about our researches, schedule and any other thing.
  Jointly this I want too integrate an twitter account to publish news into RGMS system.

  Scenario: new news
    Given the system has no news with description "noticia1teste" and date "07-04-2012" for "SPG" research group
    When I create a news with description "noticia1teste" and date "07-04-2012" for "SPG" research group
    Then the news  with description  "noticia1teste", date "07-04-2012" and "SPG" research group is properly stored by the system


  Scenario: delete news
    Given the system has a news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    When I delete the news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    Then the news with "noticiaTeste" and date "07-04-2012" doesnt exists to "SPG" research group


  Scenario: new news with existing matching news
    Given the system has a news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    When I create a news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    Then the news  with "noticiaTeste" and date "07-04-2012" is not registered to "SPG" research group


  Scenario: integrate Twitter account
    Given the research group "SPG" in the system has no Twitter account associated
    When I associate the account "@HumanBrainProj" to "SPG" group
    Then "SPG" research group has a twitter account "@HumanBrainProj" registered
    #Then the news can be retrieved by a Twitter post with "@testetwitteracount"

  Scenario: update news from twitter account
    Given the research group "SPG" in the system has a Twitter account "@HumanBrainProj" associated
     And  the research group "SPG" news list is empty
     When I request to update the news from Twitter to research group "SPG"
     Then news of "SPG" research group has been updated

  Scenario: consecutive update without duplicate news
    Given the research group "SPG" in the system has a Twitter account "@HumanBrainProj" associated
    And   twitter account associated with "SPG" research group has been updated once
    When  I request to update the news from Twitter to research group "SPG"
    Then  there is no duplicated news in Twitter account associated with research group "SPG"


#if( $new news web)
  Scenario: new news web
    Given I am at the publications menu
    And I create a research group
    When I select the "News" option at the publications menu
    And I select the "new news" option at the news page
    Then I can fill the news details
#end

#if($newInvalidNewsInvalidResearchGroup)
  Scenario: new invalid news (Invalid research group)
    Given the system has no news with description "noticiaTeste" and date "01-01-2014" for "abc" research group
    When I create a news with description "noticiaTeste" and date "01-01-2014" for "abc" research group
    Then the news with description "noticiaTeste", date "01-01-2014" and "abc" research group is not stored by the system because it is invalid
#end

  Scenario: new invalid news
    Given the system has some stored news
    When I create a news with invalid fields
    Then the news with with the invalid fields is not stored by the system because it is invalid

  Scenario: edit existing news
    Given the system has a news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    When I edit the news with description "noticiaTeste" to "newDescription", date "07-04-2012" and "SPG" research group
    Then the news "newDescription", date "07-04-2012" and "SPG" research group is properly updated by the system

  Scenario: remove existing news web
    Given I am at the publications menu
    When I select the news page
    And the news "Noticia1" is stored in the system
    And I select the option to remove in news show page
    Then the news "Noticia1" is properly removed by the system