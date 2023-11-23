<template>
    <UserProfile />
</template>

<script>
import UserProfile from '../../items/UserProfile.vue';
import { ref } from 'vue';
import TableRowComponent from './TableRowComponent.vue';
import { selectedUserStore } from '@/stores/userInfo';
import { mainApiStore } from '../../../stores/main-api';
import CircleImg from '../../items/CircleImg.vue';


export default {
    components: { TableRowComponent, CircleImg, UserProfile, UserProfile },
    setup() {
        const mainApiStoreInst = mainApiStore();
        const userInfoInst = selectedUserStore();
        const tableData = ref([
            { tier: userInfoInst.userTier, top100Solved: 50, allSolved: 100, rank: 10 },
            { tier: 2, top100Solved: 40, allSolved: 90, rank: 20 }
        ]);

        const metrics = ref([
            { name: 'Tier', key: 'tier' },
            { name: 'Top 100 Solved', key: 'top100Solved' },
            { name: 'All Solved', key: 'allSolved' },
            { name: 'Rank', key: 'rank' }
        ]);

        return {
            userInfoInst,
            tableData,
            metrics,
            mainApiStoreInst,

        };
    }
}
</script>
