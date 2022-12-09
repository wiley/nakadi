package com.wiley.epdcs.eventhub.endpoint

import com.typesafe.config.Config
import com.wiley.epdcs.eventhub.test.AssignmentProgressConfig
import io.gatling.core.Predef.{ElFileBody, jsonPath, _}
import io.gatling.http.Predef._

object AssignmentEvent {

  def config(c: Config) =
    AssignmentProgressConfig(
      c.getString("assignment.baseUrl")
    )

  def createAssignment = {
    http("01 Create assignment event")
      .post("/event-types/eventhub.perftest_1/events")
      .body(ElFileBody("createAssignment.json"))
      .check(status.is(200))
  }
  def createAssignment2 = {
    http("01 Create assignment event")
      .post("/event-types/eventhub.perftest_2/events")
      .body(ElFileBody("createAssignment.json"))
      .check(status.is(200))
  }
  def createAssignment3 = {
    http("01 Create assignment event")
      .post("/event-types/eventhub.perftest_3/events")
      .body(ElFileBody("createAssignment.json"))
      .check(status.is(200))
  }
  def createAssignment4 = {
    http("01 Create assignment event")
      .post("/event-types/eventhub.perftest_4/events")
      .body(ElFileBody("createAssignment.json"))
      .check(status.is(200))
  }
  def createAssignment5 = {
    http("01 Create assignment event")
      .post("/event-types/eventhub.perftest_5/events")
      .body(ElFileBody("createAssignment.json"))
      .check(status.is(200))
  }
}
