# HPView

[English](README.md) | [日本語](README.ja.md) | **简体中文** | [한국어](README.ko.md)

一款 PaperMC 插件，在动作栏上显示所攻击目标的剩余生命值。

```
僵尸 ❤ 12.0/20.0
```

## 运行环境

- Minecraft 服务器：PaperMC 26.2、26.3（Paper API `1.21.4` 及以上）
- Java：21 及以上
- 前置插件：无
- 基岩版兼容：通过 Geyser 接入的基岩版玩家同样可以正常显示

## 功能

- 攻击生物或玩家时，攻击者的动作栏上会显示目标的名称及剩余生命值。
- 支持近战攻击（如剑）、弹射物（弓、弩、三叉戟等）以及已驯服宠物（如狼）的攻击。
- 生命值颜色会根据剩余生命值比例动态变化（默认：50% 及以上为绿色，25% 及以上为黄色，低于 25% 为红色）。
- 已命名的生物显示其自定义名称，未命名的生物则按照各玩家客户端的游戏语言显示对应名称。
- 击杀目标时显示 `0.0`。存活目标的生命值向上取整，避免存活状态下显示为 `0.0`。
- 忽略盔甲架。
- 插件提示信息会根据玩家客户端语言显示英语、日语、简体中文或韩语（其他语言默认为英语）。

## 安装

1. 从 [Releases](https://github.com/spa77k/mc-hpview/releases) 下载 `hpview-1.0.1.jar`。
2. 将 JAR 文件放入服务器的 `plugins/` 目录中。
3. 重启服务器。

## 命令

| 命令 | 说明 | 权限 |
|---|---|---|
| `/hpview` | 切换自己的生命值显示开关 | `hpview.toggle`（所有人） |
| `/hpview on` | 开启自己的生命值显示 | `hpview.toggle`（所有人） |
| `/hpview off` | 关闭自己的生命值显示 | `hpview.toggle`（所有人） |
| `/hpview reload` | 重新加载 `config.yml` | `hpview.reload`（OP） |

每个玩家的关闭状态会保存在 `plugins/HPView/players.yml` 中，服务器重启后依然有效。

## 权限

| 权限 | 说明 | 默认 |
|---|---|---|
| `hpview.use` | 攻击目标时显示其生命值 | 所有人 |
| `hpview.toggle` | 允许使用 `/hpview` 切换自己的生命值显示 | 所有人 |
| `hpview.reload` | 允许使用 `/hpview reload` 重新加载配置 | OP |

## 配置（`plugins/HPView/config.yml`）

```yaml
# MiniMessage 格式。<name>：目标名称，<hp>：剩余生命值，<max>：最大生命值，<color>...</color>：按剩余生命值变化的颜色
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

| 配置项 | 默认值 | 说明 |
|---|---|---|
| `format` | `"<white><name></white> <color>❤ <hp>/<max></color>"` | 动作栏文本格式（MiniMessage 格式）。支持的占位符：`<name>`、`<hp>`、`<max>`、`<color>` |
| `thresholds.high` | `0.5` | 高生命值颜色的比例阈值（50% 及以上） |
| `thresholds.low` | `0.25` | 中生命值颜色的比例阈值（25% 及以上，低于此值使用低生命值颜色） |
| `colors.high` | `"#55FF55"` | 生命值比例达到或超过 `thresholds.high` 时的颜色代码 |
| `colors.mid` | `"#FFFF55"` | 生命值比例在 `thresholds.low` 与 `thresholds.high` 之间时的颜色代码 |
| `colors.low` | `"#FF5555"` | 生命值比例低于 `thresholds.low` 时的颜色代码 |
| `decimals` | `1` | 显示生命值的小数位数（0-2） |
| `show-players` | `true` | 攻击其他玩家时是否也显示生命值 |

## 许可证

[MIT License](LICENSE)
