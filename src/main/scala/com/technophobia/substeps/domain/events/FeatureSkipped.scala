package com.technophobia.substeps.domain.events

import java.util.Date
import com.technophobia.substeps.domain.Feature

case class FeatureSkipped(val feature: Feature, override val timeOccurred: Date = new Date) extends SubstepsDomainEvent