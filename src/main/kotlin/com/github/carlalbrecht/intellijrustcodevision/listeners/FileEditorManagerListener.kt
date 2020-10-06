package com.github.carlalbrecht.intellijrustcodevision.listeners

import com.github.carlalbrecht.intellijrustcodevision.Icons
import com.intellij.codeInsight.hints.HorizontalConstrainedPresentation
import com.intellij.codeInsight.hints.HorizontalConstraints
import com.intellij.codeInsight.hints.InlineInlayRenderer
import com.intellij.codeInsight.hints.presentation.AttributesTransformerPresentation
import com.intellij.codeInsight.hints.presentation.InlayPresentation
import com.intellij.codeInsight.hints.presentation.PresentationFactory
import com.intellij.codeInsight.hints.presentation.RecursivelyUpdatingRootPresentation
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import org.rust.lang.core.psi.RsEnumItem
import org.rust.lang.core.psi.RsFile
import org.rust.lang.core.psi.RsFunction
import org.rust.lang.core.psi.RsRecursiveVisitor
import org.rust.lang.core.psi.RsStructItem
import org.rust.lang.core.psi.RsTraitItem
import org.rust.lang.core.psi.RsTypeAlias
import java.awt.Color

internal class FileEditorManagerListener(val project: Project) : FileEditorManagerListener {

    companion object {
        const val INLAY_PRIORITY = 1000
        const val ICON_TEXT_SPACING = 4
        val FOREGROUND_COLOR = Color(0xA9, 0xA9, 0xA9)
    }

    data class Padding(val left: Int, val top: Int, val right: Int, val bottom: Int)

    override fun fileOpenedSync(
        source: FileEditorManager,
        file: VirtualFile,
        editors: Pair<Array<FileEditor>, Array<FileEditorProvider>>
    ) {
        println("FileEditor opened for ${file.name}")

        for (fileEditor in editors.first) {
            if (fileEditor is TextEditor) {
                println("Editor is a text editor")
                processEditor(fileEditor.editor)
            }

            println("Editor is not a text editor")
        }
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        println("FileEditor closed for ${file.name}")
    }

    private fun processEditor(editor: Editor) {
        val psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.document)

        if (psiFile !is RsFile) {
            println("PSI file isn't a Rust file :(")
            return
        }

        println("PSI file is a Rust file :)")

        val presentationFactory = PresentationFactory(editor as EditorImpl)

        psiFile.accept(
            object : RsRecursiveVisitor() {
                override fun visitFunction(function: RsFunction) {
                    super.visitFunction(function)

                    println("Found function: ${function.identifier.text}")
                    createInlay(editor, presentationFactory, function.textOffset)
                }

                override fun visitStructItem(struct: RsStructItem) {
                    super.visitStructItem(struct)

                    println("Found struct: ${struct.identifier?.text}")
                    createInlay(editor, presentationFactory, struct.textOffset)
                }

                override fun visitEnumItem(enum: RsEnumItem) {
                    super.visitEnumItem(enum)

                    println("Found enum: ${enum.identifier?.text}")
                    createInlay(editor, presentationFactory, enum.textOffset)
                }

                override fun visitTraitItem(trait: RsTraitItem) {
                    super.visitTraitItem(trait)

                    println("Found trait: ${trait.identifier?.text}")
                    createInlay(editor, presentationFactory, trait.textOffset)
                }

                override fun visitTypeAlias(typeAlias: RsTypeAlias) {
                    super.visitTypeAlias(typeAlias)

                    println("Found type alias: ${typeAlias.identifier.text}")
                    createInlay(editor, presentationFactory, typeAlias.textOffset)
                }
            }
        )
    }

    private fun createInlay(editor: Editor, presentationFactory: PresentationFactory, offset: Int) {
        editor.inlayModel.addBlockElement(
            offset,
            false,
            true,
            INLAY_PRIORITY,
            InlineInlayRenderer(
                listOf(
                    horizontalPresentation(
                        inset(
                            presentationFactory,
                            presentationFactory.icon(Icons.InsightReference),
                            Padding(0, 2, 0, 0)
                        )
                    ),
                    horizontalPresentation(
                        AttributesTransformerPresentation(
                            presentationFactory.withTooltip(
                                "This is a test tooltip :^)",
                                inset(
                                    presentationFactory,
                                    presentationFactory.smallText("42069 usages"),
                                    Padding(ICON_TEXT_SPACING, 2, 0, 0)
                                )
                            )
                        ) { attributes ->
                            val result = attributes.clone()
                            result.foregroundColor = FOREGROUND_COLOR
                            result
                        }
                    )
                )
            )
        )
    }

    private fun inset(
        presentationFactory: PresentationFactory,
        content: InlayPresentation,
        padding: Padding
    ): InlayPresentation {
        return presentationFactory.inset(
            content,
            padding.left,
            padding.right,
            padding.top,
            padding.bottom
        )
    }

    private fun horizontalPresentation(
        content: InlayPresentation
    ): HorizontalConstrainedPresentation<InlayPresentation> {
        return HorizontalConstrainedPresentation(
            RecursivelyUpdatingRootPresentation(content),
            HorizontalConstraints(INLAY_PRIORITY, false)
        )
    }

}
