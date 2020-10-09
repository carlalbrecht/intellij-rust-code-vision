package com.github.carlalbrecht.intellijrustcodevision.impl

/**
 * Marks a class as a [Metric] provider.
 *
 * Classes annotated with this annotation are also expected to implement
 * [MetricHandler].
 *
 * If [elements] is empty, then the provider will receive all element types.
 *
 * @param elements list of element types that the provider can handle.
 */
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class MetricProvider(vararg val elements: ElementType) {

    companion object {

        /**
         * Returns whether or not the supplied [provider] class can handle a
         * given [ElementType].
         *
         * Returns `true` if the [provider] has a [MetricProvider] annotation,
         * and either has an empty supported [elements] list (this means that it
         * supports everything), or said list contains [type].
         *
         * @param provider the metric provider to check
         * @param type the element type to check if the [provider] supports
         */
        fun providerSupportsElementType(
            provider: Class<*>,
            type: ElementType
        ): Boolean {
            val attr = provider.annotations.find { it is MetricProvider } as? MetricProvider

            return attr != null && (attr.elements.isEmpty() || attr.elements.contains(type))
        }

    }

}
