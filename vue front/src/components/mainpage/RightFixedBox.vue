//우측 추천 문제들

<template>
    <div class="flex flex-col items-center justify-center mt-8 2xl:col-span-2">
        <div class="overflow-x-auto rounded-lg">
            <div class="inline-block min-w-full align-middle">
                <div class="overflow-hidden shadow sm:rounded-lg">
                    <GroupRating class="bg-gray-900 rounded-lg shadow md-4 sm:p-6 xl:p-8" />
                    <ProblemCard class="m-10" />
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