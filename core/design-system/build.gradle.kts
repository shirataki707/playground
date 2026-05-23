plugins {
    id("playground.android.library.compose")
}

android {
    namespace = "jp.shirataki707.playground.core.designsystem"
}

dependencies {
    // material-icons-core is not part of the convention plugin — design-system specific
    implementation(libs.androidx.compose.material.icons.core)
}
