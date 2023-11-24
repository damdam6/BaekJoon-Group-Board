## DB 정보
### 주요 테이블
### 1. Users
- 유저 정보
- api (https://solved.ac/api/v3/user/show) : 유저 정보

- api(https://solved.ac/api/v3/search/problem/?query=@bmike0413&sort=level&direction=desc&page=2) : 

### 2. Problem
- 문제 정보

### 3. Group
- 그룹 정보


### 서브 테이블
#### tier_problem (users)
- 유저별 티어별로 푼 문제 수

#### user_group (users, group)
- 유저와 그룹 테이블 연동

#### problem_algorighm (problem)
- 문제별 알고리즘 정보
- 알고리즘은 배열형태의 string으로 저장하자 (전처리, 후처리)

#### recom_problem (group)
- 그룹별 사용자 직접 등록 추천 문제 정보

#### user_tier_problem (users) (동적)
- 모든 유저의 티어별 문제 1개