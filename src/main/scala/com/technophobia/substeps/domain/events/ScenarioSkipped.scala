package com.technophobia.substeps.domain.events

import java.util.Date
import com.technophobia.substeps.domain.{Scenario, Feature}

case class ScenarioSkipped(val scenario: Scenario, override val timeOccurred: Date = new Date) extends SubstepsDomainEvent