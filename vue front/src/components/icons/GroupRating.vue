<template>
    <div class="flex flex-row justify-between w-full">
        <RatingProfile />
        <RatingProfile />
        <RatingProfile />
    </div>
</template>

<script>
import RatingProfile from '@/components/icons/RatingProfile.vue';

export default {
    components: {
        RatingProfile
    }
}

</script>