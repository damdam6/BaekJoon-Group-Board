<template>
  <div class="overflow-hidden transition-transform transform bg-white rounded-lg shadow-lg hover:scale-105">
    <div class="p-1 bg-blue-200"></div>
    <div class="p-8">
      <h2 class="mb-4 text-3xl font-bold text-gray-800">
        {{ p.groupInfo.groupName }}
      </h2>
      <p class="mb-6 text-gray-600">{{ p.groupInfo }}</p>
      <!-- <p class="mb-6 text-4xl font-bold text-gray-800">$19.99</p> -->
      <ul class="mb-6 text-sm text-gray-600">
        <li class="flex items-center mb-2">
          <svg class="w-4 h-4 mr-2 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
          </svg>
          {{ userCount }} Users
        </li>
        <li class="flex items-center mb-2">
          <svg class="w-4 h-4 mr-2 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
          </svg>
          {{ p.userList }}
        </li>
        <!-- <li class="flex items-center">
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
          24/7 Support
        </li> -->
      </ul>
    </div>
    <div class="p-4">
      <!-- <button
      @click="enterMainPage()"
            id="regist-btn"
            class="w-full py-4 font-bold bg-indigo-800 rounded text-blue-50 hover:bg-blue-700"
          > -->

      <div class="flex justify-items-end">
        <button @click="moveToAdminLogin()"
          class="px-4 py-2 m-3 text-white bg-orange-500 rounded-full hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue active:bg-blue-800">
          admin
        </button>
        <button @click="leaveGroup()"
          class="px-4 py-2 m-3 text-white bg-red-500 rounded-full hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue active:bg-blue-800">
          leave
        </button>
      </div>
      <button @click="enterMainPage()"
        class="w-full p-3 px-4 py-2 py-4 text-xl text-white bg-indigo-800 rounded hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue active:bg-blue-800">
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
  if (p.userList !== undefined) return p.userList.length;
  return 0;
});

const moveToAdminLogin = () => {
  router.push({ name: "adminPassword" });
};

const leaveGroup = () => {
  console.log(p.groupInfo);
  store.leaveGroup(p.groupInfo.id);
  router.push({ name: "group" });
};

// const groupId = computed(() => {
//   if (!p.groupInfo) return 0;
//   return;
// });

const enterMainPage = () => {
  console.log(p.groupInfo.id);
  router.push({
    name: "about",
    params: { groupId: p.groupInfo.id },
  });
};
</script>

<style scoped></style>
