//우측 추천 문제들

<template>
    <div class="p-4 bg-gray-900 rounded-lg shadow sm:p-6 xl:p-8 2xl:col-span-2 ">
        <div class="flex flex-col mt-8">
            <div class="overflow-x-auto rounded-lg">
                <div class="inline-block min-w-full align-middle">
                    <div class="overflow-hidden shadow sm:rounded-lg">
                        <GroupRating />
                        <ProblemCard />
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import ProblemCard from '@/components/icons/ProblemCard.vue';
import GroupRating from '@/components/icons/GroupRating.vue';
export default {
    setup() {
    },

    components: { ProblemCard, GroupRating }
};
</script>