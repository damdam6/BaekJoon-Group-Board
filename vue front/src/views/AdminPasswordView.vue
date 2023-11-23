<template>
  <div
    class="h-screen overflow-hidden flex items-center justify-center"
    style="background: #edf2f7"
  >
    <div class="bg-blue-500">
      <div
        class="flex justify-center container mx-auto my-auto w-screen h-screen items-center flex-col"
      >
        <div class="text-slate-100 items-center">
          <svg
            class="w-10 h-10 mx-auto pb-3"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <g>
              <path fill="none" d="M0 0h24v24H0z" />
              <path
                d="M12 14v2a6 6 0 0 0-6 6H4a8 8 0 0 1 8-8zm0-1c-3.315 0-6-2.685-6-6s2.685-6 6-6 6 2.685 6 6-2.685 6-6 6zm0-2c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm9 6h1v5h-8v-5h1v-1a3 3 0 0 1 6 0v1zm-2 0v-1a1 1 0 0 0-2 0v1h2z"
              />
            </g>
          </svg>
          <div class="text-center pb-3 text-3xl">
            {{ selectedGroup.groupName }}
          </div>
        </div>

        <div
          class="w-full md:w-3/4 lg:w-1/2 flex flex-col items-center bg-slate-50 rounded-md pt-12"
        >
          <!-- ID input -->
          <div class="w-3/4 mb-6">
            <input
              v-model="password"
              type="password"
              name="password"
              class="w-full py-4 px-8 bg-slate-200 placeholder:font-semibold rounded hover:ring-1 hover:ring-gray-600 outline-slate-500 border-solid border-2 border-slate-300"
              placeholder="Enter Group Password"
            />
          </div>
          <!-- button -->
          <div class="w-3/4 mb-7">
            <button
              @click="adminLogin(selectedGroup.id, password)"
              type="submit"
              id="password-btn"
              class="py-4 bg-blue-500 w-full rounded text-blue-50 font-bold hover:bg-blue-700"
            >
              Go To Admin Page
            </button>
          </div>
        </div>
        <div
          class="flex justify-center container mx-auto mt-6 text-slate-100 text-sm"
        >
          <div
            class="flex flex-col sm:flex-row justify-between md:w-1/2 items-center"
          ></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { adminStore } from "@/stores/adminInfo";
import { useRouter } from "vue-router";

const router = useRouter();
const store = adminStore();
const selectedGroup = { id: 1, groupName: "group1", password: "****" };
const password = ref("");

const adminLogin = (groupId, password) => {
  console.log(store.adminLogin(groupId, password).value);
  if (store.adminLogin(groupId, password).value) router.push({ name: "admin" });
  router.push({ name: "group" });
};
</script>

<style scoped></style>
