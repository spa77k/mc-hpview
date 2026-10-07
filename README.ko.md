# HPView

[English](README.md) | [日本語](README.ja.md) | [简体中文](README.zh-CN.md) | **한국어**

공격한 대상의 남은 HP를 액션바에 표시하는 PaperMC 플러그인입니다.

```
좀비 ❤ 12.0/20.0
```

## 요구 사항

- Minecraft 서버: PaperMC 26.2·26.3 (Paper API `1.21.4` 이상)
- Java: 21 이상
- 의존 플러그인: 없음
- 베드락 에디션: Geyser를 통해 접속한 플레이어에게도 표시됩니다

## 기능

- 몹이나 플레이어를 공격하면 공격자의 액션바에 대상의 이름과 남은 HP가 표시됩니다.
- 근접 공격(검 등)뿐만 아니라 발사체(활, 쇠뇌, 삼지창 등) 및 길들인 펫(늑대 등)에 의한 공격도 지원합니다.
- 남은 HP 비율에 따라 HP 색상이 변경됩니다 (기본값: 50% 이상 초록색, 25% 이상 노란색, 25% 미만 빨간색).
- 이름표 등으로 이름을 붙인 몹은 지정된 이름을 표시하며, 그 외의 몹은 각 플레이어의 클라이언트 언어에 맞는 이름을 표시합니다.
- 대상을 처치했을 때는 `0.0`으로 표시됩니다. 살아 있는 대상의 소수점은 올림 처리되므로 생존 중에 `0.0`으로 표시되지 않습니다.
- 갑옷 거치대는 표시 대상에서 제외됩니다.
- 플러그인 안내 메시지는 각 플레이어의 클라이언트 언어(영어, 일본어, 중국어 간체, 한국어)에 맞춰 표시됩니다 (기타 언어는 영어).

## 설치

1. [Releases](https://github.com/spa77k/mc-hpview/releases)에서 `hpview-1.0.1.jar`를 다운로드합니다.
2. 서버의 `plugins/` 폴더에 JAR 파일을 넣습니다.
3. 서버를 재시작합니다.

## 명령어

| 명령어 | 설명 | 권한 |
|---|---|---|
| `/hpview` | 내 HP 표시 켜기/끄기 전환 | `hpview.toggle` (모두) |
| `/hpview on` | 내 HP 표시 켜기 | `hpview.toggle` (모두) |
| `/hpview off` | 내 HP 표시 끄기 | `hpview.toggle` (모두) |
| `/hpview reload` | `config.yml` 다시 불러오기 | `hpview.reload` (OP) |

플레이어별 끄기 설정은 `plugins/HPView/players.yml`에 저장되며 서버 재시작 후에도 유지됩니다.

## 권한

| 권한 | 설명 | 기본값 |
|---|---|---|
| `hpview.use` | 공격한 대상의 HP가 표시됨 | 모두 |
| `hpview.toggle` | `/hpview`로 내 HP 표시를 전환할 수 있음 | 모두 |
| `hpview.reload` | `/hpview reload`로 설정을 다시 불러올 수 있음 | OP |

## 설정 (`plugins/HPView/config.yml`)

```yaml
# MiniMessage 형식. <name>: 대상 이름, <hp>: 남은 HP, <max>: 최대 HP, <color>...</color>: 남은 HP에 따른 색상
format: "<white><name></white> <color>❤ <hp>/<max></color>"

thresholds:
  high: 0.5    # 이 비율 이상이면 colors.high
  low: 0.25    # 이 비율 이상이면 colors.mid, 미만이면 colors.low
colors:
  high: "#55FF55"
  mid: "#FFFF55"
  low: "#FF5555"

decimals: 1          # HP 소수점 자릿수 (0-2)
show-players: true   # 플레이어를 공격할 때도 표시
```

| 설정 키 | 기본값 | 설명 |
|---|---|---|
| `format` | `"<white><name></white> <color>❤ <hp>/<max></color>"` | 액션바 표시 형식 (MiniMessage 형식). 사용 가능한 플레이스홀더: `<name>`, `<hp>`, `<max>`, `<color>` |
| `thresholds.high` | `0.5` | 높은 HP 색상의 기준 비율 (50% 이상) |
| `thresholds.low` | `0.25` | 중간 HP 색상의 기준 비율 (25% 이상, 미만은 낮은 HP 색상) |
| `colors.high` | `"#55FF55"` | HP 비율이 `thresholds.high` 이상일 때의 색상 코드 |
| `colors.mid` | `"#FFFF55"` | HP 비율이 `thresholds.low` 이상 `thresholds.high` 미만일 때의 색상 코드 |
| `colors.low` | `"#FF5555"` | HP 비율이 `thresholds.low` 미만일 때의 색상 코드 |
| `decimals` | `1` | HP 표시의 소수점 자릿수 (0-2) |
| `show-players` | `true` | 다른 플레이어를 공격할 때도 HP를 표시할지 여부 |

## 라이선스

[MIT License](LICENSE)
