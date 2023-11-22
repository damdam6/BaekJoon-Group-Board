
<template>
    <div class="p-4 bg-gray-900 border border-yellow-700 rounded-lg shadow sm:p-6 2xl:col-span-5 min-w-fit">
        <div class="flex flex-col items-center justify-center mb-4 sm:flex-row gap-y-8 gap-x-4">
            <GroupUserView class="border border-sky-500 xl:col-span-2" />
            <CompareAndRecomPro class="border border-red-600 xl:col-span-2" />
        </div>
    </div>
</template>

<script>
import GroupUserView from './group_user/PyramidAndUsers.vue';
import CompareAndRecomPro from './group_user/CompareAndRecomPro.vue';

export default {
    components: { GroupUserView, CompareAndRecomPro }
}
</script> 

