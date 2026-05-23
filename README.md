# Android Playground

最新技術や気になった技術を試す Android アプリ。

## Tech Stack

- **Kotlin** 2.3 / **AGP** 9.0
- **Jetpack Compose** + Material3
- **Navigation3**（androidx.navigation3）
- **Kotlin Serialization**

## プロジェクト構成

```
playground/
├── build-logic/          # Convention Plugins（Gradle ビルドロジック集約）
│   └── convention/
│       └── src/main/kotlin/
│           ├── AndroidApplicationComposeConventionPlugin.kt
│           ├── AndroidLibraryConventionPlugin.kt
│           ├── AndroidLibraryComposeConventionPlugin.kt
│           ├── AndroidFeatureConventionPlugin.kt
│           ├── AndroidFeatureContractConventionPlugin.kt
│           └── DetektConventionPlugin.kt
├── app/                  # アプリモジュール
├── core/
│   └── navigation/       # NavigationState / Navigator
├── feature/
│   └── xr/
│       ├── contract/     # XrKey（NavKey）
│       └── ui/           # XrScreen / xrEntry
├── config/detekt/        # detekt 設定
└── gradle/
    └── libs.versions.toml  # バージョンカタログ
```

### モジュール構成

```
:app                        # アプリモジュール（唯一のエントリポイント）
:core:navigation            # NavigationState / Navigator
:feature:xr:contract        # XrKey（NavKey）
:feature:xr:ui              # XrScreen / xrEntry
:feature:<name>:contract    # 各 feature の NavKey
:feature:<name>:ui          # 各 feature の Screen / EntryProvider
```

## Convention Plugins

新しいモジュールを追加するときは `build.gradle.kts` に1行書くだけで、SDK バージョン・Kotlin・ktlint・detekt の基本設定が揃う。

```kotlin
// Android アプリ（Compose あり）
plugins { id("playground.android.application.compose") }

// Android ライブラリ（Compose なし / あり）
plugins { id("playground.android.library") }
plugins { id("playground.android.library.compose") }

// Feature モジュール（UI層）: Compose・Navigation3・:core:navigation・全 contract を自動追加
plugins { id("playground.android.feature") }

// Feature モジュール（契約層）: NavKey 定義のみ。Navigation3-runtime + Serialization を追加
plugins { id("playground.android.feature.contract") }
```

## Code Quality

| ツール | 用途 | 設定ファイル |
|---|---|---|
| **detekt** | 静的解析・コードスメル検出 | `config/detekt/detekt.yml` |
| **ktlint** | フォーマット統一 | `.editorconfig` |

```bash
./gradlew detekt       # 全モジュール静的解析
./gradlew ktlintCheck  # 全モジュールフォーマットチェック
./gradlew ktlintFormat # 全モジュール自動フォーマット
```

Claude の Stop hook により、コーディング終了時に変更した `.kt` ファイルへ ktlint + detekt が自動実行される（約2秒）。
