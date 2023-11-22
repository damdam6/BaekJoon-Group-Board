import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

import { mainApiStore } from '@/stores/main-api';
import { fixedBoxStore } from '@/stores/fixedbox-db';

export const selectedUserStore= defineStore('pickUser', () => {
  const mainApiStoreInst = mainApiStore()
  const userId = ref(0);

  const userName = computed(() => {
    return mainApiStoreInst.userMap.get(userId)[userName]
  })

  const userTier = computed(() => {
    console.log('test')
    console.log(mainApiStoreInst.userMap)
    if(!mainApiStoreInst.userMap)return ''
    
    return mainApiStoreInst.userMap.get(userId)[userName]
  }

  )
  const userRank = ref(0)
  const userSolvedCnt = ref(0)


  return { userId, userName, userTier, userRank, userSolvedCnt}
})