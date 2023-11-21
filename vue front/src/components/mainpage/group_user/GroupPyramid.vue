<template>
    <div class="flex flex-col items-center mt-8">
        <div v-for="level in 15" :key="level" class="flex justify-center mb-2 space-x-2">
            <div v-for="box in level" :key="level + '-' + box">
                <div v-if="level < 5" class="w-5 h-5 bg-blue-500 rounded-sm sm:rounded-md md:w-6 md:h-6">
                </div>
                <div v-else-if="level === 5" class="hidden w-5 h-5 bg-blue-500 rounded-sm sm:rounded-md md:w-6 md:h-6">
                </div>
                <div v-else class="w-4 h-4 bg-blue-500 rounded-sm sm:rounded-md sm:w-4 sm:h-4 md:w-5 md:h-5">
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'GroupPyramid',
};
</script>
