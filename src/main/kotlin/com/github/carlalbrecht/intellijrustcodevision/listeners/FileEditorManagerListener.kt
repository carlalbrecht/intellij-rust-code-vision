package com.github.carlalbrecht.intellijrustcodevision.listeners

import com.github.carlalbrecht.intellijrustcodevision.Icons
import com.intellij.codeInsight.hints.HorizontalConstrainedPresentation
import com.intellij.codeInsight.hints.HorizontalConstraints
import com.intellij.codeInsight.hints.InlineInlayRenderer
import com.intellij.codeInsight.hints.presentation.AttributesTransformerPresentation
import com.intellij.codeInsight.hints.presentation.InlayPresentation
import com.intellij.codeInsight.hints.presentation.PresentationFactory
import com.intellij.codeInsight.hints.presentation.RecursivelyUpdatingRootPresentation
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.intellij.openapi.vfs.VirtualFile
import org.rust.openapiext.toPsiFile
import java.awt.Color

internal class FileEditorManagerListener(val project: Project) : FileEditorManagerListener {

    companion object {
        const val INLAY_PRIORITY = 1000
        val FOREGROUND_COLOR = Color(0xA9, 0xA9, 0xA9)
    }

    override fun fileOpenedSync(
        source: FileEditorManager,
        file: VirtualFile,
        editors: Pair<Array<FileEditor>, Array<FileEditorProvider>>
    ) {
        println("FileEditor opened for ${file.name}")

        for (editor in editors.first) {
            if (editor is TextEditor) {
                println("Editor is a text editor")

                // TODO extract this inlay hint rendering code.
                val presentationFactory = PresentationFactory(editor.editor as EditorImpl)

                editor.editor.inlayModel.addBlockElement(
                    0,
                    false,
                    true,
                    INLAY_PRIORITY,
                    InlineInlayRenderer(
                        listOf(
                            horizontalPresentation(
                                presentationFactory.icon(Icons.InsightReference)
                            ),
                            horizontalPresentation(
                                AttributesTransformerPresentation(
                                    presentationFactory.withTooltip(
                                        "This is a test tooltip :^)",
                                        presentationFactory.smallText("69 usages")
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
        }

        val psiElement = file.toPsiFile(project)?.findElementAt(0)
        println(psiElement)
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        println("FileEditor closed for ${file.name}")
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
