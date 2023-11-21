import { ref, computed } from 'vue'
import { defineStore } from 'pinia'


export const dbStore = defineStore('allData', () => {

  const fullObject = ref({})  

  const userList = computed(() => fullObject.value.users)
  const userMap = computed(() => {
    return new Map(userList.value.map(item => [item.user_id,item]));
  });
  const top100problemList = computed(() => fullObject.value.top100problems)
  const userTierproblemList = computed(() => fullObject.value.userTierProblems)
  const recomProblemList = computed(() => fullObject.value.recomProblems)
  const algorithmList = computed(() => fullObject.value.algorithms)
  const algorithmMap = computed(() => {
    return new Map(algorithmList.value.map(item => [item.problem_num, item.algorithm]));
  });
  const userTop100problemList = computed( () => fullObject.value.userTop100problems
  )
  
  return {fullObject,
  userMap, top100problemList, userTierproblemList, recomProblemList, algorithmMap, userTop100problemList}
})