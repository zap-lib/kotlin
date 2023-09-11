# Zap: A cross-platform library for multi-device applications

---

## Introduction

- Tap 프레임워크 재현 + 확장 프로젝트.
- 크로스 플랫폼 멀티 모바일 애플리케이션 개발을 위한 라이브러리.
- 원격 디바이스와의 통신 절차 및 데이터소스를 추상화해 쉬운 멀티 디바이스 개발 환경을 제공.

---

## Requirements

- 로컬 디바이스가 리모트 디바이스의 데이터소스를 이용할 수 있다:
  - e.g., 로컬 디바이스가 리모트 디바이스의 가속도 센서를 사용.
- 같은 통신 프로토콜을 통해 다른 플랫폼의 앱과 커뮤니케이션할 수 있다:
  - e.g., PC에서 모바일 디바이스의 센서를 사용.
- 하나의 로컬 디바이스에 여러 리모트 디바이스가 동시에 데이터를 제공할 수 있다.

---

## Architecture

- NFC를 통한 연결, IP 기반으로 커뮤니케이션.
- 연결 이후 게스트와 호스트가 양방향 커넥션을 유지.
- 다이어그램: [figma.com/zap](https://www.figma.com/file/OOhAq7N2JbBQOCUWz73jA2/Zap?type=architecture)

---

## Prototype Goals

- Android, Java/Kotlin, (+ JavaScript) 라이브러리 구현, 오픈소스 릴리즈.
- 동작 센서, GPS, 카메라, (+ 클립보드), (+ 미디어) 지원.
- (+ 통신 대칭키 암호화)
