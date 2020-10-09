package com.github.carlalbrecht.intellijrustcodevision.listeners

import com.github.carlalbrecht.intellijrustcodevision.impl.MetricProvider
import com.github.carlalbrecht.intellijrustcodevision.services.MyProjectService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import org.reflections.Reflections

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.getService(MyProjectService::class.java)

        val reflections = Reflections("com.github.carlalbrecht.intellijrustcodevision")
        val annotated = reflections.getTypesAnnotatedWith(MetricProvider::class.java)

        annotated.forEach {
            println("MetricProvider: ${it.name}")
        }
    }

}
