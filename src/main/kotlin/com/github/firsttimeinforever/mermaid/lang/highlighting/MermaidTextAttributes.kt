package com.github.firsttimeinforever.mermaid.lang.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey

object MermaidTextAttributes {
  val keyword = createTextAttributesKey("MERMAID_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
  val string = createTextAttributesKey("MERMAID_STRING", DefaultLanguageHighlighterColors.STRING)
  val comment = createTextAttributesKey("MERMAID_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
  val identifier = createTextAttributesKey("MERMAID_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
  val constant = createTextAttributesKey("MERMAID_CONSTANT", DefaultLanguageHighlighterColors.CONSTANT)
  val operationSign = createTextAttributesKey("MERMAID_OPERATION_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN)
}
