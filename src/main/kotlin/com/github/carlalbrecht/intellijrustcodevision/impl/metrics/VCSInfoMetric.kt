package com.github.carlalbrecht.intellijrustcodevision.impl.metrics

import com.github.carlalbrecht.intellijrustcodevision.impl.Element
import com.github.carlalbrecht.intellijrustcodevision.impl.ElementType.*
import com.github.carlalbrecht.intellijrustcodevision.impl.Metric
import com.github.carlalbrecht.intellijrustcodevision.impl.MetricHandler
import com.github.carlalbrecht.intellijrustcodevision.impl.MetricProvider

@MetricProvider(FUNCTION, STRUCT, TRAIT, ENUM)
class VCSInfoMetric : MetricHandler {

    override fun handle(element: Element): Metric {
        TODO("Not yet implemented")
    }

}
