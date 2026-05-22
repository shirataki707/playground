# スキル構造

## 目次
- YAMLフロントマター要件
- 命名規則
- 効果的な説明の書き方
- argument-hintの書き方
- 段階的開示パターン
- 深くネストされた参照を避ける
- 目次を使用して長い参照ファイルを構造化する

---

## YAMLフロントマター要件

SKILL.mdフロントマターには `name` と `description` の2つのフィールドが必須。引数がある場合は `argument-hint` も追加する。

**`name` フィールド：**
- 最大64文字
- 小文字、数字、ハイフンのみ
- XMLタグを含まない
- 予約語を含まない：`anthropic`、`claude`

**`description` フィールド：**
- 空でない
- 最大1024文字
- XMLタグを含まない
- スキルが何をするか・いつ使用するかを説明する

**`argument-hint` フィールド：**
- 引数を受け取るスキルには必ず追加する
- **値はダブルクォートで囲む**（Claude以外のAIも正しく解釈できるようにするため）
- OK：`argument-hint: "[url] [from-version] [to-version]"`
- NG：`argument-hint: [url] [from-version] [to-version]`（クォートなしはClaudeのみ動作）

---

## 命名規則

**必須：動名詞形（動詞 + -ing）**
- `processing-pdfs`
- `analyzing-spreadsheets`
- `managing-databases`
- `testing-code`
- `writing-documentation`
- `reviewing-skills`

**許容されない代替：**
- 名詞句：`pdf-processing`、`spreadsheet-analysis` → NG
- アクション指向：`process-pdfs`、`analyze-spreadsheets` → NG

**避けるべき：**
- 曖昧な名前：`helper`、`utils`、`tools`
- 過度に一般的：`documents`、`data`、`files`
- 予約語：`anthropic-helper`、`claude-tools`
- コレクション内の一貫性のないパターン

一貫した命名により、ドキュメントや会話でのスキル参照・整理・検索が容易になる。

---

## 効果的な説明の書き方

`description` はスキル発見の主要なメカニズム。AIは100以上のスキルから正しいスキルを選択するためにこれを使用する。

**重要：常に三人称で書く。** 説明はシステムプロンプトに挿入されるため、視点の不一致は発見の問題を引き起こす。

- 良い：「Excelファイルを処理してレポートを生成します」
- NG：「Excelファイルの処理をお手伝いできます」
- NG：「これを使用してExcelファイルを処理できます」

**スキルが何をするか＋いつ使用するかの両方を含める：**

```yaml
# PDF処理スキルの例
description: PDFファイルからテキストと表を抽出し、フォームに入力し、ドキュメントをマージします。PDFファイル、フォーム、またはドキュメント抽出について言及している場合に使用してください。
```

```yaml
# Excel分析スキルの例
description: Excelスプレッドシートを分析し、ピボットテーブルを作成し、グラフを生成します。Excelファイル、スプレッドシート、表形式データ、または.xlsxファイルを分析する場合に使用してください。
```

**避けるべき曖昧な説明：**
```yaml
description: ドキュメントに役立ちます         # NG：何をするか不明
description: データを処理します               # NG：具体性がない
description: ファイルでいろいろなことをします  # NG：何も伝わらない
```

---

## argument-hintの書き方

引数を受け取るスキルは `argument-hint` フィールドで引数の形式をドキュメント化する。

**フォーマットのルール：**

```yaml
# OK：クォートで囲む（Claude・他のAI両方対応）
argument-hint: "[url] [from-version] [to-version]"

# NG：クォートなし（Claudeのみ動作）
argument-hint: [url] [from-version] [to-version]
```

**なぜクォートが必要か：**
YAMLパーサーによってはクォートなしの `[...]` を配列リテラルとして解釈する場合がある。クォートで囲むことで文字列として確実に扱われ、Claude以外のAI（GPT系、Gemini系など）でも正しく読み取れる。

**引数名の規則：**
- 角括弧で囲む：`[arg-name]`
- ハイフン区切りの小文字：`[from-version]`（スネークケース不可）
- スペース区切りで複数引数を並べる：`"[arg1] [arg2] [arg3]"`

**完全な例：**
```yaml
---
name: checking-library-updates
description: ライブラリの2バージョン間のリリースノートを分析します。
argument-hint: "[url] [from-version] [to-version]"
---
```

---

## 段階的開示パターン

SKILL.mdは概要として機能し、AIを詳細な資料に指し示す。

**3レベルのローディング：**
1. **メタデータ**（name + description）：常時コンテキストに存在（約100ワード）
2. **SKILL.mdボディ**：スキルがトリガーされたときにロード（500行以下推奨）
3. **バンドルされたリソース**：必要に応じてロード（制限なし）

**実践的なガイダンス：**
- SKILL.mdボディを500行以下に保つ
- 500行に近づいたらコンテンツを別ファイルに分割する
- SKILL.mdから参照ファイルへのリンクと、いつ読むかのガイダンスを提供する

**スキルディレクトリ構造の例：**
```
pdf/
├── SKILL.md              # メイン指示（トリガー時にロード）
├── FORMS.md              # フォーム入力ガイド（必要に応じてロード）
├── reference.md          # APIリファレンス（必要に応じてロード）
└── scripts/
    ├── analyze_form.py   # ユーティリティスクリプト（実行、ロードなし）
    └── fill_form.py      # フォーム入力スクリプト
```

**パターン1：参照付きの高レベルガイド**
```markdown
## 高度な機能

**フォーム入力**：完全なガイドについては[FORMS.md](FORMS.md)を参照してください
**APIリファレンス**：すべてのメソッドについては[REFERENCE.md](REFERENCE.md)を参照してください
```

**パターン2：ドメイン固有の組織**

複数のドメインを持つスキルでは、ドメイン別にコンテンツを整理して不要なコンテキストのロードを避ける。

```
bigquery-skill/
├── SKILL.md (概要とナビゲーション)
└── reference/
    ├── finance.md  (収益、請求指標)
    ├── sales.md    (機会、パイプライン)
    └── product.md  (API使用、機能)
```

**パターン3：条件付き詳細**
```markdown
**追跡変更の場合**：[REDLINING.md](REDLINING.md)を参照してください
**OOXML詳細の場合**：[OOXML.md](OOXML.md)を参照してください
```

---

## 深くネストされた参照を避ける

AIはネストされた参照に遭遇すると `head -100` などでプレビューして、不完全な情報を得る可能性がある。

**SKILL.mdから1レベル深い参照を保つ：**

NG（深すぎる）：
```
SKILL.md → advanced.md → details.md → 実際の情報
```

OK（1レベル深い）：
```
SKILL.md → advanced.md（実際の情報）
SKILL.md → reference.md（実際の情報）
```

---

## 目次を使用して長い参照ファイルを構造化する

100行以上の参照ファイルには上部に目次を含める。部分的な読み取りでプレビューしても、AIが利用可能な情報の全体像を把握できる。

**例：**
```markdown
# APIリファレンス

## 目次
- 認証とセットアップ
- コアメソッド（作成、読み取り、更新、削除）
- 高度な機能（バッチ操作、Webhook）
- エラー処理パターン
- コード例

## 認証とセットアップ
...
```
