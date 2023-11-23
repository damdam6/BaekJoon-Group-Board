<template>
  <div v-if="store.isLoading">로딩중</div>
  <div v-else>
    <div
      class="from-blue-900 via-indigo-800 to-indigo-500 bg-gradient-to-br min-h-screen py-12 flex items-center justify-center"
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
          :userList="store.groupUserMap[group.id]"
        />
        <!-- 그룹길이가 2개이하라면 -->
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
