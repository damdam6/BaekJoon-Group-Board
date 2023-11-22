import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';

export const mainApiStore = defineStore('allData', () => {
  const fullObject = ref({'key':'value'}) 
  const isLoading = ref(true);
  const fetchData = async () => {
    isLoading.value = true;
    try {
       const id = 1; // 아이디 설정할 것
       axios({
          url: `http://localhost:8080/api/main/group/${id}`,
          method: "GET",
          withCredentials: true,
       }).then((response) => {
          fullObject.value = {...response.data};
          isLoading.value = false;
          console.log(fullObject.value)
       });
    } catch (err) {
      console.log(err.message);
      isLoading.value = false;
    }
 };

 const userList = computed(() => {
  if (fullObject.value && fullObject.value.users) {
    return Object.values(fullObject.value.users);
  }

  return []; // fullObject.value.users가 없으면 빈 배열 반환
});
const userMap = computed(() => {
  if (userList.value && userList.value.length > 0) {
    return new Map(userList.value.map(item => [item.userId, item]));
  }
  return new Map(); // userList.value가 없으면 빈 Map 반환
});

const algorithmList = computed(() => {
  if (fullObject.value && fullObject.value.algorithms) {
    return fullObject.value.algorithms;
  }
  return []; 
})
const algorithmMap = computed(() => {
  if(!algorithmList.value)return {};
  return new Map(algorithmList.value.map(item => [item.problemNum, item.algorithm]));
});

  const top100problemList = computed(() =>{
   return fullObject.value.top100problems})
  const userTierProblemList = computed(() => fullObject.value.userTierProblems)
  const recomProblemList = computed(() => fullObject.value.recomProblems)
 
  const userTop100problemList = computed( () => fullObject.value.userTop100problems
  )
  
  return {fetchData,fullObject, userList, isLoading,
  userMap, top100problemList, userTierProblemList, recomProblemList, algorithmMap, userTop100problemList}
})