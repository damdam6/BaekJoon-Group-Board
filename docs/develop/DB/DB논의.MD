
[userId]
- groupName (max 3)

- api : 푼 전체 문제
    - key : count
    - key : problemId
    - key : level (티어)
    - key : tags : [displayNames : [language : ko & name : (알고리즘)]]
    https://solved.ac/api/v3/search/problem?query=@end24&sort=level&direction=desc
    - 상위 100문제

- api : user-search
    - key : tier
    - key : rank
    - key : profileImgUrl




[group] - 한 그룹

- groupName
- password

- userList

유저 개인별 필요
-----
- 상위 100문제
    - 사용자 별 푼 문제
    - 문제 정보 - link / num : 연결할 수 있도록
    - 프로필 이미지 link
    - 

- 유저 프로필
    - 특정 유저의 알고리즘 풀이 정보 
    - 푼 문제수
    - 비교를 위한 rank

- 문제 추천 조건
    - 지금 로그인한 사람이 풀지 않은 문제  && 티어가 유사한 문제
    - 클릭한 유저가 푼 문제
-----


- 추천pro (등록)
- 그룹 내 유저 랭킹 (그룹 통계) -> 유저별 접근 가능해야됨
- 가장 많이 푼 문제 / 가장 적게 푼 문제


