plugins {
    id("playground.android.feature")
}

android {
    namespace = "jp.shirataki707.playground.feature.xr.ui"
}

dependencies {
    implementation(libs.androidx.xr.compose)
}
