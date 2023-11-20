<template>
    <div class="flex flex-col items-center mt-8">
        <div v-for="(level, index) in 15" :key="index" class="flex justify-center space-x-4">
            <div v-for="box in (level === 1 ? 1 : level === 5 ? 0 : level + 1)" :key="box"
                class="w-8 h-8 bg-blue-500 rounded-lg"></div>
        </div>
    </div>
</template>
  
<script>
export default {
    name: 'GroupPyramid',
};
</script>
  
<style scoped>
/* 여기에 필요한 스타일 추가 가능 */
</style>
  