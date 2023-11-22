<template>
   <!-- component -->
   <!-- This is an example component -->
   <div>

      <NavigationHeader />
      <!-- <div v-if="dbStoreInt.userList.data" class="text-white">{{ dbStoreInt.userList.data }}</div> -->
      <div class="flex w-full pt-16 overflow-hidden bg-black">
         <div id="main-content" class="relative w-full min-h-screen overflow-y-auto ">
            <main>
               <div class="px-20 pt-10">
                  <div class="grid w-full h-full grid-cols-1 gap-4 2xl:grid-cols-7 ">
                     <LeftChangeBox />
                     <div class="p-4 bg-gray-900 rounded-lg shadow sm:p-6 xl:p-8 2xl:col-span-2 ">
                        <div class="flex items-center justify-between mb-4">
                           <div>
                              <h3 class="mb-2 text-xl font-bold text-gray-900">Latest Transactions</h3>
                              <span class="text-base font-normal text-gray-500">This is a list of latest
                              </span>
                           </div>
                           <div class="flex-shrink-0">
                              <a href="#" class="p-2 text-sm font-medium rounded-lg text-cyan-600 hover:bg-gray-100">View
                                 all</a>
                           </div>
                        </div>
                        <div class="flex flex-col mt-8">
                           <div class="overflow-x-auto rounded-lg">
                              <div class="inline-block min-w-full align-middle">
                                 <div class="overflow-hidden shadow sm:rounded-lg">
                                    <ProblemCard />

                                 </div>
                              </div>
                           </div>
                        </div>

                     </div>

                  </div>

                  <div class="grid grid-cols-1 my-4 2xl:grid-cols-2 xl:gap-4"></div>
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
import ProblemCard from '../components/icons/ProblemCard.vue';
import { dbStore } from '@/stores/db';



export default {
   setup() {
      const dbStoreInst = dbStore();




      onMounted(() => {
         dbStoreInst.fetchData();

      });

      return { dbStoreInst };
   },

   components: { NavigationHeader, LeftChangeBox, ProblemCard, ProblemCard }
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
