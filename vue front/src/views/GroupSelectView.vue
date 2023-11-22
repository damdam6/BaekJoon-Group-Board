<template>
  <div>
    <div>{{ store.groupList }}</div>
    <div
      class="bg-gray-100 min-h-screen py-12 flex items-center justify-center"
    >
      <div
        v-if="0"
        class="flex items-center justify-center grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6"
      >
        <EmptyGroupBox />
      </div>
      <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <GroupBox
          v-for="group in store.groupList"
          :key="group.id"
          :groupInfo="group"
          :userList="store.groupUserMap.get(group.id)"
        />
        <!-- 그룹길이가 2개이하라면 -->
        <EmptyGroupBox v-if="1" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { groupStore } from "@/stores/group";
import GroupBox from "../components/grouppage/GroupBox.vue";
import EmptyGroupBox from "../components/grouppage/EmptyGroupBox.vue";

const store = groupStore();

onMounted(() => {
  store.fetchGroupList();
});
</script>

<style scoped></style>
