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

  return {
    rankTop3
  };
});
