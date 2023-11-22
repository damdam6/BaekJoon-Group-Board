import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { dbStore } from '../stores/db'

export const selectedUserStore= defineStore('pickUser', () => {
  const userId = ref(0);
  const userName = computed(() => 'temp')
  const userTier = ref(0)
  const userRank = ref(0)
  const userSolvedCnt = ref(0)
  const userRecomPro = {
    proNum : ref(0),
    proTitle : ref(null),
    proAlgo :ref([])
  }


  return { userName, userTier, userRank, userSolvedCnt, userRecomPro }
})