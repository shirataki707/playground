---
name: creating-prs
description: コミット履歴と差分を解析してPRタイトル・説明文を自動生成し、ブランチをpushしてGitHub PRを作成します。「PRを作って」「プルリクエストを作って」「create PR」「PRを出して」のようなときに使用します。
---

# PR 作成

コミット履歴と差分を解析し、`.github/PULL_REQUEST_TEMPLATE.md` の構成に沿ったPRを作成する。

## 進捗チェックリスト

- [ ] Step 1: 変更の把握
- [ ] Step 2: PR タイトル・説明文の生成
- [ ] Step 3: push & PR 作成

---

## Step 1: 変更の把握

```bash
git status
git log --oneline main..HEAD
git diff main...HEAD --stat
```

ベースブランチは原則 `main`。`main` が存在しない場合は `master`。

---

## Step 2: PR タイトル・説明文の生成

`.github/PULL_REQUEST_TEMPLATE.md` を読み込み、その構成に沿って内容を埋める。

**タイトル（70文字以内）**
- コミットの主目的を英語・命令形で書く
- prefix は `feat:` / `fix:` / `refactor:` / `docs:` / `build:` / `skill:` のいずれか
- 例: `feat: add animation feature module`

**説明文の各セクション**

| セクション | 書き方 |
|---|---|
| Summary | 変更の目的を箇条書き1〜3点。コミットの「なぜ」を重視する |
| Changes | モジュール別に変更点を列挙する（例: `:feature:animation:ui` — 〇〇を追加） |
| Test Plan | checklist形式。ビルド確認・手動テスト・既存機能への影響を含める |

UIの変更が含まれる場合、Test PlanにScreenshotsセクションを追記するよう促す。

---

## Step 3: push & PR 作成

```bash
# ブランチを push（未追跡の場合は -u でトラッキング設定）
git push -u origin HEAD

# PR 作成（HEREDOC で本文を渡す）
gh pr create --title "<タイトル>" --body "$(cat <<'EOF'
<Step 2 で生成した説明文>

🤖 Generated with [Claude Code](https://claude.com/claude-code)
EOF
)"
```

作成後、PR の URL をユーザーに伝える。
