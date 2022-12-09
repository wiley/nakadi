package com.wiley.epdcs.eventhub.test

import com.wiley.epdcs.eventhub.data.AssignmentEventDataFeeder
import com.wiley.epdcs.eventhub.script.script
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef.http

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class EventHubLoadTest extends Simulation with AssignmentEventDataFeeder {

  val httpConf = http
    .baseUrl("https://eventhub.dev.tc.private.wiley.host")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .disableCaching
    .headers(Map(
      "Transaction-Id" -> "2eecea7d-0309-46ca-81e6-c2dd287ac54f"
    ))
    .warmUp("https://eventhub.dev.tc.private.wiley.host")
    .shareConnections

  private final def getProperty(propertyName: String, defaultValue: String): String = {
    Option(System.getenv(propertyName)).orElse(Option(System.getProperty(propertyName))).getOrElse(defaultValue)
  }

  setUp(
    script.create_assignment_event.inject(
      rampUsersPerSec(1).to(18).during(5 minutes),
      constantUsersPerSec(18).during(60 minutes),
      rampUsersPerSec(18).to(1).during(10 minutes)
    ),
    script.create_assignment_event2.inject(
      rampUsersPerSec(1).to(18).during(10 minutes),
      constantUsersPerSec(18).during(60 minutes),
      rampUsersPerSec(18).to(1).during(10 minutes)
    ),
    script.create_assignment_event3.inject(
      rampUsersPerSec(1).to(18).during(10 minutes),
      constantUsersPerSec(18).during(60 minutes),
      rampUsersPerSec(18).to(1).during(10 minutes)
    ),
    script.create_assignment_event4.inject(
      rampUsersPerSec(1).to(18).during(10 minutes),
      constantUsersPerSec(18).during(60 minutes),
      rampUsersPerSec(18).to(1).during(10 minutes)
    ),
    script.create_assignment_event5.inject(
      rampUsersPerSec(1).to(18).during(10 minutes),
      constantUsersPerSec(18).during(60 minutes),
      rampUsersPerSec(18).to(1).during(10 minutes)
    )
  ).protocols(httpConf)
}

case class AssignmentProgressConfig(baseUrl: String)
