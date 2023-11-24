### 로그인 페이지

#### UserGroupController

1. 로그인 (추가 구현 필요)

   - 쿠키 / 세션을 사용하여 로그인 구현
   - 쿠키 수명 : 24시간

   ```java
   @Component
    public class SessionManager {

        // 쿠키를 쓸 곳이 많기 때문에 상수로 만듦
        public static final String SESSION_COOKIE_NAME = "mySessionId";

        // 스프링 아이디와 객체를 맵으로 저장
        // 동시성을 위해 ConcurrentHashMap<>() 사용
        private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

        public void createSession(Object value, HttpServletResponse response) {

            // 세션 id를 생성하고, 값을 세션에 저장
            // randomUUID() : 확실한 랜덤값을 얻을 수 있음. 자바가 제공
            String sessionId = UUID.randomUUID().toString();
            sessionStore.put(sessionId, value);

            // 쿠키 생성
            Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
            mySessionCookie.setPath("/"); // 모든 경로에서 접근 가능하도록 설정
            mySessionCookie.setMaxAge(3600 * 24); // 24시간으로 쿠키 수명 설정
            response.addCookie(mySessionCookie);
        }

        public Object getSession(HttpServletRequest request) {
            // 쿠기를 찾음
            Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
            if (sessionCookie == null) {
                return null;
            }
            return sessionStore.get(sessionCookie.getValue());
        }

        public void expire(HttpServletRequest request) {
            Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
            if (sessionCookie != null) {
                // 만료된 쿠키를 지움
                sessionStore.remove(sessionCookie.getValue());
            }
        }

        public Cookie findCookie(HttpServletRequest request, String cookieName) {
            if (request.getCookies() == null) {
                return null;
            }
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findAny()
                    .orElse(null);
        }

    }
   ```

    * cors 권한 부여
    ```java

    // WebConfig
    
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("https://solved.ac/api/v3", "http://localhost:5173")
				.allowedMethods("GET", "POST").allowCredentials(true); // 쿠키 인증 요청 허용
	}

    ``` 


   * getUser -> addUser
   * 아이디를 입력시 해당 유저가 DB에 없으면 유저 등록 여부 확인 : addUser
   * 아이디를 입력시 해당 유저가 DB에 있으면 해당 유저 반환 (O) : getUser

2. 유저 등록 (Domain에서 해결, UserAddReloadDomain)

   - Solved.API 필요
   - 작동로직
   - user 수 N 일 시
     - 최소 : 4 회 호출,
     - 최대 : 3 + 30 (Tier가 모두 다른 페이지에 존재하는 경우 -> 불가능, 최대 7개 이하로 가져옴)

   1. 유저 정보 업데이트 (O)

      - API 1회 호출
      - DTO : User

   2. top 100 문제 및 관련 알고리즘 업데이트(O)

      - API 1회 호출
      - DTO : Problem, Algorithm, ProblemAndAlgoObjectDomain

   3. userTierProblem 관련 (O)
      1. userTier 정보 업데이트
         - API 1회 호출
         - DTO : UserTier
      2. Tier에 맞는 푼 문제 1개 가져오기
         - API Tier당 속한 페이지의 갯수만큼
         - DTO : Problem, UserPageNoObjectDomain

### 유저 그룹 선택 페이지

#### UserGroupController

2. 유저 등록된 그룹 가져오기 (O)

   - 로그인된 유저에 등록된 그룹 리스트 제공 : getGroups
   - 없을시 null 반환

3. 그룹 선택하기 (백에서 구현할 필요 없을듯)

4. 그룹 생성 & 유저-그룹 관계 등록 (O)

   - addGroup
   - 새로운 그룹 생성 -> 그룹 생성 성공시 로그인된 유저와 그룹 관계 등록

5. 유저 그룹 탈퇴 (유저-그룹 관계 삭제) (O)
   - leaveGroup
   - 유저 -> 그룹 관계 삭제
   - 삭제되고 다시 2번 호출해야
