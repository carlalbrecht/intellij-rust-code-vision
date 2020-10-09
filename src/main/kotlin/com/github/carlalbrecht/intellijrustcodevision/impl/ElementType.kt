package com.github.carlalbrecht.intellijrustcodevision.impl

import com.intellij.psi.PsiElement
import org.rust.lang.core.psi.*

/**
 * The type of an [Element].
 *
 * This generally refers to the concrete type of the contained [PsiElement]
 * ([Element.element]), since we only use a subset of all PSI element types that
 * IntelliJ Rust defines (otherwise consumers would have to check all of those
 * types, instead of just a few simplified enum constants).
 */
enum class ElementType {
    /**
     * A function definition.
     *
     * @see RsFunction
     */
    FUNCTION,

    /**
     * A struct definition.
     *
     * @see RsStructItem
     */
    STRUCT,

    /**
     * An enum definition.
     *
     * @see RsEnumItem
     */
    ENUM,

    /**
     * A trait definition (note: this does **not** include trait
     * implementations).
     *
     * @see RsTraitItem
     */
    TRAIT,

    /**
     * A type alias (e.g. `type TypeAlias = OriginalType`)
     *
     * @see RsTypeAlias
     */
    TYPE_ALIAS
}
