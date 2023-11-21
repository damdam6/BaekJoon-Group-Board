<template>
    <div class="p-4 text-gray-200 bg-gray-800 rounded-lg shadow sm:p-6 xl:p-8">
        <div class="flex items-center justify-between mb-10">
            <div class="flex items-center gap-4">
                <div class="w-20 h-20 bg-gray-600 rounded-full">Image 1</div>
                <span class="text-xl font-bold">VS</span>
                <div class="w-20 h-20 bg-gray-600 rounded-full">Image 2</div>
            </div>
        </div>

        <div class="block w-full overflow-x-auto">
            <table class="min-w-full bg-gray-700 border-collapse">
                <thead>
                    <tr>
                        <th
                            class="px-4 py-3 text-xs font-medium tracking-wider text-left uppercase border-b-2 border-gray-6 00">
                            Metric</th>
                        <th
                            class="px-4 py-3 text-xs font-medium tracking-wider text-left uppercase border-b-2 border-gray-600">
                            User Name</th>
                        <th
                            class="px-4 py-3 text-xs font-medium tracking-wider text-left uppercase border-b-2 border-gray-600">
                            Login User</th>
                    </tr>
                </thead>
                <tbody class="bg-gray-800">
                    <TableRowComponent v-for="metric in metrics" :key="metric.name" :metricName="metric.name"
                        :userData1="tableData[0][metric.key]" :userData2="tableData[1][metric.key]" />
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import TableRowComponent from './TableRowComponent.vue';

export default {
    components: { TableRowComponent },
    setup() {
        const tableData = ref([
            { tier: 1, top100Solved: 50, allSolved: 100, rank: 10 },
            { tier: 2, top100Solved: 40, allSolved: 90, rank: 20 }
        ]);

        const metrics = ref([
            { name: 'Tier', key: 'tier' },
            { name: 'Top 100 Solved', key: 'top100Solved' },
            { name: 'All Solved', key: 'allSolved' },
            { name: 'Rank', key: 'rank' }
        ]);

        return {
            tableData,
            metrics
        };
    }
}
</script>
