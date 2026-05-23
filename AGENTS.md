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

新モジュール追加時は、`app/build.gradle.kts` を参考に適切なプラグインを1行で適用する。

| 用途 | Plugin ID |
|---|---|
| Android アプリモジュール（Compose あり） | `playground.android.application.compose` |
| Android ライブラリ（Compose なし） | `playground.android.library` |
| Android ライブラリ（Compose あり） | `playground.android.library.compose` |

プラグインが設定する共通項目: `compileSdk=36`, `minSdk=24`, `targetSdk=36`, `jvmToolchain(17)`, ktlint, detekt。

**AGP 9.0 の制約**: `org.jetbrains.kotlin.android` プラグインを明示的に apply するとエラーになる。AGP 9.0 が Kotlin サポートを内蔵しているため不要。

## モジュール構成

```
:app              # アプリモジュール（唯一のエントリポイント）
:core             # （予定）共有ロジック・データ層
:feature:xr       # XR 関連 UI
:feature:<name>   # 各技術領域の機能モジュール
```

新モジュールを追加するときは `settings.gradle.kts` に `include(":feature:<name>")` を追加し、モジュールディレクトリに `build.gradle.kts` を作成する。

## コード規約

- detekt の設定: `config/detekt/detekt.yml`（`buildUponDefaultConfig = true`）
- ktlint の設定: `.editorconfig`（`max_line_length = 120`、Composable 関数の命名ルール無効化）
- Composable 関数名は PascalCase（detekt の `FunctionNaming.ignoreAnnotated: [Composable]` で許容）
- `UnusedParameter` は `@Suppress` で個別に抑制する
