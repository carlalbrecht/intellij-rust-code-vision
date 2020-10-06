package com.github.carlalbrecht.intellijrustcodevision.services

import com.intellij.openapi.project.Project
import com.github.carlalbrecht.intellijrustcodevision.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
