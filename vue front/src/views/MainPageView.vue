<template>
   <!-- component -->
   <div v-if="mainApiStoreInst.isLoading">
      로딩중
   </div>
   <!-- This is an example component -->
   <div v-else>
      <NavigationHeader />
      <div class="flex w-full pt-16 overflow-hidden bg-black">
         <div id="main-content" class="relative w-full overflow-y-hidden ">
            <main>
               <div class="px-20 pt-10">
                  <div class="grid w-full h-full grid-cols-1 gap-4 2xl:grid-cols-7 ">
                     <LeftChangeBox />
                     <RightFixedBox />

                  </div>
               </div>
            </main>
            <p class="my-10 text-sm text-center text-gray-500">
               &copy; 2019-2021
               <a href="#" class="hover:underline" target="_blank">Themesberg</a>.
               All rights reserved.
            </p>
         </div>
      </div>
   </div>
</template>

<script>
import { onMounted } from 'vue';
import { ref } from 'vue';
import NavigationHeader from '../components/header/NavigationHeader.vue';
import LeftChangeBox from '../components/mainpage/LeftChangeBox.vue';
import RightFixedBox from '@/components/mainpage/RightFixedBox.vue';
import { mainApiStore } from '@/stores/main-api';



export default {
   setup() {
      const mainApiStoreInst = mainApiStore();

      onMounted(() => {
         mainApiStoreInst.fetchData();

      });

      return { mainApiStoreInst };
   },

   components: { NavigationHeader, LeftChangeBox, RightFixedBox }
};
</script>

<style>
@media (min-width: 1024px) {
   .about {
      min-height: 100vh;
      display: flex;
      align-items: center;
   }
}
</style>
