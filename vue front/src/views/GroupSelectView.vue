<template>
  <div v-if="store.isLoading">로딩중</div>
  <div v-else>
    <div class="flex items-center justify-center min-h-screen py-12 from-black via-black to-gray-950 bg-gradient-to-br">
      <div v-if="!store.groupList.length"
        class="flex grid items-center justify-center grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <EmptyGroupBox />
      </div>
      <div v-else class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <GroupBox v-for="group in store.groupList" :key="group.id" :groupInfo="group"
          :userList="store.groupUserMap[group.id]" />
        <EmptyGroupBox v-if="store.groupList.length <= 2" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { groupStore } from "@/stores/group";
import GroupBox from "../components/grouppage/GroupBox.vue";
import EmptyGroupBox from "../components/grouppage/EmptyGroupBox.vue";

const store = groupStore();

onMounted(() => {
  store.fetchGroupList();
});
</script>

<style scoped></style>
