### 메인 페이지 -> 추천 문제 파트

#### ProblemController

1. 추천 문제 가져오기 (O)

2. 추천 문제 등록 (O)

3. 추천 문제 삭제

   - 시간을 48시간? 지나면 자동으로 삭제하게 할 수 있나?
   - 일괄적으로? 문제별로?
   - recomProblem dto에 등록 시간을 넣어줘야 하나?
   - 그냥 지정 시간에 하면 어떤가?

   ```java
   import org.springframework.scheduling.annotation.Scheduled;
   import org.springframework.stereotype.Component;

   @Component
   public class MyScheduledTask {

   @Scheduled(cron = "0 0 12 * * ?") // 매일 정각 12시에 실행
   public void runTaskAtMidday() {
       // 실행될 코드
       System.out.println("Task executed at midday!");
   }

   @Scheduled(cron = "0 0 0 ? * SAT") // 매주 토요일 00:00에 실행
   public void runTaskOnSaturday() {
       // 실행될 코드
       System.out.println("Task executed on Saturday!");
   }

   }
   ```
