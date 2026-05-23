---
name: updating-docs
description: git add . を実行して差分を確認し、適切な粒度でコミットした後に README.md と AGENTS.md をコードベースの現状に合わせて最新化します。「ドキュメントを更新して」「README を最新化して」「コミットしてドキュメントも直して」のようなときに使用します。
---

# ドキュメント最新化

差分のコミットとドキュメント（README.md・AGENTS.md）の最新化を一括で行う。

## 進捗チェックリスト

- [ ] Step 1: 差分の把握と粒度設計
- [ ] Step 2: 論理単位ごとにコミット
- [ ] Step 3: README.md を現状に合わせて更新
- [ ] Step 4: AGENTS.md を現状に合わせて更新
- [ ] Step 5: ドキュメント変更をコミット

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

---

## Step 3: README.md を現状に合わせて更新

`README.md` に記載されている以下の情報を現在のコードベースと照合し、古い記述を修正する:

- **プロジェクト構成のディレクトリツリー**: 実際のディレクトリ構造と一致しているか
- **モジュール構成**: `:app`・`:core:*`・`:feature:*:contract`・`:feature:*:ui` が正しく列挙されているか
- **Convention Plugins 一覧**: 使用可能な Plugin ID が全て記載されているか

確認コマンド:

```bash
find . -name "build.gradle.kts" -not -path "*/build/*" | sort
ANDROID_HOME=/mnt/c/Users/staka/AppData/Local/Android/Sdk ./gradlew projects 2>/dev/null | grep "Project '"
```

---

## Step 4: AGENTS.md を現状に合わせて更新

`AGENTS.md` に記載されている以下の情報を現在のコードベースと照合し、古い記述を修正する:

- **Convention Plugins 表**: 全 Plugin ID が正しいか
- **モジュール構成**: 実際のモジュール一覧と一致しているか
- **新モジュール追加手順**: 現在の自動スキャン仕様（`settings.gradle.kts` 追記不要など）と一致しているか

---

## Step 5: ドキュメント変更をコミット

```bash
git add README.md AGENTS.md
git commit -m "$(cat <<'EOF'
docs: update README and AGENTS.md for current codebase state

Co-Authored-By: Claude Sonnet 4.6 <noreply@anthropic.com>
EOF
)"
```

変更がない場合はコミットしない。
