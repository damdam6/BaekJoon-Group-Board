### Reload

#### APIs

```java
    @Scheduled(fixedRate = 1000000)
    public void schedulTask() {
        //유저 정보 업데이트
        List<User> users = userRepository.selectAllUser();
        processUser(users);

        //유저 목록을 사용한 상위 문제 100개 가져오기
        users = userRepository.selectAllUser();
        processProblem(users);
        
        //유저의 티어별 문제 갯수 받아오기
        processUserTier(users);
    }
```

* 작동로직
    * user 수 N 일 시 
        * 최소 : 4N 회 호출, 
        * 최대 : 3N + N * 30 (Tier가 모두 다른 페이지에 존재하는 경우 -> 불가능, 최대 7개 이하로 가져옴)

    1. 유저 정보 업데이트 (O)
        * API USER 수 만큼 호출
        * DTO : User

    2. top 100 문제 및 관련 알고리즘 업데이트(O)
        * API USER 수 만큼 호출
        * DTO : Problem, Algorithm, ProblemAndAlgoObjectDomain

    3. userTierProblem 관련 (O)
        1. userTier 정보 업데이트
            * API USER 수 만큼 호출
            * DTO : UserTier
        2. Tier에 맞는 푼 문제 1개 가져오기
            * API Tier당 속한 페이지의 갯수 * USER 수 만큼 호출
            * DTO : Problem, UserPageNoObjectDomain