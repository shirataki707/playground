import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("playground.android.library.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("androidx-navigation3-runtime").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-navigation3").get())
                add("implementation", libs.findLibrary("androidx-compose-material-icons-core").get())
                add("implementation", project(":core:navigation"))
                add("implementation", project(":core:theme"))
                target.rootProject.allprojects
                    .filter { it.path.startsWith(":feature:") && it.path.split(":").contains("contract") && it.buildFile.exists() }
                    .forEach { add("implementation", it) }
            }
        }
    }
}
