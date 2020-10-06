package com.github.carlalbrecht.intellijrustcodevision.listeners

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiTreeChangeEvent
import com.intellij.psi.PsiTreeChangeListener

internal class PsiTreeChangeListener(val project: Project) : PsiTreeChangeListener {

    override fun beforeChildAddition(event: PsiTreeChangeEvent) { }
    override fun beforeChildRemoval(event: PsiTreeChangeEvent) { }
    override fun beforeChildReplacement(event: PsiTreeChangeEvent) { }
    override fun beforeChildMovement(event: PsiTreeChangeEvent) { }
    override fun beforeChildrenChange(event: PsiTreeChangeEvent) { }
    override fun beforePropertyChange(event: PsiTreeChangeEvent) { }

    override fun childAdded(event: PsiTreeChangeEvent) = handleGenericChangeEvent(event)
    override fun childRemoved(event: PsiTreeChangeEvent) = handleGenericChangeEvent(event)
    override fun childReplaced(event: PsiTreeChangeEvent) = handleGenericChangeEvent(event)
    override fun childrenChanged(event: PsiTreeChangeEvent) = handleGenericChangeEvent(event)
    override fun childMoved(event: PsiTreeChangeEvent) = handleGenericChangeEvent(event)
    override fun propertyChanged(event: PsiTreeChangeEvent) = handleGenericChangeEvent(event)

    fun handleGenericChangeEvent(event: PsiTreeChangeEvent) {
        println("Received PSI tree change event $event")
    }

}
