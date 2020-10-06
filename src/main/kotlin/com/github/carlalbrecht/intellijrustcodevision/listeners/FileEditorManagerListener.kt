package com.github.carlalbrecht.intellijrustcodevision.listeners

import com.intellij.codeInsight.hints.HorizontalConstrainedPresentation
import com.intellij.codeInsight.hints.HorizontalConstraints
import com.intellij.codeInsight.hints.InlineInlayRenderer
import com.intellij.codeInsight.hints.presentation.PresentationFactory
import com.intellij.codeInsight.hints.presentation.RecursivelyUpdatingRootPresentation
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.intellij.openapi.vfs.VirtualFile
import org.rust.openapiext.toPsiFile

internal class FileEditorManagerListener(val project: Project) : FileEditorManagerListener {

    override fun fileOpenedSync(source: FileEditorManager, file: VirtualFile,
                                editors: Pair<Array<FileEditor>, Array<FileEditorProvider>>) {
        println("FileEditor opened for ${file.name}")

        for (editor in editors.first) {
            if (editor is TextEditor) {
                println("Editor is a text editor")

                val presentationFactory = PresentationFactory(editor.editor as EditorImpl)

                editor.editor.inlayModel.addBlockElement(
                        0,
                        false,
                        true,
                        1000,
                        InlineInlayRenderer(listOf(
                                HorizontalConstrainedPresentation(
                                        RecursivelyUpdatingRootPresentation(
                                                presentationFactory.text("Wew lad")),
                                        HorizontalConstraints(1, false))
                        )))
            }
        }

        val psiElement = file.toPsiFile(project)?.findElementAt(0)
        println(psiElement)
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        println("FileEditor closed for ${file.name}")
    }

}