# HPView

[English](README.md) | **日本語** | [简体中文](README.zh-CN.md) | [한국어](README.ko.md)

攻撃した相手の残りHPをアクションバーに表示する PaperMC プラグインです。

```
ゾンビ ❤ 12.0/20.0
```

## 動作環境

- Minecraft サーバー: PaperMC 26.2・26.3（Paper API `1.21.4` 以降）
- Java: 21 以降
- 依存プラグイン: なし
- 統合版（Geyser 経由）のプレイヤーにも表示されます

## 機能

- Mob やプレイヤーを攻撃すると、攻撃者のアクションバーに相手の名前と残りHPが表示されます。
- 近接攻撃（剣など）だけでなく、飛び道具（弓、クロスボウ、トライデントなど）や飼いならしたペット（オオカミなど）による攻撃にも対応しています。
- 残りHPの割合に応じてHP表示の色が変わります（既定値: 50%以上は緑、25%以上は黄、25%未満は赤）。
- 名札などで名前を付けたMobはカスタム名、それ以外のMobは各プレイヤーのクライアント言語に応じた名前を表示します。
- 相手を倒したときは `0.0` と表示されます。生きている相手の端数は切り上げられるため、生存中に `0.0` と表示されることはありません。
- 防具立てへの攻撃は無視されます。
- プラグインのメッセージは、各プレイヤーのクライアント言語に合わせて英語・日本語・簡体字中国語・韓国語で表示されます（それ以外の言語では英語）。

## 導入方法

1. [Releases](https://github.com/spa77k/mc-hpview/releases) から `hpview-1.0.1.jar` をダウンロードします。
2. サーバーの `plugins/` フォルダに配置します。
3. サーバーを再起動します。

## コマンド

| コマンド | 内容 | 権限 |
|---|---|---|
| `/hpview` | 自分のHP表示のオン・オフを切り替える | `hpview.toggle`（全員） |
| `/hpview on` | 自分のHP表示をオンにする | `hpview.toggle`（全員） |
| `/hpview off` | 自分のHP表示をオフにする | `hpview.toggle`（全員） |
| `/hpview reload` | `config.yml` を再読み込みする | `hpview.reload`（OP） |

オフにした設定はプレイヤーごとに `plugins/HPView/players.yml` に保存され、サーバー再起動後も保持されます。

## 権限

| 権限 | 内容 | 既定値 |
|---|---|---|
| `hpview.use` | 攻撃した相手のHPが表示される | 全員 |
| `hpview.toggle` | `/hpview` で自分のHP表示を切り替えられる | 全員 |
| `hpview.reload` | `/hpview reload` で設定を再読み込みできる | OP |

## 設定（`plugins/HPView/config.yml`）

```yaml
# MiniMessage形式。<name>: 相手の名前、<hp>: 残りHP、<max>: 最大HP、<color>〜</color>: 残りHPに応じた色
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

| 設定キー | 既定値 | 説明 |
|---|---|---|
| `format` | `"<white><name></white> <color>❤ <hp>/<max></color>"` | アクションバーの表示形式（MiniMessage形式）。利用可能なプレースホルダー: `<name>`, `<hp>`, `<max>`, `<color>` |
| `thresholds.high` | `0.5` | 高HP色のしきい値割合（50%以上） |
| `thresholds.low` | `0.25` | 中HP色のしきい値割合（25%以上、これ未満は低HP色） |
| `colors.high` | `"#55FF55"` | HP割合が `thresholds.high` 以上のときの色コード |
| `colors.mid` | `"#FFFF55"` | HP割合が `thresholds.low` 以上かつ `thresholds.high` 未満のときの色コード |
| `colors.low` | `"#FF5555"` | HP割合が `thresholds.low` 未満のときの色コード |
| `decimals` | `1` | HP表示の小数点以下の桁数（0〜2） |
| `show-players` | `true` | プレイヤーを攻撃したときもHPを表示するかどうか |

## ライセンス

[MIT License](LICENSE)
