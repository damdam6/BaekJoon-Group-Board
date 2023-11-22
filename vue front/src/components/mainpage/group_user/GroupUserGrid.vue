<template>
    <div class="flex grid items-center justify-center grid-cols-10 gap-3">
        <UserTile v-for="user in mainApiStoreInst.userList" :key="user.userId"
            :src="user.profileImageUrl || 'https://static.solved.ac/misc/360x360/default_profile.png'"
            :alt="user.userName || 'Default Name'" @onTileClick="handleTileClick(user.userId)" :userNum="user.userId" />
    </div>
</template>

<script>
import { mainApiStore } from '@/stores/main-api';
import { selectedUserStore } from '@/stores/userInfo';
import { fixedBoxStore } from '@/stores/fixedbox-db'; // Pinia 스토어 임포트
import UserTile from '@/components/items/UserTile.vue';

export default {
    components: {
        UserTile
    },
    setup() {
        const mainApiStoreInst = mainApiStore()
        const fixedBoxInst = fixedBoxStore();
        const userInfoInst = selectedUserStore();

        const handleTileClick = (userId) => {
            console.log(userId)
            userInfoInst.userId = userId;
        };

        return { mainApiStoreInst, fixedBoxInst, userInfoInst, handleTileClick };
    }
}
</script>
