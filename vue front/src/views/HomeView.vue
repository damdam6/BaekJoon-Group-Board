<script setup></script>

<template>
  <main></main>
</template>

<script setup>
import { mainApiStore } from "../stores/main-api";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
onMounted(() => {
  router.replace({ name: "login" });
});
</script>
