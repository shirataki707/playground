# Design System

`:core:design-system` モジュールが提供するテーマ・トークン・コンポーネントを使う。
`MaterialTheme` を直接参照せず、`PlaygroundTheme` 経由で統一的にアクセスする。

## テーマアクセス

```kotlin
// 色（セマンティックトークン）
PlaygroundTheme.appColors.text.primary
PlaygroundTheme.appColors.background.default
PlaygroundTheme.appColors.accent.primary     // ブランドカラー・インタラクティブ要素
PlaygroundTheme.appColors.accent.secondary
PlaygroundTheme.appColors.status.error

// スペーシング
PlaygroundTheme.space.medium   // 16dp
PlaygroundTheme.space.large    // 24dp

// タイポグラフィ・シェイプ（M3 を再エクスポート）
PlaygroundTheme.typography.titleLarge
PlaygroundTheme.shapes.medium
```

## スペーシングスケール

| トークン | 値   |
|--------|------|
| xxxs   | 2dp  |
| xxs    | 4dp  |
| xs     | 8dp  |
| small  | 12dp |
| medium | 16dp |
| large  | 24dp |
| xl     | 32dp |
| xxl    | 48.dp |

マジックナンバーの `dp` 値は使わない。必ず `PlaygroundTheme.space.*` を使う。

## 汎用コンポーネント

新しい画面やコンポーネントを作るときは、まず下記が使えないか確認する。

| 用途 | コンポーネント |
|------|--------------|
| 主要アクション | `PrimaryButton` |
| 副次アクション | `SecondaryButton` |
| 軽微アクション / リンク的操作 | `TertiaryButton` |
| カード型コンテナ（タップ可） | `PlaygroundCard(onClick = {...})` |
| カード型コンテナ（静的） | `PlaygroundCard()` |
| テキスト入力 | `PlaygroundTextField` |
| 画面上部バー | `PlaygroundTopAppBar` |

## Preview

- 各 Composable の Preview では必ず `PlaygroundTheme { ... }` でラップする
- ライト・ダーク両方の Preview を用意する（`uiMode = Configuration.UI_MODE_NIGHT_YES`）
- `dynamicColor = false` を指定してプレビューを固定色にする

```kotlin
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyComponentPreview() {
    PlaygroundTheme(dynamicColor = false) {
        MyComponent(...)
    }
}
```

## 禁止事項

- `MaterialTheme` / `PlaygroundTheme.colorScheme` を UI コード（feature モジュール）で直接参照しない（必ず `appColors.*` を使う）
- ハードコードした色値（`Color(0xFF...)` や `Color.Red` など）を使わない
- `16.dp` などマジックナンバーのスペーシングは使わない
