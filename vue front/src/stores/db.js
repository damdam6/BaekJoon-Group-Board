import { ref, computed } from 'vue'
import { defineStore } from 'pinia'


export const dbStore = defineStore('allData', () => {

  const fullObject = ref({'key':'key'})  

  const userList = computed(() => {
    // fullObject.value.users가 존재하는지 확인
    console.log(fullObject)
    if (fullObject.value && fullObject.value.users) {
      return fullObject.value.users;
    }
    console.log('없다고 뜸')
    return ['뭔데']; // fullObject.value.users가 없으면 빈 배열 반환
  });
  
  const userMap = computed(() => {
    // userList.value가 존재하는지 확인
    if (userList.value && userList.value.length > 0) {
      return new Map(userList.value.map(item => [item.userId, item])); // 'userId' 속성 사용
    }
    return new Map(); // userList.value가 없으면 빈 Map 반환
  });
  const top100problemList = computed(() => fullObject.value.top100problems)
  const userTierproblemList = computed(() => fullObject.value.userTierProblems)
  const recomProblemList = computed(() => fullObject.value.recomProblems)
  const algorithmList = computed(() => fullObject.value.algorithms)
  const algorithmMap = computed(() => {
    if(!algorithmList.value)return {};
    return new Map(algorithmList.value.map(item => [item.problem_num, item.algorithm]));
  });
  const userTop100problemList = computed( () => fullObject.value.userTop100problems
  )
  
  return {fullObject, userList,
  userMap, top100problemList, userTierproblemList, recomProblemList, algorithmMap, userTop100problemList}
})