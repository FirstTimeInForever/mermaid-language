package com.github.firsttimeinforever.mermaid.lang.parser

class FlowchartTest : MermaidParserTestCase() {
  fun `test simple flowchart`() {
    val content = """
      flowchart LR
        no-de1[name1] -- text --> node2 -->|text| node3[123]
    """.trimIndent()
    val expectedTree = """
    Element(FILE)
    >Element(FLOWCHART_HEADER)
    >>PsiElement(Flowchart.FLOWCHART)
    >>PsiWhiteSpace
    >>PsiElement(DIR)
    >Element(FLOWCHART_DOCUMENT)
    >>Element(FLOWCHART_LINE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(VERTEX_STATEMENT)
    >>>>Element(NODE_STATEMENT)
    >>>>>Element(VERTEX)
    >>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>PsiElement(ID)
    >>>>>>>PsiElement(ID)
    >>>>>>>PsiElement(ID)
    >>>>>>Element(VERTEX_TEXT)
    >>>>>>>PsiElement(OPEN_SQUARE)
    >>>>>>>PsiElement(ALIAS)
    >>>>>>>PsiElement(CLOSE_SQUARE)
    >>>>PsiWhiteSpace
    >>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>PsiElement(Flowchart.START_ARROW)
    >>>>>PsiElement(Flowchart.LINK_TEXT)
    >>>>>PsiElement(ARROW)
    >>>>PsiWhiteSpace
    >>>>Element(VERTEX_STATEMENT)
    >>>>>Element(NODE_STATEMENT)
    >>>>>>Element(VERTEX)
    >>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>PsiElement(ID)
    >>>>>PsiWhiteSpace
    >>>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>>PsiElement(ARROW)
    >>>>>>PsiElement(Flowchart.SEP)
    >>>>>>PsiElement(Flowchart.LINK_TEXT)
    >>>>>>PsiElement(Flowchart.SEP)
    >>>>>PsiWhiteSpace
    >>>>>Element(VERTEX_STATEMENT)
    >>>>>>Element(NODE_STATEMENT)
    >>>>>>>Element(VERTEX)
    >>>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>>PsiElement(ID)
    >>>>>>>>Element(VERTEX_TEXT)
    >>>>>>>>>PsiElement(OPEN_SQUARE)
    >>>>>>>>>PsiElement(ALIAS)
    >>>>>>>>>PsiElement(CLOSE_SQUARE)
    """.trimIndent()
    doTest(content, expectedTree)
  }

  fun `test flowchart with subgraphs`() {
    val content = """
      flowchart TB
        c1-->a2
        subgraph one; a1-->a2;end;
        subgraph two
          b1-->b2
        end
        subgraph three
          c1-->c2
        end
    """.trimIndent()
    val expectedTree = """
    Element(FILE)
    >Element(FLOWCHART_HEADER)
    >>PsiElement(Flowchart.FLOWCHART)
    >>PsiWhiteSpace
    >>PsiElement(DIR)
    >Element(FLOWCHART_DOCUMENT)
    >>Element(FLOWCHART_LINE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(VERTEX_STATEMENT)
    >>>>Element(NODE_STATEMENT)
    >>>>>Element(VERTEX)
    >>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>PsiElement(ID)
    >>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>PsiElement(ARROW)
    >>>>Element(VERTEX_STATEMENT)
    >>>>>Element(NODE_STATEMENT)
    >>>>>>Element(VERTEX)
    >>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>PsiElement(ID)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(SUBGRAPH_STATEMENT)
    >>>>PsiElement(SUBGRAPH)
    >>>>PsiWhiteSpace
    >>>>Element(COMPLEX_IDENTIFIER)
    >>>>>PsiElement(ID)
    >>>>PsiElement(SEMICOLON)
    >>>>PsiWhiteSpace
    >>>>Element(FLOWCHART_DOCUMENT)
    >>>>>Element(FLOWCHART_LINE)
    >>>>>>Element(VERTEX_STATEMENT)
    >>>>>>>Element(NODE_STATEMENT)
    >>>>>>>>Element(VERTEX)
    >>>>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>>>PsiElement(ID)
    >>>>>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>>>>PsiElement(ARROW)
    >>>>>>>Element(VERTEX_STATEMENT)
    >>>>>>>>Element(NODE_STATEMENT)
    >>>>>>>>>Element(VERTEX)
    >>>>>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>>>>PsiElement(ID)
    >>>>>>PsiElement(SEMICOLON)
    >>>>PsiElement(END)
    >>>PsiElement(SEMICOLON)
    >>Element(FLOWCHART_LINE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(SUBGRAPH_STATEMENT)
    >>>>PsiElement(SUBGRAPH)
    >>>>PsiWhiteSpace
    >>>>Element(COMPLEX_IDENTIFIER)
    >>>>>PsiElement(ID)
    >>>>PsiElement(EOL)
    >>>>PsiWhiteSpace
    >>>>Element(FLOWCHART_DOCUMENT)
    >>>>>Element(FLOWCHART_LINE)
    >>>>>>Element(VERTEX_STATEMENT)
    >>>>>>>Element(NODE_STATEMENT)
    >>>>>>>>Element(VERTEX)
    >>>>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>>>PsiElement(ID)
    >>>>>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>>>>PsiElement(ARROW)
    >>>>>>>Element(VERTEX_STATEMENT)
    >>>>>>>>Element(NODE_STATEMENT)
    >>>>>>>>>Element(VERTEX)
    >>>>>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>>>>PsiElement(ID)
    >>>>>>PsiElement(EOL)
    >>>>PsiWhiteSpace
    >>>>PsiElement(END)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(SUBGRAPH_STATEMENT)
    >>>>PsiElement(SUBGRAPH)
    >>>>PsiWhiteSpace
    >>>>Element(COMPLEX_IDENTIFIER)
    >>>>>PsiElement(ID)
    >>>>PsiElement(EOL)
    >>>>PsiWhiteSpace
    >>>>Element(FLOWCHART_DOCUMENT)
    >>>>>Element(FLOWCHART_LINE)
    >>>>>>Element(VERTEX_STATEMENT)
    >>>>>>>Element(NODE_STATEMENT)
    >>>>>>>>Element(VERTEX)
    >>>>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>>>PsiElement(ID)
    >>>>>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>>>>PsiElement(ARROW)
    >>>>>>>Element(VERTEX_STATEMENT)
    >>>>>>>>Element(NODE_STATEMENT)
    >>>>>>>>>Element(VERTEX)
    >>>>>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>>>>PsiElement(ID)
    >>>>>>PsiElement(EOL)
    >>>>PsiWhiteSpace
    >>>>PsiElement(END)
    """.trimIndent()
    doTest(content, expectedTree)
  }

  fun `test flowchart with styles`() {
    val content = """
      flowchart LR
        id1[Start]-->id2[Stop]
        
        style id1 fill:#f9f,stroke:#333,stroke-width:4px
        style id2 fill:#bbf,stroke:#f66,stroke-width:2px,color:#fff,stroke-dasharray: 5 5
        
        A:::someclass --> B
        linkStyle 0,1 stroke:#ff3,stroke-width:4px,color:red;
        C & D --> E
        classDef someclass fill:#f96;
        class B,C someclass
    """.trimIndent()
    val expectedTree = """
    Element(FILE)
    >Element(FLOWCHART_HEADER)
    >>PsiElement(Flowchart.FLOWCHART)
    >>PsiWhiteSpace
    >>PsiElement(DIR)
    >Element(FLOWCHART_DOCUMENT)
    >>Element(FLOWCHART_LINE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(VERTEX_STATEMENT)
    >>>>Element(NODE_STATEMENT)
    >>>>>Element(VERTEX)
    >>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>PsiElement(ID)
    >>>>>>Element(VERTEX_TEXT)
    >>>>>>>PsiElement(OPEN_SQUARE)
    >>>>>>>PsiElement(ALIAS)
    >>>>>>>PsiElement(CLOSE_SQUARE)
    >>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>PsiElement(ARROW)
    >>>>Element(VERTEX_STATEMENT)
    >>>>>Element(NODE_STATEMENT)
    >>>>>>Element(VERTEX)
    >>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>PsiElement(ID)
    >>>>>>>Element(VERTEX_TEXT)
    >>>>>>>>PsiElement(OPEN_SQUARE)
    >>>>>>>>PsiElement(ALIAS)
    >>>>>>>>PsiElement(CLOSE_SQUARE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(STYLE_STATEMENT)
    >>>>PsiElement(STYLE)
    >>>>PsiWhiteSpace
    >>>>PsiElement(STYLE_TARGET)
    >>>>PsiWhiteSpace
    >>>>Element(STYLE_OPTIONS)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>>>PsiElement(COMMA)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>>>PsiElement(COMMA)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(STYLE_STATEMENT)
    >>>>PsiElement(STYLE)
    >>>>PsiWhiteSpace
    >>>>PsiElement(STYLE_TARGET)
    >>>>PsiWhiteSpace
    >>>>Element(STYLE_OPTIONS)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>>>PsiElement(COMMA)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>>>PsiElement(COMMA)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>>>PsiElement(COMMA)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>>>PsiElement(COMMA)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiWhiteSpace
    >>>>>PsiElement(STYLE_VAL)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(VERTEX_STATEMENT)
    >>>>Element(NODE_STATEMENT)
    >>>>>Element(VERTEX)
    >>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>PsiElement(ID)
    >>>>>PsiElement(STYLE_SEPARATOR)
    >>>>>PsiElement(STYLE_TARGET)
    >>>>PsiWhiteSpace
    >>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>PsiElement(ARROW)
    >>>>PsiWhiteSpace
    >>>>Element(VERTEX_STATEMENT)
    >>>>>Element(NODE_STATEMENT)
    >>>>>>Element(VERTEX)
    >>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>PsiElement(ID)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(LINK_STYLE_STATEMENT)
    >>>>PsiElement(LINK_STYLE)
    >>>>PsiWhiteSpace
    >>>>PsiElement(STYLE_TARGET)
    >>>>PsiElement(COMMA)
    >>>>PsiElement(STYLE_TARGET)
    >>>>PsiWhiteSpace
    >>>>Element(STYLE_OPTIONS)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>>>PsiElement(COMMA)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>>>PsiElement(COMMA)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>PsiElement(SEMICOLON)
    >>Element(FLOWCHART_LINE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(VERTEX_STATEMENT)
    >>>>Element(NODE_STATEMENT)
    >>>>>Element(VERTEX)
    >>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>PsiElement(ID)
    >>>>>PsiElement(AMPERSAND)
    >>>>>Element(NODE_STATEMENT)
    >>>>>>Element(VERTEX)
    >>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>PsiElement(ID)
    >>>>PsiWhiteSpace
    >>>>Element(FLOWCHART_LINK_STATEMENT)
    >>>>>PsiElement(ARROW)
    >>>>PsiWhiteSpace
    >>>>Element(VERTEX_STATEMENT)
    >>>>>Element(NODE_STATEMENT)
    >>>>>>Element(VERTEX)
    >>>>>>>Element(COMPLEX_IDENTIFIER)
    >>>>>>>>PsiElement(ID)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(CLASS_DEF_STATEMENT)
    >>>>PsiElement(CLASS_DEF)
    >>>>PsiWhiteSpace
    >>>>PsiElement(STYLE_TARGET)
    >>>>PsiWhiteSpace
    >>>>Element(STYLE_OPTIONS)
    >>>>>PsiElement(STYLE_OPT)
    >>>>>PsiElement(COLON)
    >>>>>PsiElement(STYLE_VAL)
    >>>PsiElement(SEMICOLON)
    >>Element(FLOWCHART_LINE)
    >>>PsiElement(EOL)
    >>PsiWhiteSpace
    >>Element(FLOWCHART_LINE)
    >>>Element(FLOWCHART_CLASS_STATEMENT)
    >>>>PsiElement(CLASS)
    >>>>PsiWhiteSpace
    >>>>PsiElement(ID)
    >>>>PsiElement(COMMA)
    >>>>PsiElement(ID)
    >>>>PsiWhiteSpace
    >>>>PsiElement(STYLE_TARGET)
    """.trimIndent()
    doTest(content, expectedTree)
  }
}
