// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.detekt)
  alias(libs.plugins.ktlint.gradle) apply false
}

detekt {
  config.setFrom(files("config/detekt/detekt.yml"))
  buildUponDefaultConfig = true
  allRules = false
  source.setFrom(
    "app/src/main/kotlin",
    "app/src/main/java",
    "app/src/test/kotlin",
    "app/src/test/java",
  )
}