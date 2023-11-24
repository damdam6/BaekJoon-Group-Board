<template>
  <NewGroupFormView v-if="isModalVisible" @close="isModalVisible = false" />

  <div class="overflow-hidden transition-transform border border-white shadow-lg transformrounded-lg hover:scale-105">
    <div class="p-1 bg-blue-200 bg-opacity-30"></div>

    <div class="p-8">
      <h2 class="mb-4 text-3xl font-bold text-white">New Group</h2>
    </div>
    <div class="p-4">
      <button @click="moveToForm()"
        class="w-full p-3 px-4 py-2 py-4 text-xl text-indigo-800 border border-indigo-800 rounded hover:bg-gray-800 focus:outline-none focus:shadow-outline-blue active:bg-blue-800">
        make new group
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import NewGroupFormView from "../../views/NewGroupFormView.vue";
const props = defineProps({
  isVisible: Boolean
});
const isModalVisible = ref(false);
const moveToForm = () => {
  isModalVisible.value = true;
};
</script>

<style scoped></style>
