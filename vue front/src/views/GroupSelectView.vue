<template>
  <div>
    <div>{{ store.groupList }}</div>
    <div>{{ store.groupList.length }}</div>
    <div
      class="bg-gray-100 min-h-screen py-12 flex items-center justify-center"
    >
      <div
        v-if="!store.groupList.length"
        class="flex items-center justify-center grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6"
      >
        <div>그룹 정보가 없음</div>
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
        <EmptyGroupBox v-if="store.groupList.length <= 2" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { groupStore } from "@/stores/group";
import GroupBox from "../components/grouppage/GroupBox.vue";
import EmptyGroupBox from "../components/grouppage/EmptyGroupBox.vue";

const store = groupStore();

// const userList = computed((id) => {
//   if (store.groupUserMap.value.get(id) != undefined)
//     return store.groupUserMap.get(id);
//   return ref([]);
// });

onMounted(() => {
  store.fetchGroupList();
});
</script>

<style scoped></style>
