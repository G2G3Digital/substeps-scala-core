package com.technophobia.substeps

import java.io.FileReader
import com.technophobia.substeps.domain.repositories.SubstepRepository
import com.technophobia.substeps.parsing.FeatureFileParser

object FeatureFileRunner extends FeatureFileParser(new SubstepRepository) {

  def main(args: Array[String]) {

    val reader = new FileReader(args(0))

    val result = parse(reader)  match {

      case Success(feature, in) => feature
      case x => x.toString

    }

    println(result)
  }
}