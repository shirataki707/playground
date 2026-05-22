# Android Playground

最新技術や気になった技術を試すためのPlaygroundアプリです。

## ビルドとリント

```bash
# リント（WSL では ANDROID_HOME が必要）
ANDROID_HOME=/mnt/c/Users/staka/AppData/Local/Android/Sdk ./gradlew detekt
ANDROID_HOME=... ./gradlew :app:ktlintCheck
ANDROID_HOME=... ./gradlew :app:ktlintFormat   # 自動修正

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

プラグインが設定する共通項目: `compileSdk=36`, `minSdk=24`, `targetSdk=36`, `jvmToolchain(17)`, ktlint。

**AGP 9.0 の制約**: `org.jetbrains.kotlin.android` プラグインを明示的に apply するとエラーになる。AGP 9.0 が Kotlin サポートを内蔵しているため不要。

## モジュール構成

```
:app                  # アプリモジュール（唯一のエントリポイント）
:module:core          # （予定）共有ロジック・データ層
:module:feature:*     # （予定）機能モジュール
```

## コード規約

- detekt の設定: `config/detekt/detekt.yml`（`buildUponDefaultConfig = true`）
- ktlint の設定: `.editorconfig`（`max_line_length = 120`、Composable 関数の命名ルール無効化）
- Composable 関数名は PascalCase（detekt の `FunctionNaming.ignoreAnnotated: [Composable]` で許容）
- `UnusedParameter` は `@Suppress` で個別に抑制する
