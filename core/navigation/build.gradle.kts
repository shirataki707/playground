plugins {
    id("playground.android.library.compose")
}

android {
    namespace = "jp.shirataki707.playground.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
}
