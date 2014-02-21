package com.technophobia.substeps

import java.io.FileReader
import com.technophobia.substeps.repositories.SubstepRepository

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