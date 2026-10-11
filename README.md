# AProtectWorldPlus

월드별로 블록 파괴, PvP, 인벤 보존 등의 행위를 제한하거나 허용할 수 있는 플러그인입니다.
제한 메시지는 액션바(ActionBar)로 표시되어 채팅창 도배를 방지합니다.

## 기능
- 월드별 독립적인 보호 설정 지원
- 블록 파괴/설치, PvP, 낙하데미지, 배고픔 감소, 아이템 드롭, 사망 시 인벤 보존 기능
- OP(운영자)는 모든 제한에서 자동 제외
- 명령어 탭 완성(Tab Complete) 지원

## 설치
1. `AProtectWorldPlus.jar` 파일을 다운로드합니다.
2. 서버의 `plugins` 폴더에 넣습니다.
3. 서버를 재시작합니다.
4. `plugins/AProtectWorldPlus/cache.yml` 파일이 자동 생성됩니다.

## 명령어
기본적으로 OP 권한이 있는 사용자만 사용 가능합니다.

| 명령어 | 설명 |
| :--- | :--- |
| `/월드보호 정보 <월드명>` | 해당 월드의 전체 설정 상태 확인 |
| `/월드보호 정보 <월드명> <타입>` | 해당 월드의 특정 설정 상태 확인 |
| `/월드보호 설정 <월드명> <타입> <on/off>` | 설정 변경 (on: 제한, off: 허용) |

## 설정 타입
명령어 또는 설정 파일에서 사용하는 타입 목록입니다. (대소문자 구분 없음)

- `drop_item` : 아이템 드롭 방지
- `exhaust` : 배고픔 감소 방지 (게이지 20 고정)
- `falling_damage` : 낙하 데미지 방지
- `invensave` : 사망 시 인벤토리 및 경험치 보존
- `block_break` : 블록 파괴 방지
- `block_place` : 블록 설치 방지
- `pvp` : PvP 방지 (직접 공격 및 화살 등 투사체 포함)

## 설정 파일
경로: `plugins/AProtectWorldPlus/cache.yml`

명령어로도 변경이 가능하지만, 서버 정지 상태에서 파일을 직접 수정해도 정상적으로 반영됩니다.

```yaml
world:
  drop_item: 'off'
  exhaust: 'off'
  falling_damage: 'off'
  invensave: 'off'
  block_break: 'off'
  block_place: 'off'
  pvp: 'off'
```

- `on`: 해당 행위를 제한합니다.
- `off`: 해당 행위를 허용합니다. (기본값)
