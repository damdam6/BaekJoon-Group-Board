<template>
  <div v-if="store.isLoading">로딩중</div>
  <div v-else>
    <!-- from-blue-900 via-indigo-800 to-indigo-500 bg-gradient-to-br -->
    <div class="h-screen bg-black">
      <div class="pt-5 pl-5 flex">
        <p class="border-2 border-white flex p-5 w-fit rounded items-center">
          <img
          :src="store.loginUser.profileImageUrl"
          class="rounded-lg w-10 h-10"
        />
        <p class="pl-2 font-bold text-center text-white">{{ store.loginUser.handle }}</p>
        </p>
      </div>
      <div class="py-12 flex items-center justify-center">
        <div
          v-if="!store.groupList.length"
          class="flex items-center justify-center grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6"
        >
          <EmptyGroupBox />
        </div>
        <div
          v-else
          class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6"
        >
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
