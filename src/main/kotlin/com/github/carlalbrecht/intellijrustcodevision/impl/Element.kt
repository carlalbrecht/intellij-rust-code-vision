package com.github.carlalbrecht.intellijrustcodevision.impl

import com.intellij.psi.PsiElement

/**
 * Wrapper class for PSI elements that we are interested in, which contains some
 * extra information about the element.
 */
data class Element(val type: ElementType, val element: PsiElement)
