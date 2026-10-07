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
- Geyser로 접속한 베드락 에디션 플레이어에게도 표시됩니다

## 기능

- 몹이나 플레이어를 공격하면 공격한 사람의 액션바에 대상의 이름과 남은 HP가 표시됩니다.
- 검 등의 근접 공격뿐 아니라 활, 쇠뇌, 삼지창 같은 발사체와 길들인 늑대 등 펫의 공격에도 표시됩니다.
- HP 색상은 남은 비율에 따라 바뀝니다 (50% 이상: 초록, 25% 이상: 노랑, 그 미만: 빨강).
- 이름을 붙인 몹은 붙인 이름을, 그 외의 몹은 각 플레이어의 게임 언어로 된 이름을 표시합니다.
- 대상을 처치하면 `0.0`으로 표시됩니다. 살아 있는 대상의 HP는 올림 처리하므로 살아 있는데 `0.0`으로 표시되는 일은 없습니다.
- 갑옷 거치대는 대상에서 제외됩니다.
- 명령어 메시지는 각 플레이어의 게임 언어에 맞춰 영어, 일본어, 중국어(간체), 한국어로 표시됩니다 (그 외 언어는 영어).

## 설치

1. [Releases](https://github.com/spa77k/mc-hpview/releases)에서 `hpview-1.0.1.jar`를 다운로드합니다
2. 서버의 `plugins/` 폴더에 넣고 서버를 재시작합니다

## 명령어

| 명령어 | 설명 | 권한 |
|---|---|---|
| `/hpview` | 내 표시를 켜기 / 끄기 전환 | `hpview.toggle` (모두) |
| `/hpview on` / `/hpview off` | 내 표시를 켜기 / 끄기 | `hpview.toggle` (모두) |
| `/hpview reload` | `config.yml` 다시 불러오기 | `hpview.reload` (OP) |

끈 설정은 `plugins/HPView/players.yml`에 저장되며 재시작 후에도 유지됩니다.

## 권한

| 권한 | 설명 | 기본값 |
|---|---|---|
| `hpview.use` | 공격한 대상의 HP가 표시됨 | 모두 |
| `hpview.toggle` | `/hpview`로 내 표시를 전환할 수 있음 | 모두 |
| `hpview.reload` | `/hpview reload`를 사용할 수 있음 | OP |

## 설정 (`plugins/HPView/config.yml`)

```yaml
# MiniMessage 형식. <name> 대상 이름, <hp> 남은 HP, <max> 최대 HP, <color>...</color> 남은 HP에 따른 색상
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

## 빌드

```bash
mvn -B package
```

`target/hpview-1.0.1.jar`가 생성됩니다.

격리된 Paper 서버에서 동작을 확인하려면 `server-data/paper-26.2-129.jar`를 넣은 뒤 다음 명령을 실행합니다.

```bash
python3 scripts/test-hpview-paper.py
```

## 라이선스

[MIT License](LICENSE)
