import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { mainApiStore } from '@/stores/main-api';

export const fixedBoxStore = defineStore('fixedBoxData', () => {
  const mainApiStoreInst = mainApiStore();

  const rankTop3 = computed(() => {
    
    if (!mainApiStoreInst.userList || mainApiStoreInst.userList.length === 0) {
      return [];
    }
    const sortedList = [...mainApiStoreInst.userList].sort((a, b) => a.rank - b.rank);
    return sortedList.slice(0,3);
  });

  const recomProAlgoObject = computed(() => {
    let recomObject = {};
    if(mainApiStoreInst.recomProblemList.length === 0){
      recomObject = mainApiStoreInst.userTierProblemList;
    }else{
      recomObject = mainApiStoreInst.recomProblemList;
    }

    let recomProAndAlgo = {};
    recomObject.forEach((value, key) => {
      const proNum = value.problemId;

      const algo= mainApiStoreInst.algorithmMap.get(proNum);
      const algorithmArr = algo.split(" ");
      recomProAndAlgo[key] = { ...value, algorithm: algorithmArr };
    });
    console.log(typeof(recomProAndAlgo))
    console.log(recomProAndAlgo)
    return recomProAndAlgo;
  }

  )

  return {
    rankTop3, recomProAlgoObject
  };




});
