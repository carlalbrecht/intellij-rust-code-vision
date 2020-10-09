package com.github.carlalbrecht.intellijrustcodevision.impl

interface MetricHandler {

    fun handle(element: Element): Metric

}
