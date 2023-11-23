<template>
    <div>
        <img v-if="src" :src="src" class="w-20 h-20 border border-red-800 rounded-full">
        <img v-else src="https://static.solved.ac/misc/360x360/default_profile.png"
            class="w-20 h-20 border border-red-800 rounded-full">
    </div>
</template>

<script>

export default {
    props: {
        src: String
    }
}

</script>