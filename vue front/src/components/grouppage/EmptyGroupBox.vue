<template>
  <div
    class="opacity-75 m-5 bg-white rounded-lg overflow-hidden shadow-lg transition-transform transform hover:scale-105 flex flex-col"
  >
    <div class="p-1 bg-indigo-400"></div>

    <div class="p-8 pb-1">
      <h2 class="text-3xl font-bold text-indigo-800 mb-4">New Group</h2>
    </div>
    <div class="p-4 mt-auto flex items-center justify-center">
      <svg
        class="mr-2 w-12 h-12 text-indigo-800"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M12 2v20M2 12h20"
        ></path>
      </svg>
    </div>
    <div class="p-4 mt-auto">
      <button
        @click="moveToForm()"
        class="py-4 p-3 w-full bg-indigo-800 text-white text-xl rounded px-4 py-2 hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue active:bg-blue-800"
      >
        make new group
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const moveToForm = () => {
  router.push({ name: "newGroupForm" });
};
</script>

<style scoped></style>
