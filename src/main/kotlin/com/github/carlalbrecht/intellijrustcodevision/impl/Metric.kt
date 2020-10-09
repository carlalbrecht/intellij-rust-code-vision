package com.github.carlalbrecht.intellijrustcodevision.impl

/**
 * A _metric_, which can be shown in the code vision inlay above whatever source
 * item the metric applies to.
 *
 * A _metric_ is a specific data point, such as the number of references to the
 * current item, or the most recent VCS contributor to the source item.
 */
data class Metric(val text: String)
