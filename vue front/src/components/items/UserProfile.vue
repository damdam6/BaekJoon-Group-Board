<template>
    <div class="relative w-64 px-6 py-6 my-4 bg-white shadow-xl rounded-3xl">
        <div class="absolute flex items-center px-1 py-1 text-white bg-pink-500 rounded-full shadow-xl left-4 -top-6">
            <img class="object-cover w-12 h-12 rounded-full"
                :src="userInfoInst.userProfileImg || 'https://static.solved.ac/misc/360x360/default_profile.png'" />
        </div>

        <div class="mt-8">
            <p class="my-2 text-xl font-semibold">{{ userInfoInst.userName }}</p>
            <div class="flex space-x-2 text-sm text-gray-400">
                <img :src="`https://static.solved.ac/tier_small/${userInfoInst.userTier}.svg`" class="w-6 h-6">
                <p>{{ rankMap[userInfoInst.userTier] }}</p>
            </div>
            <div class="flex my-3 space-x-2 text-sm text-gray-400">
                <img :src="userInfoInst.userProfileImg || 'https://static.solved.ac/misc/360x360/default_profile.png'"
                    class="w-5 h-5" />
                <p>1 Weeks Left</p>
            </div>
            <div class="border-t-2"></div>

            <div class="flex justify-between">
                <div class="my-2">
                    <p class="mb-2 text-sm font-semibold">Group Rank</p>
                    <div class="text-base font-semibold text-gray-400">
                        <div class="flex space-x-2">
                            <p>{{ userInfoInst.groupRank }} + 100</p>
                        </div>
                    </div>
                </div>
                <div class="my-2">
                    <p class="mb-2 text-sm font-semibold">SolvedAC Rank</p>
                    <div class="text-base font-semibold text-gray-400">
                        <div class="flex space-x-2">
                            <p>{{ userInfoInst.userRank }}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>

import { selectedUserStore } from '@/stores/userInfo'

const userInfoInst = selectedUserStore();


const rankMap = {
    1: 'Bronze V',
    2: 'Bronze IV',
    3: 'Bronze III',
    4: 'Bronze II',
    5: 'Bronze I',
    6: 'Silver V',
    7: 'Silver IV',
    8: 'Silver III',
    9: 'Silver II',
    10: 'Silver I',
    11: 'Gold V',
    12: 'Gold IV',
    13: 'Gold III',
    14: 'Gold II',
    15: 'Gold I',
    16: 'Platinum V',
    17: 'Platinum IV',
    18: 'Platinum III',
    19: 'Platinum II',
    20: 'Platinum I',
    21: 'Diamond V',
    22: 'Diamond IV',
    23: 'Diamond III',
    24: 'Diamond II',
    25: 'Diamond I',
    26: 'Ruby V',
    27: 'Ruby IV',
    28: 'Ruby III',
    29: 'Ruby II',
    30: 'Ruby I',
    31: 'Master'
};


</script>