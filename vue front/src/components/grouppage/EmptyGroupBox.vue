<template>
  <div
    class="bg-white rounded-lg overflow-hidden shadow-lg transition-transform transform hover:scale-105"
  >
    <div class="p-1 bg-blue-200"></div>

    <div class="p-8">
      <h2 class="text-3xl font-bold text-gray-800 mb-4">New Group</h2>
    </div>
    <div class="p-4">
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
import { storeToRefs } from "pinia";
import { computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const moveToForm = () => {
  router.push({ name: "newGroupForm" });
};
</script>

<style scoped></style>
