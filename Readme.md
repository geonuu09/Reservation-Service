# 개요
매장 테이블 예약 서비스

Goal : 이 프로젝트는 식당이나 점포를 이용하기 전에 미리 예약을 할 수 있는 서비스를 제공합니다. 사용자는 매장을 검색하고 예약할 수 있으며, 매장 관리자는 매장 정보를 관리할 수 있습니다.

## 주요 기능
- 사용자 및 파트너(점장) 회원가입 및 로그인
- 매장 등록, 수정, 삭제 (파트너 전용)
- 매장 검색 및 상세 정보 조회
- 예약 생성 및 관리
- 키오스크를 통한 방문 확인
- 리뷰 작성, 수정, 삭제
-
## 기술 스택
- Java 17
- Spring Boot (version : 3.2.7)
- Spring Data JPA
- Spring Security
- MariaDB
- Gradle

## 회원
- 회원가입
  - 고객 / 점장 (role_user, role_partner)

### 고객
- 매장검색
- 매장 상세정보
- 예약
  - 요청 시 필요 : 상점 id, 예약일, 내 정보(이름, 전화번호)
- 키오스크 방문 확인
  - 예약 10분 전까지 방문확인
- 리뷰
  - 작성 : 예약 및 사용한 이력이 있는 고객만
  - 수정 : 고객만
  - 삭제 : 작성자(고객) + 파트너 권한
### 점장
- 상점 관리
  - 상점 등록 / 수정 / 삭제
  - 파트너 인지 확인(승인 조건 X)
  - 매장 정보 : 매장 이름, 위치, 상세설명
  - 예약 관리 


### 포스트맨 403 에러 
    - 로그인 시 생성된 JWT 토큰을 Header -> AUTH -> Bear token 에 삽입 후 재요청 보내기 