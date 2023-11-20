### 메인 페이지 -> 추천 문제 파트

#### ProblemController

1. 추천 문제 가져오기 (O)

2. 추천 문제 등록 (O)
    - 등록할 때 10개가 넘으면 제일 먼저 등록된 문제 (id별로 정렬하여) 삭제

3. 추천 문제 삭제 (O)
   - 시간을 48시간? 지나면 자동으로 삭제하게 할 수 있나? > 하지 않고 추가될 때 10개가 넘으면 삭제되는걸로
   - 그룹 삭제시 추천문제도 자동 삭제되도록 구현


   <!-- - 일괄적으로? 문제별로?
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
   ``` -->
