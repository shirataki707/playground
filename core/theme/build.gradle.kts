plugins {
    id("playground.android.library.compose")
}

android {
    namespace = "jp.shirataki707.playground.core.theme"
}

dependencies {
    implementation(libs.androidx.compose.material.icons.core)
}
