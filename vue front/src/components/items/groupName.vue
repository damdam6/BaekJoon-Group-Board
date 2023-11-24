<template>
    <span class="text-xl text-yellow-300">
        {{ groupInfo }}
    </span>
</template>

<script setup>
import { mainApiStore } from '@/stores/main-api';
import { computed } from 'vue';
const mainApiStoreInst = mainApiStore();

const groupInfo = computed(() =>
    mainApiStoreInst.selectedGroup
);


</script>