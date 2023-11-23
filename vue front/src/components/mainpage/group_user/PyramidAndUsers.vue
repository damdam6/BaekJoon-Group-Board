<template>
    <div class="h-full p-4 mb-4 border border-yellow-600 rounded-lg shadow sm:p-6">


        <div class="grid gap-6">
            <GroupPyramid />
            <div class="p-5 border border-white rounded-lg">
                <GroupUserGrid />
            </div>

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