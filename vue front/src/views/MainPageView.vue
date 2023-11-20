<template>
   <!-- component -->
   <!-- This is an example component -->
   <div>
      <NavigationHeader />
      <div class="flex w-full pt-16 overflow-hidden bg-black">
         <div id="main-content" class="relative w-full h-full overflow-y-auto bg-black">
            <main>
               <div class="px-4 pt-6">
                  <div class="grid w-full grid-cols-1 gap-4 border border-pink-600 2xl:grid-cols-7 ">
                     <div class="p-4 bg-gray-900 border border-yellow-700 rounded-lg shadow sm:p-6 2xl:col-span-5">
                        <div class="flex items-center justify-between mb-4 gap-x-4">
                           <GroupUserView class="border border-sky-500 xl:col-span-2" />
                           <CompareView class="border border-red-600" />
                        </div>
                     </div>
                     <div class="p-4 bg-gray-900 rounded-lg shadow sm:p-6 xl:p-8 2xl:col-span-2 ">
                        <div class="flex items-center justify-between mb-4">
                           <div>
                              <h3 class="mb-2 text-xl font-bold text-gray-900">Latest Transactions</h3>
                              <span class="text-base font-normal text-gray-500">This is a list of latest
                                 transactions</span>
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
                                    <table class="min-w-full divide-y divide-gray-200">
                                       <thead class="bg-black">
                                          <tr>
                                             <th scope="col"
                                                class="p-4 text-xs font-medium tracking-wider text-left text-gray-500 uppercase">
                                                Transaction
                                             </th>
                                             <th scope="col"
                                                class="p-4 text-xs font-medium tracking-wider text-left text-gray-500 uppercase">
                                                Date & Time
                                             </th>
                                             <th scope="col"
                                                class="p-4 text-xs font-medium tracking-wider text-left text-gray-500 uppercase">
                                                Amount
                                             </th>
                                          </tr>
                                       </thead>
                                       <tbody class="bg-black">
                                          <tr>
                                             <td class="p-4 text-sm font-normal text-gray-900 whitespace-nowrap">
                                                Payment from <span class="font-semibold">Bonnie Green</span>
                                             </td>
                                             <td class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap">
                                                Apr 23 ,2021
                                             </td>
                                             <td class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap">
                                                $2300
                                             </td>
                                          </tr>
                                          <tr class="bg-gray-50">
                                             <td
                                                class="p-4 text-sm font-normal text-gray-900 rounded-lg whitespace-nowrap rounded-left">
                                                Payment refund to <span class="font-semibold">#00910</span>
                                             </td>
                                             <td class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap">
                                                Apr 23 ,2021
                                             </td>
                                             <td class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap">
                                                -$670
                                             </td>
                                          </tr>
                                          <tr>
                                             <td class="p-4 text-sm font-normal text-gray-900 whitespace-nowrap">
                                                Payment failed from <span class="font-semibold">#087651</span>
                                             </td>
                                             <td class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap">
                                                Apr 18 ,2021
                                             </td>
                                             <td class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap">
                                                $234
                                             </td>
                                          </tr>
                                          <tr class="bg-gray-50">
                                             <td
                                                class="p-4 text-sm font-normal text-gray-900 rounded-lg whitespace-nowrap rounded-left">
                                                Payment from <span class="font-semibold">Lana Byrd</span>
                                             </td>
                                             <td class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap">
                                                Apr 15 ,2021
                                             </td>
                                             <td class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap">
                                                $5000
                                             </td>
                                          </tr>
                                          <tr>
                                             <td class="p-4 text-sm font-normal text-gray-900 whitespace-nowrap">
                                                Payment from <span class="font-semibold">Jese Leos</span>
                                             </td>
                                             <td class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap">
                                                Apr 15 ,2021
                                             </td>
                                             <td class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap">
                                                $2300
                                             </td>
                                          </tr>
                                          <tr class="bg-gray-50">
                                             <td
                                                class="p-4 text-sm font-normal text-gray-900 rounded-lg whitespace-nowrap rounded-left">
                                                Payment from <span class="font-semibold">THEMESBERG LLC</span>
                                             </td>
                                             <td class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap">
                                                Apr 11 ,2021
                                             </td>
                                             <td class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap">
                                                $560
                                             </td>
                                          </tr>
                                          <tr>
                                             <td class="p-4 text-sm font-normal text-gray-900 whitespace-nowrap">
                                                Payment from <span class="font-semibold">Lana Lysle</span>
                                             </td>
                                             <td class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap">
                                                Apr 6 ,2021
                                             </td>
                                             <td class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap">
                                                $1437
                                             </td>
                                          </tr>
                                       </tbody>
                                    </table>
                                 </div>
                              </div>
                           </div>
                        </div>

                     </div>

                  </div>

                  <div class="grid grid-cols-1 my-4 2xl:grid-cols-2 xl:gap-4">


                  </div>
               </div>
            </main>

            <p class="my-10 text-sm text-center text-gray-500">
               &copy; 2019-2021 <a href="#" class="hover:underline" target="_blank">Themesberg</a>. All rights reserved.
            </p>
         </div>
      </div>
   </div>
</template>

<script>
import NavigationHeader from '../components/header/NavigationHeader.vue';
import GroupUserView from '../components/group_user/GroupUserView.vue';
import CompareView from '../components/CompareView.vue';
import BoxViewTemp from '../components/BoxViewTemp.vue';

export default {
   mounted() {
      let script = document.createElement("script");
      script.async = true;
      script.defer = true;
      script.src = "https://buttons.github.io/buttons.js";
      document.head.appendChild(script);
   },
   components: { NavigationHeader, GroupUserView, CompareView, BoxViewTemp }
}
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
