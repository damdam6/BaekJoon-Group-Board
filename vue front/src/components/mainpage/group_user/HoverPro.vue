<template>
    <div class="flex justify-center w-80 h-90">
        <ProblemCard v-if="objCheck" :pro-data="changeboxStoreInst.getProblemObj" />
    </div>
</template>

<script setup>
import { computed } from 'vue';
import ProblemCard from '../../items/pro_card/ProblemCard.vue';
import { changeboxStore } from '../../../stores/changebox-db';

const changeboxStoreInst = changeboxStore();
const objCheck = computed(() =>
    changeboxStoreInst.getProblemObj);
</script>
