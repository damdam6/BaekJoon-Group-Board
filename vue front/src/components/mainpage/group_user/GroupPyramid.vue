<template>
    <div class="flex flex-col items-center mt-8">
        <div v-for="level in 14" :key="level" class="flex justify-center mb-2 space-x-2">
            <div v-for="box in level" :key="level + '-' + box">
                <a :href="getBoxLink(level, box)" target="_blank">
                    <div :class="[getBoxClass(level, box), getBoxSize(level)]">
                    </div>
                </a>
            </div>
        </div>
    </div>
</template>

<script>
import { mainApiStore } from '@/stores/main-api';
import { selectedUserStore } from '@/stores/userInfo';

export default {
    setup() {
        const mainApiStoreInst = mainApiStore();
        const userInfoInst = selectedUserStore();

        function getBoxClass(level, box) {
            const idx = level * (level - 1) / 2 + box - 1;
            const problemNum = mainApiStoreInst.top100problemList[idx].problemId;
            const getUserId = userInfoInst.userId;
            // userInfo의 problemNum 목록에 현재 problemNum이 있는지 확인합니다.
            const isSolved = getUserId && mainApiStoreInst.setUserTop100[getUserId].includes(problemNum);
            console.log(isSolved)
            return isSolved ? 'bg-red-500' : 'bg-blue-500';
        }

        function getBoxSize(level) {
            // 레벨에 따라 박스 크기를 조정합니다.
            if (level < 5) {
                return 'w-5 h-5 rounded-sm sm:rounded-md md:w-6 md:h-6'; // 20px
            } else if (level === 5) {
                return 'hidden w-5 h-5 rounded-sm sm:rounded-md md:w-6 md:h-6'
            } else {
                return 'w-4 h-4 rounded-sm sm:rounded-md sm:w-4 sm:h-4 md:w-5 md:h-5'; // 16px
            }
        }

        function getBoxLink(level, box) {
            const idx = level * (level - 1) / 2 + box - 1;
            const problemNum = mainApiStoreInst.top100problemList[idx].problemId;
            return `https://www.acmicpc.net/problem/${problemNum}`
        }

        return { mainApiStoreInst, userInfoInst, getBoxClass, getBoxSize, getBoxLink };
    }
};
</script>
