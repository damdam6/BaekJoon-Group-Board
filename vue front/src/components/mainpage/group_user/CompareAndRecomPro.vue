<template>
    <div class="flex flex-col h-full gap-10">
        <CompareView />
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
