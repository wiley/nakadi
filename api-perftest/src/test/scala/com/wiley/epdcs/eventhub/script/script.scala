package com.wiley.epdcs.eventhub.script

import com.wiley.epdcs.eventhub.data.AssignmentEventDataFeeder
import com.wiley.epdcs.eventhub.endpoint.AssignmentEvent
import io.gatling.core.Predef._

object script extends AssignmentEventDataFeeder {

  val create_assignment_event = scenario("Create_Assignment_Event_1")
    .feed(assignmentForDataCreation.queue)
    .exec(v1Flows.CREATEASSIGNMENT)

  val create_assignment_event2 = scenario("Create_Assignment_Event_2")
    .feed(assignmentForDataCreation.queue)
    .exec(v1Flows.CREATEASSIGNMENT2)

  val create_assignment_event3 = scenario("Create_Assignment_Event_3")
    .feed(assignmentForDataCreation.queue)
    .exec(v1Flows.CREATEASSIGNMENT3)

  val create_assignment_event4 = scenario("Create_Assignment_Event_4")
    .feed(assignmentForDataCreation.queue)
    .exec(v1Flows.CREATEASSIGNMENT4)

  val create_assignment_event5 = scenario("Create_Assignment_Event_5")
    .feed(assignmentForDataCreation.queue)
    .exec(v1Flows.CREATEASSIGNMENT5)

  object v1Flows {
    def CREATEASSIGNMENT = {
      AssignmentEvent.createAssignment
    }
    def CREATEASSIGNMENT2 = {
      AssignmentEvent.createAssignment2
    }
    def CREATEASSIGNMENT3 = {
      AssignmentEvent.createAssignment3
    }
    def CREATEASSIGNMENT4 = {
      AssignmentEvent.createAssignment4
    }
    def CREATEASSIGNMENT5 = {
      AssignmentEvent.createAssignment5
    }
  }
}
