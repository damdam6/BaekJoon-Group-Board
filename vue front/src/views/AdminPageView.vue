<template>
  <div
    class="w-full h-full from-blue-900 via-indigo-800 to-indigo-500 bg-gradient-to-br p-8"
  >
    <div class="bg-white p-3 max-w-md mx-auto">
      <div class="text-center">
        <h1 class="m-2 text-xl font-bold">
          {{ store.selectedGroup.groupName }} Admin Page
        </h1>
        <h3 class="m-2 text-2xl font-bold">UserList</h3>
      </div>
      <div class="mt-3">
        <ul>
          <li
            v-for="user in store.allUserList"
            :key="user.userId"
            class="p-1 rounded-lg"
          >
            <div class="flex align-middle flex-row justify-between">
              <div class="p-1">
                <p class="text-neutral-950">
                  {{ user.handle }}
                </p>
              </div>

              <button
                v-if="store.groupUserList.some((u) => u.userId === user.userId)"
                @click="store.removeUser(user.userId)"
                class="flex text-red-500 border-2 border-red-500 p-2 rounded-lg"
              >
                <svg
                  class="h-6 w-6 text-red-500"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <circle cx="12" cy="12" r="10" />
                  <line x1="15" y1="9" x2="9" y2="15" />
                  <line x1="9" y1="9" x2="15" y2="15" />
                </svg>
                <span> Remove</span>
              </button>
              <button
                v-else
                @click="store.addUser(user.userId)"
                class="ml-2 border-2 border-green-500 p-2 text-green-500 hover:text-white hover:bg-green-500 rounded-lg flex"
              >
                <svg
                  class="h-6 w-6"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                  stroke-width="2"
                  stroke="currentColor"
                  fill="none"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <path stroke="none" d="M0 0h24v24H0z" />
                  <circle cx="12" cy="12" r="9" />
                  <line x1="9" y1="12" x2="15" y2="12" />
                  <line x1="12" y1="9" x2="12" y2="15" />
                </svg>
                <span>Add</span>
              </button>
            </div>
            <hr class="mt-2" />
          </li>
        </ul>
      </div>
      <div class="mt-8">
        <button
          @click="store.deleteGroup()"
          class="border-2 border-red-500 p-2 text-red-500"
        >
          Remove Group
        </button>
        <button
          @click="returnToGroupPage"
          class="border-2 border-indigo-500 p-2 text-indigo-500 ml-4"
        >
          Go Back
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { adminStore } from "@/stores/adminInfo";
import { useRouter } from "vue-router";

const store = adminStore();
const router = useRouter();

const returnToGroupPage = () => {
  router.push({ name: "group" });
};

onMounted(() => {
  store.reset();
});
</script>

<style scoped></style>
