package com.martin.commonplugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project


class CommonDependencies : Plugin<Project> {
    override fun apply(target: Project) {
     target.dependencies{
         add("implementation", project(":core"))
         add("ksp",target.versionCatalog().findLibrary("hilt-android-compiler").get())
         add("implementation", target.versionCatalog().findLibrary("hilt-android").get())
         add("implementation",target.versionCatalog().findLibrary("androidx-runtime-livedata").get())
         add("implementation",target.versionCatalog().findLibrary("androidx-navigation-compose").get())
         add("implementation",target.versionCatalog().findLibrary("androidx-hilt-navigation-compose").get())
         add("implementation",target.versionCatalog().findLibrary("androidx-lifecycle-viewmodel-compose").get())
         add("implementation",target.versionCatalog().findLibrary("androidx-lifecycle-livedata-ktx").get())
         add("implementation",target.versionCatalog().findLibrary("coil-compose").get())
         add("testImplementation", target.versionCatalog().findLibrary("junit").get())
         add("androidTestImplementation", target.versionCatalog().findLibrary("androidx-junit").get())
         add("androidTestImplementation", target.versionCatalog().findLibrary("androidx-espresso-core").get())
     }
    }
}
