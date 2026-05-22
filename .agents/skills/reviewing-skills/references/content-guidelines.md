# コンテンツガイドライン

## 目次
- 時間に敏感な情報を避ける
- 一貫した用語を使用する
- 避けるべきアンチパターン

---

## 時間に敏感な情報を避ける

スキルは長期間使用される。古くなる情報を含めると、スキルが誤った指示を与えるリスクがある。

**NG（時間に敏感）：**
```markdown
2025年8月前にこれを行っている場合は、古いAPIを使用してください。
2025年8月以降は、新しいAPIを使用してください。
```

**OK（「古いパターン」セクションを使用）：**
```markdown
## 現在の方法

v2 APIエンドポイントを使用する：`api.example.com/v2/messages`

## 古いパターン

<details>
<summary>レガシーv1 API（2025-08で廃止）</summary>

v1 APIは以下を使用：`api.example.com/v1/messages`

このエンドポイントはサポートされなくなった。
</details>
```

「古いパターン」セクションはメインコンテンツを乱さずに履歴コンテキストを提供する。

---

## 一貫した用語を使用する

1つの用語を選択してスキル全体で使用する。用語が混在するとAIが指示を正しく理解できない場合がある。

**OK（一貫性あり）：**
- 常に「APIエンドポイント」
- 常に「フィールド」
- 常に「抽出」

**NG（一貫性なし）：**
- 「APIエンドポイント」、「URL」、「APIルート」、「パス」を混用
- 「フィールド」、「ボックス」、「要素」、「コントロール」を混用
- 「抽出」、「プル」、「取得」、「取り出す」を混用

---

## 避けるべきアンチパターン

### Windowsスタイルのパスを避ける

常にフォワードスラッシュを使用する（WindowsでもUnixでも動作する）。

- OK：`scripts/helper.py`、`reference/guide.md`
- NG：`scripts\helper.py`、`reference\guide.md`

### 多くのオプションを提示しすぎることを避ける

AIに選択肢を与えすぎると、どれを使うべきか判断できない場合がある。デフォルトを1つ示し、例外として追記するスタイルが効果的。

**NG（選択肢が多すぎる）：**
```markdown
pypdfまたはpdfplumberまたはPyMuPDFまたはpdf2imageまたは...を使用できます
```

**OK（デフォルトを提供し、例外を追記）：**
```markdown
テキスト抽出にはpdfplumberを使用する：
```python
import pdfplumber
```

スキャンされたPDFでOCRが必要な場合のみ、代わりにpdf2imageとpytesseractを使用する。
```

### 例が抽象的すぎる

例は具体的で実際の使用に近いものにする。抽象的な例はAIが正しいスタイルや詳細レベルを理解するのを妨げる。

**NG（抽象的）：**
```markdown
データを適切に処理してください。
```

**OK（具体的）：**
```markdown
CSVを読み込み、nullを0で埋め、合計を計算して結果を返す：
```python
df = pd.read_csv("data.csv")
df.fillna(0, inplace=True)
result = df["amount"].sum()
```
```

### ツールがインストールされていると仮定しない

**NG（インストールを仮定）：**
```markdown
pdfライブラリを使用してファイルを処理する。
```

**OK（依存関係を明示）：**
```markdown
必要なパッケージをインストールする：`pip install pypdf`

その後、使用する：
```python
from pypdf import PdfReader
reader = PdfReader("file.pdf")
```
```
