<template>
  <div
    class="bg-white rounded-lg overflow-hidden shadow-lg transition-transform transform hover:scale-105"
  >
    <div class="p-1 bg-blue-200"></div>
    <div class="p-8">
      <h2 class="text-3xl font-bold text-gray-800 mb-4">
        {{ p.groupInfo.groupName }}
      </h2>
      <p class="text-gray-600 mb-6">{{ p.groupInfo }}</p>
      <!-- <p class="text-4xl font-bold text-gray-800 mb-6">$19.99</p> -->
      <ul class="text-sm text-gray-600 mb-6">
        <li class="mb-2 flex items-center">
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
        <li class="mb-2 flex items-center">
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
      <button
        class="w-full bg-blue-500 text-white rounded-full px-4 py-2 hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue active:bg-blue-800"
      >
        Enter
      </button>
      <button
        @click="moveToAdminLogin()"
        class="bg-orange-500 text-white rounded-full px-4 py-2 hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue active:bg-blue-800"
      >
        admin
      </button>
      <button
        @click="leaveGroup()"
        class="bg-red-500 text-white rounded-full px-4 py-2 hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue active:bg-blue-800"
      >
        leave
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
  if (p.userList != undefined) return p.userList.value.length;
  return 0;
});

const moveToAdminLogin = () => {
  router.push({ name: "adminPassword" });
};

const leaveGroup = () => {
  console.log(p.groupInfo);
  store.leaveGroup(p.groupInfo["id"]);
  router.push({ name: "group" });
};

const enterMainPage = () => {
  router.push({ name: "adminPassword" });
};
</script>

<style scoped></style>
