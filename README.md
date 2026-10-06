# HPView

PaperMC サーバー向けの、攻撃した相手の残りHPをアクションバーに表示するプラグインです。

```
ゾンビ ❤ 12.0/20.0
```

## 動作環境

- Minecraft サーバー: PaperMC 26.2（Paper API `1.21.4` 以降）
- Java: 21 以降
- 依存プラグイン: なし
- 統合版（Geyser 経由）のプレイヤーにも表示されます

## できること

- Mob・プレイヤーを攻撃すると、攻撃した人のアクションバーに相手の名前と残りHPが出ます。
- 剣などの直接攻撃のほか、弓・クロスボウ・トライデントなどの飛び道具、飼いならしたオオカミなどペットの攻撃でも出ます。
- HPの色は残りの割合で変わります（半分以上: 緑、4分の1以上: 黄、それ未満: 赤）。
- 名前を付けたMobは付けた名前、それ以外は各プレイヤーの言語でのMob名を表示します。
- 倒したときは `0.0` と表示します。生きている相手の端数は切り上げるので、生きているのに `0.0` と出ることはありません。
- 防具立ては対象外です。

## 導入

1. [Releases](https://github.com/spa77k/mc-hpview/releases) から `hpview-1.0.0.jar` をダウンロードする
2. サーバーの `plugins/` に置いて、サーバーを再起動する

## コマンド

| コマンド | 内容 | 権限 |
|---|---|---|
| `/hpview` | 自分の表示のオン・オフを切り替える | `hpview.toggle`（全員） |
| `/hpview on` / `/hpview off` | 自分の表示をオン・オフにする | `hpview.toggle`（全員） |
| `/hpview reload` | `config.yml` を読み直す | `hpview.reload`（OP） |

オフにした設定は `plugins/HPView/players.yml` に保存され、再起動後も残ります。

## 権限

| 権限 | 内容 | 既定 |
|---|---|---|
| `hpview.use` | 攻撃した相手のHPが表示される | 全員 |
| `hpview.toggle` | `/hpview` で自分の表示を切り替えられる | 全員 |
| `hpview.reload` | `/hpview reload` が使える | OP |

## 設定（`plugins/HPView/config.yml`）

```yaml
# MiniMessage形式。<name> 相手の名前、<hp> 残りHP、<max> 最大HP、<color>〜</color> 残りHPに応じた色
format: "<white><name></white> <color>❤ <hp>/<max></color>"

thresholds:
  high: 0.5    # この割合以上は colors.high
  low: 0.25    # この割合以上は colors.mid、未満は colors.low
colors:
  high: "#55FF55"
  mid: "#FFFF55"
  low: "#FF5555"

decimals: 1          # HPの小数点以下の桁数（0〜2）
show-players: true   # プレイヤーを攻撃したときも表示する
```

## ビルド

```bash
mvn -B package
```

`target/hpview-1.0.0.jar` ができます。

隔離した Paper での動作確認は、`server-data/paper-26.2-129.jar` を置いてから次のコマンドで行えます。

```bash
python3 scripts/test-hpview-paper.py
```

## ライセンス

[MIT License](LICENSE)
