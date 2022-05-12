package com.github.firsttimeinforever.mermaid.lang.lexer

import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

object MermaidTokens {
  @JvmField
  val OPEN_DIRECTIVE = MermaidToken("OPEN_DIRECTIVE")

  @JvmField
  val CLOSE_DIRECTIVE = MermaidToken("CLOSE_DIRECTIVE")

//  @JvmField
//  val TYPE_DIRECTIVE = MermaidToken("TYPE_DIRECTIVE")
//
//  @JvmField
//  val PROPERTY_KEY = MermaidToken("PROPERTY_KEY")
//
//  @JvmField
//  val PROPERTY_VALUE = MermaidToken("PROPERTY_VALUE")
//
//  @JvmField
//  val ARG_DIRECTIVE = MermaidToken("ARG_DIRECTIVE")

  @JvmField
  val DIRECTIVE_TEXT = MermaidToken("DIRECTIVE_TEXT")

  @JvmField
  val COLON = MermaidToken("COLON")

  @JvmField
  val COMMA = MermaidToken("COMMA")

  @JvmField
  val OPEN_CURLY = MermaidToken("OPEN_CURLY")

  @JvmField
  val CLOSE_CURLY = MermaidToken("CLOSE_CURLY")

  @JvmField
  val SEMICOLON = MermaidToken("SEMICOLON")

  @JvmField
  val DOUBLE_QUOTE = MermaidToken("DOUBLE_QUOTE")

  @JvmField
  val STRING_VALUE = MermaidToken("STRING_VALUE")

  @JvmField
  val LINE_COMMENT = MermaidToken("LINE_COMMENT")

  @JvmField
  val COMMENT_TEXT = MermaidToken("COMMENT_TEXT")

  @JvmField
  val WHITE_SPACE: IElementType = TokenType.WHITE_SPACE

  @JvmField
  val BAD_CHARACTER: IElementType = TokenType.BAD_CHARACTER

  @JvmField
  val EOL = MermaidToken("EOL")

  @JvmField
  val TITLE = MermaidToken("TITLE")

  @JvmField
  val TITLE_VALUE = MermaidToken("TITLE_VALUE")

  @JvmField
  val IGNORED = MermaidToken("IGNORED")

  object Pie {
    @JvmField
    val PIE = MermaidToken("Pie.PIE")

    @JvmField
    val SHOW_DATA = MermaidToken("Pie.SHOW_DATA")

    @JvmField
    val VALUE = MermaidToken("Pie.VALUE")
  }

  object Journey {
    @JvmField
    val JOURNEY = MermaidToken("Journey.JOURNEY")

    @JvmField
    val SECTION = MermaidToken("Journey.SECTION")

    @JvmField
    val SECTION_TITLE = MermaidToken("Journey.SECTION_TITLE")

    @JvmField
    val TASK_NAME = MermaidToken("Journey.TASK_NAME")

    @JvmField
    val TASK_DATA = MermaidToken("Journey.TASK_DATA")
  }
}
