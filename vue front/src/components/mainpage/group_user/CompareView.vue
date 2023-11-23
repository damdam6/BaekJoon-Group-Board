<template>
    <div
        :class="['p-5 text-gray-200 bg-gray-800 rounded-lg shadow sm:p-6 xl:p-8', userInfoInst.userId === 0 ? 'invisible' : '']">
        <div>
            <UserProfile class="bg-black" />
        </div>
    </div>
</template>

<script>
import UserProfile from '../../items/UserProfile.vue';
import { selectedUserStore } from '@/stores/userInfo';
import { mainApiStore } from '../../../stores/main-api';


export default {
    components: { UserProfile },
    setup() {
        const mainApiStoreInst = mainApiStore();
        const userInfoInst = selectedUserStore();


        const setHidden = function () {
            return "{'invisible': userInfoInst.userId === 0}"
        }

        return {
            userInfoInst,
            mainApiStoreInst,
            setHidden

        };
    }
}
</script>
