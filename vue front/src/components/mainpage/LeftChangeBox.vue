
<template>
    <div class="p-4 bg-black rounded-lg shadow sm:p-6 2xl:col-span-5 min-w-fit">
        <div class="flex flex-col h-full mb-4 justify-evenly sm:flex-row gap-y-8 gap-x-4">
            <CompareAndRecomPro class="xl:col-span-2" />
            <div></div>
            <PyramidAndUsers class="xl:col-span-2" />


        </div>
    </div>
</template>

<script>
import CompareAndRecomPro from './group_user/CompareAndRecomPro.vue';
import PyramidAndUsers from './group_user/PyramidAndUsers.vue';

export default {
    components: { CompareAndRecomPro, PyramidAndUsers }
}
</script> 

