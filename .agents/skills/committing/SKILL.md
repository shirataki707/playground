---
name: committing
description: 変更を論理単位ごとに適切な粒度でコミットします。「コミットして」「変更をコミットして」「git commitして」のようなときに使用します。ドキュメント更新は含みません（コミット後のフックが自動確認します）。
---

# コミット

変更を論理単位に分けてコミットする。

## 進捗チェックリスト

- [ ] Step 1: 差分の把握と粒度設計
- [ ] Step 2: 論理単位ごとにコミット

---

## Step 1: 差分の把握と粒度設計

```bash
git status
git diff
git diff --cached
git log --oneline -5
```

変更内容を以下の観点で分類する:

- **機能単位**: 新モジュール・新機能・バグ修正はそれぞれ独立したコミット
- **ビルド設定**: Convention Plugin・Gradle 設定変更はまとめる
- **ツール設定**: `.agents/`・`.claude/` 等の設定変更は別コミット
- **IDE 設定**: `.idea/` は関連する機能コミットに含める（または別コミット）

まだステージされていないファイルがある場合は `git add <files>` で適切に選択してからコミットする（`git add .` は全ファイルを追加するため使用に注意）。

---

## Step 2: 論理単位ごとにコミット

各コミットは以下の形式で作成する:

```bash
git add <関連ファイル>
git commit -m "$(cat <<'EOF'
<type>: <概要（英語・命令形）>

Co-Authored-By: Claude Sonnet 4.6 <noreply@anthropic.com>
EOF
)"
```

コミットメッセージの type:
- `feat` / `add`: 新機能・新モジュール追加
- `fix`: バグ修正
- `refactor`: 動作変更を伴わない構造変更
- `docs`: ドキュメントのみの変更
- `build`: ビルド設定・Convention Plugin
- `skill`: `.agents/skills/` の変更
