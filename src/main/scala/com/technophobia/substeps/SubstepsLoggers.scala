package com.technophobia.substeps

/**
 * @author rbarefield
 */
object SubstepsLoggers {

  var loggers : List[SubstepsLogger] = List()

  def addLogger(logger: SubstepsLogger) = loggers ::= logger

  val compositeLogger = new SubstepsLogger {

    def error(message: String, cause: Throwable): Unit = loggers.foreach(_.error(message, cause))

    def error(message: String): Unit = loggers.foreach(_.error(message))

    def debug(message: String): Unit = loggers.foreach(_.debug(message))

    def info(message: String): Unit = loggers.foreach(_.info(message))
  }
}
