<template>
    <div class="flex flex-col gap-10">
        <CompareView />
        <UserRecomPro />
    </div>
</template>

<script>
import CompareView from './CompareView.vue';
import UserRecomPro from './UserRecomPro.vue';

export default {
    components: {
        CompareView,
        UserRecomPro
    }
}
</script>
