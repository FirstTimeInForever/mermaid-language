package com.github.firsttimeinforever.mermaid.lang.highlighting

import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidLexer
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class MermaidHighlighter : SyntaxHighlighterBase() {
  override fun getHighlightingLexer(): Lexer {
    return MermaidLexer()
  }

  private fun getPieHighlights(tokenType: IElementType): Array<TextAttributesKey>? {
    return when (tokenType) {
      MermaidTokens.Pie.PIE,
      MermaidTokens.Pie.SHOW_DATA -> arrayOf(MermaidTextAttributes.keyword)
      else -> null
    }
  }

  private fun getJourneyHighlights(tokenType: IElementType): Array<TextAttributesKey>? {
    return when (tokenType) {
      MermaidTokens.Journey.JOURNEY,
      MermaidTokens.Journey.SECTION -> arrayOf(MermaidTextAttributes.keyword)

      MermaidTokens.Journey.TASK_NAME,
      MermaidTokens.Journey.SECTION_TITLE -> arrayOf(MermaidTextAttributes.string)
      else -> null
    }
  }

  private fun getFlowchartHighlights(tokenType: IElementType): Array<TextAttributesKey>? {
    return when (tokenType) {
      MermaidTokens.Flowchart.FLOWCHART,
      MermaidTokens.Flowchart.SUBGRAPH,
      MermaidTokens.Flowchart.DIRECTION,
      MermaidTokens.Flowchart.STYLE,
      MermaidTokens.Flowchart.STYLE_OPT,
      MermaidTokens.Flowchart.CLASS_DEF -> arrayOf(MermaidTextAttributes.keyword)

      MermaidTokens.Flowchart.ARROW,
      MermaidTokens.Flowchart.START_ARROW,
      MermaidTokens.Flowchart.STYLE_SEPARATOR -> arrayOf(MermaidTextAttributes.operationSign)


      MermaidTokens.Flowchart.STYLE_VAL -> arrayOf(MermaidTextAttributes.string)

      MermaidTokens.Flowchart.DIR  -> arrayOf(MermaidTextAttributes.constant)

      MermaidTokens.Flowchart.STYLE_TARGET,
      MermaidTokens.Flowchart.CLASS -> arrayOf(MermaidTextAttributes.identifier)

      else -> null
    }
  }

  private fun getSequenceHighlights(tokenType: IElementType): Array<TextAttributesKey>? {
    return when (tokenType) {
      MermaidTokens.Sequence.SEQUENCE,
      MermaidTokens.Sequence.PARTICIPANT,
      MermaidTokens.Sequence.ACTOR,
      MermaidTokens.Sequence.AS,
      MermaidTokens.Sequence.ACTIVATE,
      MermaidTokens.Sequence.DEACTIVATE,
      MermaidTokens.Sequence.NOTE,
      MermaidTokens.Sequence.RIGHT_OF,
      MermaidTokens.Sequence.LEFT_OF,
      MermaidTokens.Sequence.OVER,
      MermaidTokens.Sequence.LOOP,
      MermaidTokens.Sequence.ALT,
      MermaidTokens.Sequence.ELSE,
      MermaidTokens.Sequence.OPT,
      MermaidTokens.Sequence.PAR,
      MermaidTokens.Sequence.AND,
      MermaidTokens.Sequence.RECT,
      MermaidTokens.Sequence.AUTONUMBER,
      MermaidTokens.Sequence.LINK,
      MermaidTokens.Sequence.LINKS -> arrayOf(MermaidTextAttributes.keyword)

      MermaidTokens.Sequence.SOLID_ARROW,
      MermaidTokens.Sequence.DOTTED_ARROW,
      MermaidTokens.Sequence.SOLID_OPEN_ARROW,
      MermaidTokens.Sequence.DOTTED_OPEN_ARROW,
      MermaidTokens.Sequence.SOLID_CROSS,
      MermaidTokens.Sequence.DOTTED_CROSS,
      MermaidTokens.Sequence.SOLID_POINT,
      MermaidTokens.Sequence.DOTTED_POINT -> arrayOf(MermaidTextAttributes.operationSign)

      MermaidTokens.Sequence.MESSAGE -> arrayOf(MermaidTextAttributes.string)

      else -> null
    }
  }

  override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
    val pieHighlights = getPieHighlights(tokenType)
    val journeyHighlighter = getJourneyHighlights(tokenType)
    val flowchartHighlighter = getFlowchartHighlights(tokenType)
    val sequenceHighlighter = getSequenceHighlights(tokenType)
    return pieHighlights
      ?: journeyHighlighter
      ?: flowchartHighlighter
      ?: sequenceHighlighter
      ?: when (tokenType) {
        MermaidTokens.END,
        MermaidTokens.TITLE -> arrayOf(MermaidTextAttributes.keyword)

        MermaidTokens.TITLE_VALUE,
        MermaidTokens.DOUBLE_QUOTE,
        MermaidTokens.STRING_VALUE,
        MermaidTokens.ALIAS -> arrayOf(MermaidTextAttributes.string)

        MermaidTokens.LINE_COMMENT,
        MermaidTokens.COMMENT_TEXT,
        MermaidTokens.IGNORED -> arrayOf(MermaidTextAttributes.comment)

        MermaidTokens.ID -> arrayOf(MermaidTextAttributes.identifier)

        MermaidTokens.PLUS,
        MermaidTokens.MINUS -> arrayOf(MermaidTextAttributes.operationSign)

        else -> arrayOf(HighlighterColors.TEXT)
      }
  }
}
