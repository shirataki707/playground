plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "playground.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "playground.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "playground.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "playground.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidFeatureContract") {
            id = "playground.android.feature.contract"
            implementationClass = "AndroidFeatureContractConventionPlugin"
        }
        register("detekt") {
            id = "playground.detekt"
            implementationClass = "DetektConventionPlugin"
        }
    }
}
