package com.github.firsttimeinforever.mermaid.lang.lexer

import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.COLON
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.COMMENT_TEXT
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.EOL
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.IGNORED
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.Journey
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.LINE_COMMENT
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.SECTION
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.SECTION_TITLE
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.TASK_DATA
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.TASK_NAME
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.TITLE
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.TITLE_VALUE
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens.WHITE_SPACE

class JourneyTest: MermaidLexerTestCase() {
  fun `test simple journey title and section title`() {
    val content = """
    journey
      title My working day
      section Go to work
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 16, " "),
      Token(TITLE_VALUE, 16, 30, "My working day"),
      Token(EOL, 30, 31, "\n"),
      Token(WHITE_SPACE, 31, 33, "  "),
      Token(SECTION, 33, 40, "section"),
      Token(WHITE_SPACE, 40, 41, " "),
      Token(SECTION_TITLE, 41, 51, "Go to work")
    )
    doTest(content, expected)
  }

  fun `test journey title and section title with whitespaces`() {
    val content = """
    journey
      title     My working day
      section       Go to work
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 20, "     "),
      Token(TITLE_VALUE, 20, 34, "My working day"),
      Token(EOL, 34, 35, "\n"),
      Token(WHITE_SPACE, 35, 37, "  "),
      Token(SECTION, 37, 44, "section"),
      Token(WHITE_SPACE, 44, 51, "       "),
      Token(SECTION_TITLE, 51, 61, "Go to work")
    )
    doTest(content, expected)
  }

  fun `test journey title and section title with whitespaces and sharp`() {
    val content = """
    journey
      title     My working# day
      section       Go to# work
    
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 20, "     "),
      Token(TITLE_VALUE, 20, 30, "My working"),
      Token(IGNORED, 30, 35, "# day"),
      Token(EOL, 35, 36, "\n"),
      Token(WHITE_SPACE, 36, 38, "  "),
      Token(SECTION, 38, 45, "section"),
      Token(WHITE_SPACE, 45, 52, "       "),
      Token(SECTION_TITLE, 52, 57, "Go to"),
      Token(IGNORED, 57, 63, "# work"),
      Token(EOL, 63, 64, "\n")
    )
    doTest(content, expected)
  }

  fun `test journey with one section and tasks`() {
    val content = """
    journey
      title My working day
      section Go to work
        Make tea: 5: Me
        Go upstairs: 3: Me
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 16, " "),
      Token(TITLE_VALUE, 16, 30, "My working day"),
      Token(EOL, 30, 31, "\n"),
      Token(WHITE_SPACE, 31, 33, "  "),
      Token(SECTION, 33, 40, "section"),
      Token(WHITE_SPACE, 40, 41, " "),
      Token(SECTION_TITLE, 41, 51, "Go to work"),
      Token(EOL, 51, 52, "\n"),
      Token(WHITE_SPACE, 52, 56, "    "),
      Token(TASK_NAME, 56, 64, "Make tea"),
      Token(COLON, 64, 65, ":"),
      Token(WHITE_SPACE, 65, 66, " "),
      Token(TASK_DATA, 66, 67, "5"),
      Token(COLON, 67, 68, ":"),
      Token(WHITE_SPACE, 68, 69, " "),
      Token(TASK_DATA, 69, 71, "Me"),
      Token(EOL, 71, 72, "\n"),
      Token(WHITE_SPACE, 72, 76, "    "),
      Token(TASK_NAME, 76, 87, "Go upstairs"),
      Token(COLON, 87, 88, ":"),
      Token(WHITE_SPACE, 88, 89, " "),
      Token(TASK_DATA, 89, 90, "3"),
      Token(COLON, 90, 91, ":"),
      Token(WHITE_SPACE, 91, 92, " "),
      Token(TASK_DATA, 92, 94, "Me")
    )
    doTest(content, expected)
  }

  fun `test journey with sharp after task`() {
    val content = """
    journey
      title My working day
      section Go to work
        Make tea: 5: Me#123
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 16, " "),
      Token(TITLE_VALUE, 16, 30, "My working day"),
      Token(EOL, 30, 31, "\n"),
      Token(WHITE_SPACE, 31, 33, "  "),
      Token(SECTION, 33, 40, "section"),
      Token(WHITE_SPACE, 40, 41, " "),
      Token(SECTION_TITLE, 41, 51, "Go to work"),
      Token(EOL, 51, 52, "\n"),
      Token(WHITE_SPACE, 52, 56, "    "),
      Token(TASK_NAME, 56, 64, "Make tea"),
      Token(COLON, 64, 65, ":"),
      Token(WHITE_SPACE, 65, 66, " "),
      Token(TASK_DATA, 66, 67, "5"),
      Token(COLON, 67, 68, ":"),
      Token(WHITE_SPACE, 68, 69, " "),
      Token(TASK_DATA, 69, 71, "Me"),
      Token(IGNORED, 71, 75, "#123")
    )
    doTest(content, expected)
  }

  fun `test journey with sharp in task`() {
    val content = """
    journey
      title My working day
      section Go to work
        Make tea: #5: Me
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 16, " "),
      Token(TITLE_VALUE, 16, 30, "My working day"),
      Token(EOL, 30, 31, "\n"),
      Token(WHITE_SPACE, 31, 33, "  "),
      Token(SECTION, 33, 40, "section"),
      Token(WHITE_SPACE, 40, 41, " "),
      Token(SECTION_TITLE, 41, 51, "Go to work"),
      Token(EOL, 51, 52, "\n"),
      Token(WHITE_SPACE, 52, 56, "    "),
      Token(TASK_NAME, 56, 64, "Make tea"),
      Token(COLON, 64, 65, ":"),
      Token(WHITE_SPACE, 65, 66, " "),
      Token(IGNORED, 66, 72, "#5: Me")
    )
    doTest(content, expected)
  }

  fun `test journey with two sections`() {
    val content = """
    journey
      title My working day
      section Go to work
        Make tea: 5: Me
      section Go home
        Go downstairs: 5: Me
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 16, " "),
      Token(TITLE_VALUE, 16, 30, "My working day"),
      Token(EOL, 30, 31, "\n"),
      Token(WHITE_SPACE, 31, 33, "  "),
      Token(SECTION, 33, 40, "section"),
      Token(WHITE_SPACE, 40, 41, " "),
      Token(SECTION_TITLE, 41, 51, "Go to work"),
      Token(EOL, 51, 52, "\n"),
      Token(WHITE_SPACE, 52, 56, "    "),
      Token(TASK_NAME, 56, 64, "Make tea"),
      Token(COLON, 64, 65, ":"),
      Token(WHITE_SPACE, 65, 66, " "),
      Token(TASK_DATA, 66, 67, "5"),
      Token(COLON, 67, 68, ":"),
      Token(WHITE_SPACE, 68, 69, " "),
      Token(TASK_DATA, 69, 71, "Me"),
      Token(EOL, 71, 72, "\n"),
      Token(WHITE_SPACE, 72, 74, "  "),
      Token(SECTION, 74, 81, "section"),
      Token(WHITE_SPACE, 81, 82, " "),
      Token(SECTION_TITLE, 82, 89, "Go home"),
      Token(EOL, 89, 90, "\n"),
      Token(WHITE_SPACE, 90, 94, "    "),
      Token(TASK_NAME, 94, 107, "Go downstairs"),
      Token(COLON, 107, 108, ":"),
      Token(WHITE_SPACE, 108, 109, " "),
      Token(TASK_DATA, 109, 110, "5"),
      Token(COLON, 110, 111, ":"),
      Token(WHITE_SPACE, 111, 112, " "),
      Token(TASK_DATA, 112, 114, "Me")
    )
    doTest(content, expected)
  }

  fun `test journey task with whitespaces`() {
    val content = """
    journey
      title My working day
      section Go to work
            Make tea  :    5   :   Me
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 16, " "),
      Token(TITLE_VALUE, 16, 30, "My working day"),
      Token(EOL, 30, 31, "\n"),
      Token(WHITE_SPACE, 31, 33, "  "),
      Token(SECTION, 33, 40, "section"),
      Token(WHITE_SPACE, 40, 41, " "),
      Token(SECTION_TITLE, 41, 51, "Go to work"),
      Token(EOL, 51, 52, "\n"),
      Token(WHITE_SPACE, 52, 60, "        "),
      Token(TASK_NAME, 60, 70, "Make tea  "),
      Token(COLON, 70, 71, ":"),
      Token(WHITE_SPACE, 71, 75, "    "),
      Token(TASK_DATA, 75, 79, "5   "),
      Token(COLON, 79, 80, ":"),
      Token(WHITE_SPACE, 80, 83, "   "),
      Token(TASK_DATA, 83, 85, "Me")
    )
    doTest(content, expected)
  }

  fun `test journey`() {
    val content = """
    journey
      title My working day
      section Go to work
         : Make tea: 5: Me
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(EOL, 7, 8, "\n"),
      Token(WHITE_SPACE, 8, 10, "  "),
      Token(TITLE, 10, 15, "title"),
      Token(WHITE_SPACE, 15, 16, " "),
      Token(TITLE_VALUE, 16, 30, "My working day"),
      Token(EOL, 30, 31, "\n"),
      Token(WHITE_SPACE, 31, 33, "  "),
      Token(SECTION, 33, 40, "section"),
      Token(WHITE_SPACE, 40, 41, " "),
      Token(SECTION_TITLE, 41, 51, "Go to work"),
      Token(EOL, 51, 52, "\n"),
      Token(WHITE_SPACE, 52, 57, "     "),
      Token(COLON, 57, 58, ":"),
      Token(WHITE_SPACE, 58, 59, " "),
      Token(TASK_DATA, 59, 67, "Make tea"),
      Token(COLON, 67, 68, ":"),
      Token(WHITE_SPACE, 68, 69, " "),
      Token(TASK_DATA, 69, 70, "5"),
      Token(COLON, 70, 71, ":"),
      Token(WHITE_SPACE, 71, 72, " "),
      Token(TASK_DATA, 72, 74, "Me")
    )
    doTest(content, expected)
  }

  fun `test journey with comments`() {
    val content = """
    journey %% This is comment 
      title My working day %% This is not comment
      section Go to work %% This is not comment
        Make tea: 5: Me %% This is not comment
        %% This is comment
    """.trimIndent()
    val expected = listOf(
      Token(Journey.JOURNEY, 0, 7, "journey"),
      Token(WHITE_SPACE, 7, 8, " "),
      Token(LINE_COMMENT, 8, 10, "%%"),
      Token(COMMENT_TEXT, 10, 27, " This is comment "),
      Token(EOL, 27, 28, "\n"),
      Token(WHITE_SPACE, 28, 30, "  "),
      Token(TITLE, 30, 35, "title"),
      Token(WHITE_SPACE, 35, 36, " "),
      Token(TITLE_VALUE, 36, 73, "My working day %% This is not comment"),
      Token(EOL, 73, 74, "\n"),
      Token(WHITE_SPACE, 74, 76, "  "),
      Token(SECTION, 76, 83, "section"),
      Token(WHITE_SPACE, 83, 84, " "),
      Token(SECTION_TITLE, 84, 117, "Go to work %% This is not comment"),
      Token(EOL, 117, 118, "\n"),
      Token(WHITE_SPACE, 118, 122, "    "),
      Token(TASK_NAME, 122, 130, "Make tea"),
      Token(COLON, 130, 131, ":"),
      Token(WHITE_SPACE, 131, 132, " "),
      Token(TASK_DATA, 132, 133, "5"),
      Token(COLON, 133, 134, ":"),
      Token(WHITE_SPACE, 134, 135, " "),
      Token(TASK_DATA, 135, 160, "Me %% This is not comment"),
      Token(EOL, 160, 161, "\n"),
      Token(WHITE_SPACE, 161, 165, "    "),
      Token(LINE_COMMENT, 165, 167, "%%"),
      Token(COMMENT_TEXT, 167, 183, " This is comment")
    )
    doTest(content, expected)
  }
}
