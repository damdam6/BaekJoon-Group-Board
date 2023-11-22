<template>
    <div class="flex items-start p-4 bg-white shadow-lg rounded-xl">
        <div class="flex items-center justify-center w-12 h-12 border border-indigo-100 rounded-full bg-indigo-50">
            <img src="https://static.solved.ac/tier_small/1.svg" class="w-6 h-6 text-indigo-400" fill="none"
                viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
        </div>

        <div class="ml-4">
            <h2 class="font-semibold">??</h2>
            <AlgorithmTag class="text-green-600 bg-green-200" />
            <AlgorithmTag class="text-red-600 bg-red-200" />
            <AlgorithmTag class="text-yellow-600 bg-yellow-200" />
            <AlgorithmTag />
        </div>

    </div>
</template>

<script>
import AlgorithmTag from './AlgorithmTag.vue'

export default {

    components: { AlgorithmTag }
}

</script>