package com.technophobia.substeps

import scala.util.parsing.combinator.RegexParsers
import java.io.Reader

abstract class AbstractParser[T] extends RegexParsers  {

  override val skipWhitespace = false
  override val whiteSpace                    = """[ \t]+""".r

  def eol: Parser[Any]                       = """\r?\n""".r

  protected def entryPoint: Parser[T];

  def parse(reader: Reader) = parseAll(entryPoint, reader)

  def parseOrFail(reader: Reader) : T = {

    parse(reader) match {

      case Success(feature, _) => feature
      case x => throw new RuntimeException(x.toString)

    }

  }
}
