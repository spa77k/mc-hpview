# HPView

**English** | [日本語](README.ja.md) | [简体中文](README.zh-CN.md) | [한국어](README.ko.md)

A PaperMC plugin that shows the remaining HP of the target you attack on your action bar.

```
Zombie ❤ 12.0/20.0
```

## Requirements

- Minecraft server: PaperMC 26.2 (Paper API `1.21.4` or later)
- Java: 21 or later
- Dependencies: none
- Also works for Bedrock players connected through Geyser

## Features

- When you attack a mob or player, their name and remaining HP appear on your action bar.
- Works for melee attacks as well as projectiles (bows, crossbows, tridents) and attacks by tamed pets such as wolves.
- The HP color changes with the remaining ratio (50% or more: green, 25% or more: yellow, below that: red).
- Named mobs show their custom name; other mobs show their name in each player's own language.
- When a target is killed, `0.0` is shown. HP of living targets is rounded up, so a living target never shows `0.0`.
- Armor stands are ignored.
- Command messages follow each player's client language: English, Japanese, Simplified Chinese or Korean (English for any other language).

## Installation

1. Download `hpview-1.0.0.jar` from [Releases](https://github.com/spa77k/mc-hpview/releases)
2. Put it in the server's `plugins/` folder and restart the server

## Commands

| Command | Description | Permission |
|---|---|---|
| `/hpview` | Toggle the display for yourself | `hpview.toggle` (everyone) |
| `/hpview on` / `/hpview off` | Turn the display on / off for yourself | `hpview.toggle` (everyone) |
| `/hpview reload` | Reload `config.yml` | `hpview.reload` (OP) |

The off setting is saved in `plugins/HPView/players.yml` and persists across restarts.

## Permissions

| Permission | Description | Default |
|---|---|---|
| `hpview.use` | The HP of attacked targets is shown | everyone |
| `hpview.toggle` | Toggle your own display with `/hpview` | everyone |
| `hpview.reload` | Use `/hpview reload` | OP |

## Configuration (`plugins/HPView/config.yml`)

```yaml
# MiniMessage format. <name> target name, <hp> remaining HP, <max> max HP, <color>...</color> color based on remaining HP
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

## Build

```bash
mvn -B package
```

This produces `target/hpview-1.0.0.jar`.

To verify behavior on an isolated Paper server, place `server-data/paper-26.2-129.jar` and run:

```bash
python3 scripts/test-hpview-paper.py
```

## License

[MIT License](LICENSE)
