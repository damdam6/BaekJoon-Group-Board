<template>
  <div
    class="m-5 bg-white rounded-lg overflow-hidden shadow-lg transition-transform transform hover:scale-105 flex flex-col"
  >
    <div class="p-1 bg-slate-600"></div>
    <div class="p-8">
      <h2 class="text-3xl font-bold text-slate-800 mb-4">
        {{ p.groupInfo.groupName }}
      </h2>
      <ul class="text-sm text-gray-600 mb-6 grid grid-cols-3 gap-2">
        <li class="mb-2 flex items-center text-l font-bold text-slate-950">
          <svg
            class="w-4 h-4 mr-2 text-green-500"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M5 13l4 4L19 7"
            ></path>
          </svg>
          {{ userCount }} Users
        </li>
        <li
          class="mb-2 flex items-center"
          v-for="user in p.userList"
          :key="user.userId"
        >
          <img :src="user.profileImageUrl" class="rounded-lg w-5 h-5" />
          <div class="pl-2 text-sm">{{ user.handle }}</div>
        </li>
      </ul>
    </div>
    <div class="p-4 flex flex-col justify-end mt-auto">
      <div class="flex justify-items-end">
        <button
          @click="moveToAdminLogin()"
          class="m-3 ml-0 bg-orange-500 text-white rounded px-4 py-2 hover:bg-orange-400 focus:outline-none focus:shadow-outline-blue active:bg-orange-800"
        >
          admin
        </button>
        <button
          @click="leaveGroup()"
          class="m-3 ml-0 bg-red-500 text-white rounded px-4 py-2 hover:bg-red-400 focus:outline-none focus:shadow-outline-blue active:bg-red-800"
        >
          leave
        </button>
      </div>
      <button
        @click="enterMainPage()"
        class="py-4 p-3 w-full bg-black text-white text-xl rounded px-4 py-2 hover:bg-slate-800 focus:outline-none focus:shadow-outline-blue active:bg-slate-400"
      >
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
  store.selectedGroup = p.groupInfo;
  router.push({
    name: "about",
  });
};
</script>

<style scoped></style>
