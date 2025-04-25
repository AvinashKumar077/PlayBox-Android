plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.android.tools.build:gradle:${libs.versions.agp.get()}")
}
gradlePlugin{
    plugins{
        register("base-common-dependencies") {
            id = "base-common-dependencies"
            implementationClass = "com.martin.commonplugins.CommonDependencies"
        }
    }
}
