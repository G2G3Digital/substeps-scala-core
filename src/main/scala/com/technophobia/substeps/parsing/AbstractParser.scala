package com.technophobia.substeps.parsing

import scala.util.parsing.combinator.RegexParsers
import java.io.Reader
import com.technophobia.substeps.domain.events.{ParsingFailed, ParsingSuccessful, ParsingStarted, DomainEventPublisher}

abstract class AbstractParser[T] extends RegexParsers  {

  override val skipWhitespace = false
  override val whiteSpace                    = """[ \t]+""".r

  def eol: Parser[Any]                       = """[ ]*\r?\n""".r

  protected def entryPoint: Parser[T];

  def parse(fileName: String, reader: Reader) = {

    DomainEventPublisher.instance().publish(ParsingStarted(fileName))
    val result = parseAll(entryPoint, reader)

    val completionEvent = result match {

      case Success(_, _) => ParsingSuccessful(fileName)
      case x => ParsingFailed(fileName, x.toString)
    }

    DomainEventPublisher.instance().publish(completionEvent)

    result
  }

  def parseOrFail(fileName: String, reader: Reader) : T = {

    parse(fileName, reader) match {

      case Success(content, _) => content
      case x => throw new ParseFailureException(x.toString)

    }

  }
}
