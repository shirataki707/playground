---
name: managing-adrs
description: >-
  docs/adr/ 配下の ADR（アーキテクチャ決定記録）を作成・更新・検索します。
  「ADR を作って」「Jetpack Compose の採用を記録して」「アーキテクチャ決定を残して」のようなときに create を使います。
  「ADR 0003 を置き換えて」「このADRを Superseded にして」のようなときに update を使います。
  「過去の技術選定を確認して」「Navigation3 を選んだ理由を調べて」のようなときに search を使います。
  ADR ファイルはすべて英語で記述し、ユーザーとの会話は日本語で行います。
argument-hint: "[create|update|search] [adr-title-or-number]"
---

# ADR 管理

`docs/adr/` 配下の ADR（Architecture Decision Record）を作成・更新・検索する。
ADR ファイルの記述はすべて英語。ユーザーとのやり取りは日本語。

## 引数

- `action`（任意）: `create` / `update` / `search` のいずれか。省略時は会話から判断する。
- `adr-title-or-number`（任意）: 対象 ADR のタイトルまたは番号（例: `0003`）。

## 進捗チェックリスト

- [ ] Step 0: アクションの確定
- [ ] Step 1（create）: ADR 新規作成
- [ ] Step 2（update）: ADR ステータス更新
- [ ] Step 3（search）: ADR 検索・回答

---

## Step 0: アクションの確定

会話・引数から `create` / `update` / `search` のいずれかを判断する。

| アクション | 判断の目安 |
|---|---|
| `create` | 新しい技術的決定を記録したい、ADR を新規作成したい |
| `update` | 既存 ADR のステータスを変更したい、決定を覆す場合 |
| `search` | 過去の ADR を参照・質問に回答したい |

不明な場合は `AskUserQuestion` で確認する。

---

## Step 1: ADR 新規作成（create）

### 1-1. 次の連番を確定する

```bash
ls docs/adr/ 2>/dev/null | grep -E '^[0-9]{4}-' | sort | tail -1
```

最後のファイルの番号 + 1 を次の連番とする（例: `0003` → 次は `0004`）。
`docs/adr/` が存在しない場合は `mkdir -p docs/adr/` を実行し、`0001` から開始する。

### 1-2. 決定内容の確認

ユーザーから提案された決定事項（日本語可）を受け取る。
以下の情報が揃っているか確認し、足りなければ日本語で質問する:

- **タイトル**: 英語の短いタイトル（例: `Adopt Jetpack Compose for UI Layer`）
- **Context**: なぜこの決定が必要になったか（背景・課題）
- **Decision**: 何を決定したか（具体的に）
- **Consequences**: 良い点・悪い点・トレードオフ

日本語で受け取った場合は、英語に翻訳・整形して ADR に反映する。

### 1-3. ファイルを生成する

ファイル名: `docs/adr/XXXX-kebab-case-title.md`

```markdown
# XXXX: Title

- Status: Proposed — YYYY-MM-DD
- Date: YYYY-MM-DD

## Context

Describe the issue that motivates this decision, including any relevant constraints.

## Decision

State the architecture decision and the full context in which it is being made.

## Consequences

Describe the resulting context after applying the decision.
List both positive and negative consequences, and any tradeoffs made.
```

生成後、ファイルパスと内容をユーザーに日本語で報告する。

---

## Step 2: ADR ステータス更新（update）

### 2-1. 対象 ADR の特定

```bash
ls docs/adr/
```

ADR 番号またはタイトルキーワードから対象ファイルを特定する。
複数候補がある場合や不明な場合は `AskUserQuestion` で確認する。

### 2-2. ステータスの変更

対象ファイルの `Status:` 行のみを変更する。
**Context / Decision / Consequences セクションは一切改変しない。**

ステータスの遷移:

| 変更前 | 変更後 |
|---|---|
| `Proposed` | `Accepted — YYYY-MM-DD` |
| `Proposed` / `Accepted` | `Superseded by [XXXX: New Title](XXXX-new-title.md) — YYYY-MM-DD` |

Superseded にする場合は、後継 ADR の番号とファイルへのリンクを英語で追記する:

```markdown
- Status: Superseded by [0005: Use Compose Navigation](0005-use-compose-navigation.md) — 2025-05-23
```

変更後、差分をユーザーに日本語で報告する。

---

## Step 3: ADR 検索・参照（search）

### 3-1. 一覧の取得

```bash
ls docs/adr/ 2>/dev/null | grep -E '^[0-9]{4}-'
```

ファイルを列挙し、番号・タイトル・ステータスの一覧をユーザーに日本語で提示する。

### 3-2. 内容の読み取りと回答

ユーザーの質問に関連する ADR を `Read` ツールで読み取る。
読み取った内容をもとに、ユーザーの質問に日本語で回答する。

複数の ADR が関係する場合は、時系列（番号）順に整理して提示する。
Superseded なADRがある場合は、最新の決定を先に、過去の経緯を後に示す。
