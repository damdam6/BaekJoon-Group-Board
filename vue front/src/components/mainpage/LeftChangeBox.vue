<template>
    <div class="p-4 bg-gray-900 rounded-lg shadow sm:p-6 2xl:col-span-5 min-w-fit">
        <div class="flex flex-col items-center justify-center sm:flex-row gap-y-8 gap-x-4">
            <HoverPro />
            <PyramidAndUsers class="bg-gray-800 border xl:col-span-2" />
            <CompareAndRecomPro class="xl:col-span-2" />
        </div>
    </div>
</template>


<script>
import PyramidAndUsers from './group_user/PyramidAndUsers.vue';
import CompareAndRecomPro from './group_user/CompareAndRecomPro.vue';
import HoverPro from './group_user/HoverPro.vue';

export default {
    components: { PyramidAndUsers, CompareAndRecomPro, HoverPro }
}
</script> 

