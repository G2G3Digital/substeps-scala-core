package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

abstract class Scenario(val title: String, val tags: Set[Tag]) {

  def run():RunResult

}
