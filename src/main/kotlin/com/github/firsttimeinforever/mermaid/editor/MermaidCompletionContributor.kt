package com.github.firsttimeinforever.mermaid.editor

import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidToken
import com.github.firsttimeinforever.mermaid.lang.lexer.MermaidTokens
import com.github.firsttimeinforever.mermaid.lang.parser.MermaidElements
import com.github.firsttimeinforever.mermaid.lang.psi.MermaidDirective
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PatternCondition
import com.intellij.patterns.PlatformPatterns.*
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext

class MermaidCompletionContributor : CompletionContributor() {

  init {
    extend(
      CompletionType.BASIC,
      not(psiElement().insideDiagram(psiElement().diagramHeader())),
      MermaidDiagramCompletionProvider()
    )

    extend(
      CompletionType.BASIC,
      psiElement().afterLeaf(
        or(
          psiElement(MermaidTokens.Flowchart.FLOWCHART),
          psiElement(MermaidTokens.DIRECTION)
        )
      ),
      DirectionCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidElements.FLOWCHART_HEADER)),
      FlowchartCompletionProvider()
    )

    extend(
      CompletionType.BASIC,
      psiElement().afterLeaf(
        or(
          psiElement(MermaidTokens.Pie.PIE),
          psiElement(MermaidTokens.EOL).afterLeaf(psiElement(MermaidTokens.Pie.PIE))
        )
      ),
      PieShowDataCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidElements.PIE_HEADER)),
      TitleCompletionProvider()
    )

    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidTokens.Sequence.SEQUENCE)),
      SequenceCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().insideBlock(psiElement(MermaidTokens.Sequence.ALT)),
      BranchCompletionProvider("else")
    )
    extend(
      CompletionType.BASIC,
      psiElement().insideBlock(psiElement(MermaidTokens.Sequence.PAR)),
      BranchCompletionProvider("and")
    )

    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidTokens.Journey.JOURNEY)),
      TitleCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidTokens.Journey.JOURNEY)),
      BranchCompletionProvider("section")
    )

    extend(
      CompletionType.BASIC,
      psiElement().afterSiblingSkipping(
        not(psiElement(MermaidTokens.ClassDiagram.CLASS_DIAGRAM)),
        psiElement(MermaidTokens.ClassDiagram.CLASS_DIAGRAM)
      ),
      ClassDiagramSimpleCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidTokens.ClassDiagram.CLASS_DIAGRAM)),
      ClassDiagramCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      and(
        psiElement().afterLeaf(psiElement(MermaidTokens.ANNOTATION_START)),
        psiElement().insideDiagram(psiElement(MermaidTokens.ClassDiagram.CLASS_DIAGRAM))
      ),
      ClassDiagramAnnotationCompletionProvider()
    )

    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidTokens.StateDiagram.STATE_DIAGRAM)),
      StateDiagramSimpleCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      and(
        psiElement().afterLeaf(psiElement(MermaidTokens.ANNOTATION_START)),
        psiElement().insideDiagram(psiElement(MermaidTokens.StateDiagram.STATE_DIAGRAM)),
      ),
      StateDiagramAnnotationCompletionProvider()
    )

    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidTokens.Gantt.GANTT)),
      TitleCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidTokens.Gantt.GANTT)),
      GanttSimpleCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().insideDiagram(psiElement(MermaidTokens.Gantt.GANTT)),
      BranchCompletionProvider("section")
    )

    extend(
      CompletionType.BASIC,
      psiElement().afterSiblingSkipping(
        not(psiElement(MermaidElements.REQUIREMENT_DOCUMENT)),
        psiElement(MermaidElements.REQUIREMENT_DOCUMENT)
      ),
      RequirementCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().isRequirementAttribute(MermaidTokens.Requirement.RISK),
      RequirementRiskCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().isRequirementAttribute(MermaidTokens.Requirement.VERIFY_METHOD),
      RequirementVerifyMethodCompletionProvider()
    )
    extend(
      CompletionType.BASIC,
      psiElement().afterSiblingSkippingElementsAndWhitespaces(
        or(
          psiElement(MermaidTokens.Requirement.ARROW_LEFT),
          psiElement(MermaidTokens.Requirement.REQ_LINE)
        ), MermaidTokens.ID
      ),
      RequirementRelationshipCompletionProvider()
    )
  }

  private fun PsiElementPattern.Capture<PsiElement>.insideBlock(pattern: ElementPattern<in PsiElement>): PsiElementPattern.Capture<PsiElement> {
    return with(object : PatternCondition<PsiElement>("insideBlock") {
      override fun accepts(psiElement: PsiElement, context: ProcessingContext): Boolean {
        val parent = psiElement.parent
        if (parent != null) {
          val children = parent.children
          var i = listOf(*children).indexOf(psiElement)
          while (--i >= 0) {
            if (psiElement(MermaidTokens.END).accepts(children[i], context)) {
              return false
            }
            if (pattern.accepts(children[i], context)) {
              return true
            }
          }
        }
        return false
      }
    })
  }

  private fun PsiElementPattern.Capture<PsiElement>.insideDiagram(pattern: ElementPattern<in PsiElement>): PsiElementPattern.Capture<PsiElement> {
    return with(object : PatternCondition<PsiElement>("insideDiagram") {
      override fun accepts(psiElement: PsiElement, context: ProcessingContext): Boolean {
        val file = psiElement.containingFile
        for (child in file.children) {
          if (whitespaceCommentEmptyErrorEolOrDirective().accepts(child, context)) {
            continue
          }
          return pattern.accepts(child, context)
        }
        return false
      }
    })
  }

  private fun PsiElementPattern.Capture<PsiElement>.whitespaceCommentEmptyErrorEolOrDirective(): PsiElementPattern.Capture<PsiElement> {
    return andOr(
      psiElement().whitespaceCommentEmptyOrError(),
      psiElement(MermaidTokens.EOL),
      psiElement(MermaidDirective::class.java)
    )
  }

  private fun PsiElementPattern.Capture<PsiElement>.diagramHeader(): PsiElementPattern.Capture<PsiElement> {
    return andOr(
      psiElement(MermaidElements.PIE_HEADER),
      psiElement(MermaidElements.FLOWCHART_HEADER),
      psiElement(MermaidTokens.Sequence.SEQUENCE),
      psiElement(MermaidTokens.ClassDiagram.CLASS_DIAGRAM),
      psiElement(MermaidTokens.Journey.JOURNEY),
      psiElement(MermaidTokens.StateDiagram.STATE_DIAGRAM),
      psiElement(MermaidTokens.EntityRelationship.ENTITY_RELATIONSHIP),
      psiElement(MermaidTokens.Gantt.GANTT),
      psiElement(MermaidTokens.Requirement.REQUIREMENT_DIAGRAM)
    )
  }

  private fun PsiElementPattern.Capture<PsiElement>.isRequirementAttribute(attribute: MermaidToken): PsiElementPattern.Capture<PsiElement> {
    return withParent(
      psiElement().afterSiblingSkipping(
        psiElement().whitespace(),
        psiElement(MermaidTokens.COLON).afterSiblingSkipping(
          psiElement().whitespace(),
          psiElement(attribute)
        )
      )
    )
  }

  private fun PsiElementPattern.Capture<PsiElement>.afterSiblingSkippingElementsAndWhitespaces(
    skip: ElementPattern<in PsiElement>,
    attribute: MermaidToken
  ): PsiElementPattern.Capture<PsiElement> {
    return withParent(
      psiElement().afterSiblingSkipping(
        psiElement().whitespace(),
        andOr(psiElement(MermaidTokens.COLON), skip).afterSiblingSkipping(
          psiElement().whitespace(),
          psiElement(attribute)
        )
      )
    )
  }
}
