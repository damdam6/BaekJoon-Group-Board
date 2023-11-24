<template>
  <div class="overflow-hidden transition-transform transform border border-white rounded-lg shadow-lg hover:scale-105">
    <div class="p-1 bg-blue-200 bg-opacity-30"></div>
    <div class="p-8">
      <h2 class="mb-4 text-3xl font-bold text-white">
        {{ p.groupInfo.groupName }}
      </h2>
      <ul class="mb-6 text-sm text-gray-600">
        <li class="flex items-center mt-5 mb-2 font-serif">
          {{ userCount }} Users
        </li>
      </ul>
    </div>
    <div class="p-4">

      <div class="flex justify-items-end">
        <button @click="moveToAdminLogin()"
          class="px-4 py-2 m-3 text-orange-500 border border-orange-500 rounded-full hover:bg-gray-800 focus:outline-none focus:shadow-outline-blue active:bg-blue-800">
          admin
        </button>
        <button @click="leaveGroup()"
          class="px-4 py-2 m-3 text-red-500 border border-red-500 rounded-full hover:bg-gray-800 focus:outline-none focus:shadow-outline-blue active:bg-blue-800">
          leave
        </button>
      </div>
      <button @click="enterMainPage()"
        class="w-full p-3 px-4 py-2 py-4 text-xl text-indigo-800 border border-indigo-800 rounded hover:bg-gray-800 focus:outline-none focus:shadow-outline-blue active:bg-blue-800">
        Enter
      </button>
    </div>
  </div>
</template>

<script setup>
import { storeToRefs } from "pinia";
import { defineProps, computed } from "vue";
import { useRouter } from "vue-router";
import { groupStore } from "@/stores/group";

const store = groupStore();

const router = useRouter();
const p = defineProps({
  groupInfo: Object,
  userList: Object,
});

const userCount = computed(() => {
  if (p.userList != undefined) return p.userList.length;
  return 0;
});

const moveToAdminLogin = () => {
  store.selectedGroup = p.groupInfo;
  router.push({
    name: "adminPassword",
  });
};

const leaveGroup = () => {
  store.leaveGroup(p.groupInfo.id);
  router.push({ name: "group" });
};

// const groupId = computed(() => {
//   if (!p.groupInfo) return 0;
//   return;
// });

const enterMainPage = () => {
  console.log(p.groupInfo)
  store.selectedGroup = p.groupInfo;
  console.log(store.selectedGroup)
  router.push({
    name: "about",
  });
};
</script>

<style scoped></style>
