<template>
    <div class="flex flex-col gap-10">
        <!-- CompareView : 사람 프로필 -->
        <CompareView />
        <!-- CompareView : 사람 프로필 -->
        <HoverPro />
    </div>
</template>

<script>
import CompareView from './CompareView.vue';
import HoverPro from './HoverPro.vue';

export default {
    components: {
        CompareView,
        HoverPro,
    }
}
</script>
