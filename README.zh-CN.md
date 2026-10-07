# HPView

[English](README.md) | [日本語](README.ja.md) | **简体中文** | [한국어](README.ko.md)

一款 PaperMC 插件，在动作栏上显示你所攻击目标的剩余生命值。

```
僵尸 ❤ 12.0/20.0
```

## 运行环境

- Minecraft 服务器：PaperMC 26.2、26.3（Paper API `1.21.4` 及以上）
- Java：21 及以上
- 前置插件：无
- 通过 Geyser 进入的基岩版玩家同样可以看到

## 功能

- 攻击生物或玩家时，攻击者的动作栏上会显示对方的名称和剩余生命值。
- 除了剑等近战攻击外，弓、弩、三叉戟等弹射物以及驯服的狼等宠物的攻击也会显示。
- 生命值的颜色随剩余比例变化（50% 及以上：绿色，25% 及以上：黄色，低于 25%：红色）。
- 已命名的生物显示其自定义名称，其他生物按各玩家的游戏语言显示名称。
- 击杀目标时显示 `0.0`。存活目标的生命值会向上取整，因此存活时不会显示 `0.0`。
- 不包括盔甲架。
- 命令提示会根据各玩家的游戏语言显示为英语、日语、简体中文或韩语（其他语言显示英语）。

## 安装

1. 从 [Releases](https://github.com/spa77k/mc-hpview/releases) 下载 `hpview-1.0.1.jar`
2. 放入服务器的 `plugins/` 文件夹并重启服务器

## 命令

| 命令 | 说明 | 权限 |
|---|---|---|
| `/hpview` | 切换自己的显示开关 | `hpview.toggle`（所有人） |
| `/hpview on` / `/hpview off` | 开启 / 关闭自己的显示 | `hpview.toggle`（所有人） |
| `/hpview reload` | 重新加载 `config.yml` | `hpview.reload`（OP） |

关闭的设置保存在 `plugins/HPView/players.yml` 中，重启后依然有效。

## 权限

| 权限 | 说明 | 默认 |
|---|---|---|
| `hpview.use` | 显示所攻击目标的生命值 | 所有人 |
| `hpview.toggle` | 可以用 `/hpview` 切换自己的显示 | 所有人 |
| `hpview.reload` | 可以使用 `/hpview reload` | OP |

## 配置（`plugins/HPView/config.yml`）

```yaml
# MiniMessage 格式。<name> 目标名称，<hp> 剩余生命值，<max> 最大生命值，<color>...</color> 按剩余生命值变化的颜色
format: "<white><name></white> <color>❤ <hp>/<max></color>"

thresholds:
  high: 0.5    # 达到此比例及以上使用 colors.high
  low: 0.25    # 达到此比例及以上使用 colors.mid，低于此比例使用 colors.low
colors:
  high: "#55FF55"
  mid: "#FFFF55"
  low: "#FF5555"

decimals: 1          # 生命值的小数位数（0-2）
show-players: true   # 攻击玩家时也显示
```

## 构建

```bash
mvn -B package
```

会生成 `target/hpview-1.0.1.jar`。

如需在隔离的 Paper 服务器上验证运行效果，请先放置 `server-data/paper-26.2-129.jar`，然后运行：

```bash
python3 scripts/test-hpview-paper.py
```

## 许可证

[MIT License](LICENSE)
