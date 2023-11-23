<script setup>
</script>

<template>
  <main>
    <div>{{ mainApiStoreInst.setUserTop100 }}</div>

  </main>
</template>

<script>
import { mainApiStore } from '../stores/main-api';
import { onMounted } from 'vue';

export default {
  setup() {
    const mainApiStoreInst = mainApiStore();

    onMounted(() => {
      mainApiStoreInst.fetchData();
    }

    )


    return { mainApiStoreInst }
  },

};
</script>