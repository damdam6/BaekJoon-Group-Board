import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';

export const dbStore = defineStore('allData', () => {
  const fullObject = ref({'key':'value'}) 

  const fetchData = async () => {
    try {
       const id = 1; // 아이디 설정할 것
       axios({
          url: `http://localhost:8080/api/main/group/${id}`,
          method: "GET",
          withCredentials: true,
       }).then((response) => {
          fullObject.value = response.data;
       });
    } catch (err) {
      console.log(err.message);
    }
 };

  const userList = computed(() => {
    // fullObject.value.users가 존재하는지 확인
    if (fullObject.value && fullObject.value.users) {
      return fullObject.value.users;
    }
    return []; // fullObject.value.users가 없으면 빈 배열 반환
  });
  
  const userMap = computed(() => {
    // userList.value가 존재하는지 확인
    if (userList.value && userList.value.length > 0) {
      return new Map(userList.value.map(item => [item.userId, item])); // 'userId' 속성 사용
    }
    return new Map(); // userList.value가 없으면 빈 Map 반환
  });
  const top100problemList = computed(() =>{
    console.log('tp100')
   return fullObject.value.top100problems})
  const userTierproblemList = computed(() => fullObject.value.userTierProblems)
  const recomProblemList = computed(() => fullObject.value.recomProblems)
  const algorithmList = computed(() => fullObject.value.algorithms)
  const algorithmMap = computed(() => {
    if(!algorithmList.value)return {};
    return new Map(algorithmList.value.map(item => [item.problemNum, item.algorithm]));
  });
  const userTop100problemList = computed( () => fullObject.value.userTop100problems
  )
  
  return {fetchData,fullObject, 
  userMap, top100problemList, userTierproblemList, recomProblemList, algorithmMap, userTop100problemList}
})