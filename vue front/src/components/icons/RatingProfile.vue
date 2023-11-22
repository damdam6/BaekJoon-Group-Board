<template>
    <div class="flex flex-col w-24 h-28 text-center mb-11 pt-3 rounded-[.95rem] relative">
        <div class="mb-[-20px] flex justify-center">
            <div class="flex flex-row">
                <img class="mr-[-15px] inline-block shrink-0 rounded-[.95rem] w-[50px] h-[50px]"
                    :src="ranker.profileImageUrl || 'https://static.solved.ac/tier_small/1.svg'" alt="avatar image">
                <img class="inline-block w-[25px] h-[25px]" :src="`https://static.solved.ac/tier_small/${ranker.tier}.svg`"
                    alt="tier image">
            </div>
        </div>
        <div class="pt-[18px] pb-5 text-center  rounded-[.95rem]">
            <a :href="`https://solved.ac/profile/${ranker.handle}`" target="_blank" rel="noopener noreferrer"
                class="text-dark font-semibold text-blue-500 hover:text-primary text-[0.8rem] transition-colors duration-200 ease-in-out">
                {{ ranker.handle }}
            </a>
            <span class="block font-medium text-[0.7rem] text-blue-700 text-muted">rank {{ ranker.rank }}</span>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        ranker: {
            type: Object,
            required: true
        }
    }
}
</script>
