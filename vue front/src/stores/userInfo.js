import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

import { mainApiStore } from '@/stores/main-api';
import { fixedBoxStore } from '@/stores/fixedbox-db';

export const selectedUserStore= defineStore('pickUser', () => {
  const mainApiStoreInst = mainApiStore()
  const userId = ref(0);

  const getUserMap = computed(() => 
  mainApiStoreInst.userMap.get(userId.value));

  const userName = computed(() => {
    const user = getUserMap.value;
    return user ? user.userName : '';
  });

  const userTier = computed(() => {
    const user = getUserMap.value;
    return user ? user.tier : '';
  });

  const userRank = computed(() => {
    const user = getUserMap.value;
    return user ? user.rank : 0;
  });

  const userSolvedCnt = computed(() => {
    const user = getUserMap.value;
    return user ? user.solvedCnt : 0;
  });

  return { userId, userName, userTier, userRank, userSolvedCnt };

})