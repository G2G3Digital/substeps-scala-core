package com.technophobia.substeps

import com.technophobia.substeps.model.{WrittenSubstep, Substep}

class SubstepsFileParser extends AbstractParser[List[Substep]] {

  protected override def entryPoint = substepsFile;

  private def substepsFile: Parser[List[Substep]] = rep(eol) ~> repsep(substepDef, rep1(eol)) <~ rep(eol)

  private def substepDef: Parser[Substep] = (signature <~ rep1(eol)) ~ rep1sep(substepInvocation, eol) ^^ {

    case(signature ~ substepUsages) => WrittenSubstep(signature, substepUsages:_*)
  }

  def substepInvocation: Parser[String] = """([^:\r\n])+""".r ^^ (_.trim)

  private def signature: Parser[String] = "Define:" ~> opt(whiteSpace) ~> """[^\n\r]+""".r ^^ ((x) => x.trim)
}
