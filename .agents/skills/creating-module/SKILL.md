---
name: creating-module
description: Android Playground プロジェクトに新しいモジュールを追加します。ディレクトリ作成・build.gradle.kts 生成・Gradle 検証まで一貫して実行します。「新しいモジュールを作って」「:feature:animation を追加して」「xr モジュールをセットアップして」のようなときに使用します。
argument-hint: "[module-path]"
---

# モジュール作成

Android Playground の規約に沿って新モジュールを作成する。

## 進捗チェックリスト

- [ ] Step 1: モジュール情報の確定
- [ ] Step 2: ファイル生成
- [ ] Step 3: Gradle 検証

---

## Step 1: モジュール情報の確定

引数 `module-path` が渡された場合はそれをモジュール名として使用する（例: `feature animation` → `:feature:animation`）。
渡されていない場合は `AskUserQuestion` でモジュールの情報を確認する。

**確認事項:**

1. **パス**: 現在サポートする形式は `:feature:<name>` のみ
2. **Convention Plugin**: 下表を参考に確定する。playground のほぼすべての feature モジュールは Compose ありを使う

| 用途 | Plugin ID |
|---|---|
| Compose あり（feature モジュール標準） | `playground.android.library.compose` |
| Compose なし | `playground.android.library` |

**namespace の決定規則:**
- `:feature:<name>` → `jp.shirataki707.playground.feature.<name>`

---

## Step 2: ファイル生成

`:feature:<name>` の場合、リポジトリルートの `feature/<name>/` に以下を作成する。

### ディレクトリ構造

```
feature/<name>/
├── build.gradle.kts
└── src/
    ├── main/
    │   └── kotlin/jp/shirataki707/playground/feature/<name>/
    └── test/
        └── kotlin/jp/shirataki707/playground/feature/<name>/
```

```bash
mkdir -p feature/<name>/src/main/kotlin/jp/shirataki707/playground/feature/<name>
mkdir -p feature/<name>/src/test/kotlin/jp/shirataki707/playground/feature/<name>
```

### build.gradle.kts

```kotlin
plugins {
    id("playground.android.library.compose")
}

android {
    namespace = "jp.shirataki707.playground.feature.<name>"
}
```

**`settings.gradle.kts` への追記は不要。** `feature/` 配下は自動スキャンで検出される。

**`.gitignore` の追加も不要。** ルートの `.gitignore` が `build/` を全階層でカバーしている。

---

## Step 3: Gradle 検証

```bash
ANDROID_HOME=/mnt/c/Users/staka/AppData/Local/Android/Sdk ./gradlew projects
```

出力の `Project hierarchy` に `:feature:<name>` が表示されれば成功。
表示されない場合は `feature/<name>/build.gradle.kts` が正しく配置されているか確認する。
