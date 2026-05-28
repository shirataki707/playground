# Android Playground

最新技術や気になった技術を試すためのPlaygroundアプリです。

## ビルドとリント

```bash
# リント（WSL では ANDROID_HOME が必要）
ANDROID_HOME=/mnt/c/Users/staka/AppData/Local/Android/Sdk ./gradlew detekt
ANDROID_HOME=... ./gradlew ktlintCheck     # 全モジュール
ANDROID_HOME=... ./gradlew ktlintFormat    # 全モジュール自動修正

# アプリビルド（Windows の Android Studio から実行推奨）
./gradlew :app:assembleDebug
```

Stop hook が `.kt` ファイルの変更を検知して ktlint + detekt を自動実行する（`~/.local/bin/ktlint` と `~/.local/lib/detekt-cli.jar` を初回自動ダウンロード）。

## Convention Plugins（build-logic）

新モジュール追加時は適切なプラグインを1行で適用する。

| 用途 | Plugin ID |
|---|---|
| Android アプリモジュール（Compose あり） | `playground.android.application.compose` |
| Android ライブラリ（Compose なし） | `playground.android.library` |
| Android ライブラリ（Compose あり） | `playground.android.library.compose` |
| Feature モジュール（UI層・Compose あり） | `playground.android.feature` |
| Feature モジュール（契約層・NavKey のみ） | `playground.android.feature.contract` |

プラグインが設定する共通項目: `compileSdk=36`, `minSdk=24`, `targetSdk=36`, `jvmToolchain(17)`, ktlint, detekt。

- `playground.android.feature` は Compose 依存・Navigation3・`:core:navigation` を自動追加し、パスに `contract` セグメントを含む全 feature モジュールを依存に自動注入する
- `playground.android.feature.contract` は Navigation3-runtime + Kotlin Serialization のみ追加する

**AGP 9.0 の制約**: `org.jetbrains.kotlin.android` プラグインを明示的に apply するとエラーになる。AGP 9.0 が Kotlin サポートを内蔵しているため不要。

## モジュール構成

```
:app                             # アプリモジュール（唯一のエントリポイント）
:core:navigation                 # NavigationState / Navigator
:feature:xr:contract:main        # XrKey（NavKey）
:feature:xr:ui:main              # XrScreen / xrEntry
:feature:<name>:contract:<variant>   # 各 feature の NavKey
:feature:<name>:ui:<variant>         # 各 feature の Screen / EntryProvider
```

`feature/` 配下のスキャン規則:
- `feature/<name>/<type>/build.gradle.kts` が存在 → `:feature:<name>:<type>`（2階層）
- `feature/<name>/<type>/<variant>/build.gradle.kts` が存在 → `:feature:<name>:<type>:<variant>`（3階層）

新モジュールを追加するときは `feature/<name>/contract/<variant>/` と `feature/<name>/ui/<variant>/` にそれぞれ `build.gradle.kts` を作成する。**`settings.gradle.kts` への追記は不要**（`feature/` 配下を自動スキャン）。その後 `app/src/main/java/.../Navigation.kt` の `navEntryProvider` ブロックに `<name>Entry(navigator)` を追加する。

## コード規約

- detekt の設定: `config/detekt/detekt.yml`（`buildUponDefaultConfig = true`）
- ktlint の設定: `.editorconfig`（`max_line_length = 120`、Composable 関数の命名ルール無効化）
- Composable 関数名は PascalCase（detekt の `FunctionNaming.ignoreAnnotated: [Composable]` で許容）
- `UnusedParameter` は `@Suppress` で個別に抑制する
