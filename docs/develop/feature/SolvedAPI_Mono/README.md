# feature/ProblemService

### Mono / Flux : 비동기 처리해주는 객체

- block() : 동기적 처리
- subscribe() : mono 처리 후 처리

- JsonNode로 받아오기 (단, Mono임)
  -> 비동기 처리 관련 로직 해야됨.

만약 ~이면 처리 on을 해야되는데,,, subscribe를 해야되나?

- Mono<JsonNode> 받아오는 util 선언
- Json을 잘라주는 조건 -> 매개 변수로
- 자른 Json을 Mapping해주는 로직

<받아와야 하는 API>

1. 각 유저별 상위 100 문제
2. 티어 별 문제수
3. 사용자 정보
4. 각 사용자의 티어에 따른 문제들... -> tier_problem domain

## 1. 각 유저별 상위 100 문제

### (1) 사용자 별 상위 100문제 page X 2 - 티어 순서대로 불러오기

- Api 호출 함수 (return : Mono<JsonNode>) - UtilConfig 선언
  - api에 넣어야되는 query를 매개 변수로 받음
-

-- Flux 사용하기

```
    public Flux<YourResponseType> fetchUserData(String userId) {
        String url = "http://example.com/api/user/" + userId; // 사용자별 URL 생성
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(YourResponseType.class); // YourResponseType는 API 응답에 맞게 정의
    }

    public void processUserList(List<String> userIds) {
        Flux.fromIterable(userIds)
            .flatMap(this::fetchUserData) // 각 사용자 ID에 대해 API 호출 수행
            .subscribe(
                data -> {
                    // 각 API 호출 결과 처리
                },
                error -> {
                    // 에러 처리
                },
                () -> {
                    // 모든 API 호출이 완료된 후 실행될 로직
                }
            );
    }
```

### (2) 모든 사용자의 문제 중 상위 100문제를 골라오기

### (3) 상위 100문제의 알고리즘을 -> 알고리즘 테이블에 저장

### (4) 상위 100문제를 Problem 테이블에 저장
