<template>
    <div class="h-full p-4 mb-4 bg-gray-900 rounded-lg shadow sm:p-6">

        <div class="text-white"> 테스트임 </div>

        <div class="gap-6">
            <GroupPyramid />
            <GroupUserGrid />
        </div>

    </div>
</template>
<script>
import GroupUserGrid from './GroupUserGrid.vue';
import GroupPyramid from './GroupPyramid.vue';
export default {
    components: { GroupUserGrid, GroupPyramid }
}
</script>