package com.technophobia.substeps

import java.io.InputStreamReader

trait ParsingTestHelpers[T] {

  this: AbstractParser[T] =>

  protected def getSuccessfulParse(fileName: String) : T = {

    parseFile(fileName) match {

      case Success(feature, _) => feature
      case x => throw new AssertionError(x.toString)

    }

  }

  protected def parseFile(fileName: String) : ParseResult[T] = {

    val reader = new InputStreamReader(this.getClass.getResourceAsStream("/" + fileName))

    try {

      parse(reader)
    }
    finally {

      reader.close()
    }

  }


}
