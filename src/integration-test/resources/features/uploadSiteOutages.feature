Feature: As a client I should able to post the outages in the kraken system

  @happyPath
  Scenario: Post the valid site outage
    When I make a "POST" request on the path "/report-outages/norwich-pear-tree"
    Then I get the response code '200'
    And I get a response body matching below json:
    """
    {
      "noOfOutageReported" : 10
    }
    """

  @sadPath
  Scenario: Site without any outage
    When I make a "POST" request on the path "/report-outages/site-without-outages"
    Then I get the response code '404'
    And I get a response body matching below json:
    """
    {
      "message" : "No outage to post for site: site-without-outages"
    }
    """

  @sadPath
  Scenario: Invalid site id
    When I make a "POST" request on the path "/report-outages/kingfisher"
    Then I get the response code '404'
    And I get a response body matching below json:
    """
    {
      "message": "Record not found"
    }
    """