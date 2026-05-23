---
name: creating-feature-module
description: Android Playground プロジェクトに新しい feature モジュール（contract + ui の2層構成）を追加します。NavKey・Screen・EntryProvider のテンプレコード生成と Navigation.kt へのワイヤリングまで一貫して実行します。「新しい feature を作って」「:feature:animation を追加して」「animationモジュールをセットアップして」のようなときに使用します。
argument-hint: "[feature-name]"
---

# Feature モジュール作成

`:feature:<name>:contract` + `:feature:<name>:ui` の2層構成で新 feature モジュールを作成する。

## 引数

- `feature-name`（任意）: 作成する feature の名前（小文字・ハイフン区切り）。例: `animation`

## 進捗チェックリスト

- [ ] Step 1: feature 名の確定
- [ ] Step 2: contract モジュール作成
- [ ] Step 3: ui モジュール作成
- [ ] Step 4: Navigation.kt へのワイヤリング
- [ ] Step 5: Gradle 検証

---

## Step 1: feature 名の確定

引数 `feature-name` が渡された場合はそれを使う（例: `animation` → `:feature:animation:contract` / `:feature:animation:ui`）。
渡されていない場合は `AskUserQuestion` でfeature名を確認する。

以降 `<name>` = feature名（小文字）、`<Name>` = PascalCase（例: `animation` → `Animation`）として扱う。

---

## Step 2: contract モジュール作成

```bash
mkdir -p feature/<name>/contract/src/main/kotlin/jp/shirataki707/playground/feature/<name>
```

### `feature/<name>/contract/build.gradle.kts`

```
plugins {
    id("playground.android.feature.contract")
}

android {
    namespace = "jp.shirataki707.playground.feature.<name>.contract"
}
```

### `<Name>Key.kt`

パス: `feature/<name>/contract/src/main/kotlin/jp/shirataki707/playground/feature/<name>/<Name>Key.kt`

```
package jp.shirataki707.playground.feature.<name>

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object <Name>Key : NavKey
```

---

## Step 3: ui モジュール作成

```bash
mkdir -p feature/<name>/ui/src/main/kotlin/jp/shirataki707/playground/feature/<name>
```

### `feature/<name>/ui/build.gradle.kts`

```
plugins {
    id("playground.android.feature")
}

android {
    namespace = "jp.shirataki707.playground.feature.<name>.ui"
}
```

### `<Name>Screen.kt`

パス: `feature/<name>/ui/src/main/kotlin/jp/shirataki707/playground/feature/<name>/<Name>Screen.kt`

public/internal の2層パターン:

```
package jp.shirataki707.playground.feature.<name>

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import jp.shirataki707.playground.core.navigation.Navigator

@Suppress("UnusedParameter")
@Composable
fun <Name>Screen(navigator: Navigator, modifier: Modifier = Modifier) {
    <Name>Screen(modifier = modifier)
}

@Composable
internal fun <Name>Screen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Text("<Name> Screen")
    }
}
```

### `<Name>EntryProvider.kt`

パス: `feature/<name>/ui/src/main/kotlin/jp/shirataki707/playground/feature/<name>/<Name>EntryProvider.kt`

```
package jp.shirataki707.playground.feature.<name>

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import jp.shirataki707.playground.core.navigation.Navigator

fun EntryProviderScope<NavKey>.<name>Entry(navigator: Navigator) {
    entry<<Name>Key> { <Name>Screen(navigator = navigator) }
}
```

---

## Step 4: Navigation.kt へのワイヤリング

`app/src/main/java/jp/shirataki707/playground/Navigation.kt` を編集する。

**import を追加:**

```
import jp.shirataki707.playground.feature.<name>.<name>Entry
```

**`navEntryProvider` ブロックに1行追加:**

```
val navEntryProvider =
    entryProvider {
        entry<Main> { ... }
        xrEntry(navigator)
        <name>Entry(navigator)  // ← 追加
    }
```

---

## Step 5: Gradle 検証

```bash
ANDROID_HOME=/mnt/c/Users/staka/AppData/Local/Android/Sdk ./gradlew projects
```

出力の `Project hierarchy` に `:feature:<name>:contract` と `:feature:<name>:ui` が両方表示されれば成功。

**`settings.gradle.kts` への追記・`.gitignore` の追加は不要。** `feature/` 配下は自動スキャンで検出される。
