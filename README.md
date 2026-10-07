# HPView

**English** | [日本語](README.ja.md) | [简体中文](README.zh-CN.md) | [한국어](README.ko.md)

A PaperMC plugin that displays the remaining HP of an attacked target on your action bar.

```
Zombie ❤ 12.0/20.0
```

## Requirements

- Minecraft server: PaperMC 26.2 and 26.3 (Paper API `1.21.4` or later)
- Java: 21 or later
- Dependencies: None
- Bedrock Edition: Also works for Bedrock players connected via Geyser

## Features

- Displays the target's name and remaining HP on the action bar when you attack a mob or player.
- Supports melee attacks, projectiles (bows, crossbows, tridents), and attacks by tamed pets (such as wolves).
- HP text color changes dynamically based on the remaining HP ratio (by default: green at 50% or more, yellow at 25% or more, red below that).
- Shows custom names for named mobs, and localized mob names matching each player's client language for unnamed mobs.
- Displays `0.0` when the target is killed. The HP of living targets is rounded up so that living entities never show `0.0`.
- Armor stands are ignored.
- Plugin messages are displayed in each player's client language: English, Japanese, Simplified Chinese, or Korean (defaults to English for other languages).

## Installation

1. Download `hpview-1.0.1.jar` from [Releases](https://github.com/spa77k/mc-hpview/releases).
2. Place the jar file into the server's `plugins/` directory.
3. Restart the server.

## Commands

| Command | Description | Permission |
|---|---|---|
| `/hpview` | Toggle your own HP display on or off | `hpview.toggle` (everyone) |
| `/hpview on` | Turn on your own HP display | `hpview.toggle` (everyone) |
| `/hpview off` | Turn off your own HP display | `hpview.toggle` (everyone) |
| `/hpview reload` | Reload `config.yml` | `hpview.reload` (OP) |

The off state is saved per player in `plugins/HPView/players.yml` and persists across server restarts.

## Permissions

| Permission | Description | Default |
|---|---|---|
| `hpview.use` | Displays HP when attacking targets | everyone |
| `hpview.toggle` | Allows toggling your own display with `/hpview` | everyone |
| `hpview.reload` | Allows reloading configuration with `/hpview reload` | OP |

## Configuration (`plugins/HPView/config.yml`)

```yaml
# MiniMessage format. <name>: target name, <hp>: remaining HP, <max>: max HP, <color>...</color>: color based on remaining HP
format: "<white><name></white> <color>❤ <hp>/<max></color>"

thresholds:
  high: 0.5    # at or above this ratio: colors.high
  low: 0.25    # at or above this ratio: colors.mid, below it: colors.low
colors:
  high: "#55FF55"
  mid: "#FFFF55"
  low: "#FF5555"

decimals: 1          # decimal places for HP (0-2)
show-players: true   # also show HP when attacking players
```

| Key | Default | Description |
|---|---|---|
| `format` | `"<white><name></white> <color>❤ <hp>/<max></color>"` | Format for the action bar text using MiniMessage tags. Supported placeholders: `<name>`, `<hp>`, `<max>`, `<color>`. |
| `thresholds.high` | `0.5` | Ratio threshold for the high health color (at or above 50%). |
| `thresholds.low` | `0.25` | Ratio threshold for the medium health color (at or above 25%; below it uses low). |
| `colors.high` | `"#55FF55"` | Color hex code when remaining HP is at or above `thresholds.high`. |
| `colors.mid` | `"#FFFF55"` | Color hex code when remaining HP is between `thresholds.low` and `thresholds.high`. |
| `colors.low` | `"#FF5555"` | Color hex code when remaining HP is below `thresholds.low`. |
| `decimals` | `1` | Number of decimal places to display for HP (between 0 and 2). |
| `show-players` | `true` | Whether to show HP when attacking other players. |

## License

[MIT License](LICENSE)
