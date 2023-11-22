<template>
    <div class="flex items-start p-4 m-10 bg-white shadow-lg rounded-xl">
        <div class="flex items-center justify-center w-12 h-12 border border-indigo-100 rounded-full bg-indigo-50">
            <!-- 여기에 이미지나 다른 데이터를 proData를 사용하여 표시 -->
            <img :src="`https://static.solved.ac/tier_small/${proData.level}.svg`" class="w-6 h-6 text-indigo-400">
        </div>

        <div class="ml-4">
            <h2 class="mb-2 font-semibold">{{ proData.titleKo }}</h2>
            <AlgorithmTag v-for="(algorithm, index) in proData.algorithm" :key="index"
                :class="shuffledColorPairs[index % shuffledColorPairs.length].join(' ')" :algorithm="algorithm" />
        </div>
    </div>
</template>


<script>
import AlgorithmTag from '@/components/items/pro_card/AlgorithmTag.vue';

export default {
    components: { AlgorithmTag },
    props: {
        proData: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            // 초기 색상 배열 정의
            colorPairs: [
                ["bg-blue-300", "text-blue-800"],
                ["bg-green-300", "text-green-800"],
                ["bg-red-300", "text-red-800"],
                // ... 기타 색상 쌍
            ],
        };
    },
    computed: {
        shuffledColorPairs() {
            return this.shuffleArray([...this.colorPairs]);
        }
    },
    methods: {
        shuffleArray(array) {
            let currentIndex = array.length, temporaryValue, randomIndex;

            // 남은 요소가 없을 때까지 반복
            while (0 !== currentIndex) {
                // 남은 요소 중에서 하나를 임의로 선택
                randomIndex = Math.floor(Math.random() * currentIndex);
                currentIndex -= 1;

                // 선택한 요소와 현재 요소를 교환
                temporaryValue = array[currentIndex];
                array[currentIndex] = array[randomIndex];
                array[randomIndex] = temporaryValue;
            }

            return array;
        }
    }



}
</script>

