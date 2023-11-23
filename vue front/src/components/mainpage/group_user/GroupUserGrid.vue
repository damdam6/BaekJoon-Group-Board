<template>
    <div class="flex grid items-center justify-center grid-cols-10 gap-3">
        <!-- UserTile 컴포넌트를 사용하여 사용자 목록을 표시 -->
        <UserTile v-for="user in mainApiStoreInst.userList" :key="user.userId"
            :src="user.profileImageUrl || 'https://static.solved.ac/misc/360x360/default_profile.png'"
            :alt="user.userName || 'Default Name'" @onTileClick="handleTileClick(user.userId)"
            @mouseover="(event) => handleMouseOver(user.userName, event)" @mouseleave="handleMouseLeave" />

        <!-- MiniProfile 컴포넌트가 조건부로 렌더링되며 사용자 이름과 위치를 표시 -->
        <MiniProfile v-if="showMiniProfile" :userName="currentUserName"
            :style="{ top: `${mouseY}px`, left: `${mouseX}px` }" />
    </div>
</template>

<script setup>
import { ref } from 'vue';
import UserTile from '@/components/items/UserTile.vue';
import MiniProfile from '@/components/items/MiniProfile.vue';

// Pinia 스토어를 사용하는 경우 (예시)
import { mainApiStore } from '@/stores/main-api';
import { selectedUserStore } from '@/stores/userInfo';

const mainApiStoreInst = mainApiStore();
const userInfoInst = selectedUserStore();

const showMiniProfile = ref(false);
const currentUserName = ref('');
const mouseX = ref(0);
const mouseY = ref(0);

const handleMouseOver = (userName, event) => {
    currentUserName.value = userName;
    mouseX.value = event.clientX;
    console.log(event.clientX)
    mouseY.value = event.clientY;
    showMiniProfile.value = true;
};

const handleMouseLeave = () => {
    showMiniProfile.value = false;
};

const handleTileClick = (userId) => {
    userInfoInst.userId = userId;
};
</script>
