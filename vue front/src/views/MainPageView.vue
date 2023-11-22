<template>
  <!-- component -->
  <!-- This is an example component -->
  <div>
    <NavigationHeader />
    <!-- <div v-if="dbStoreInt.userList.data" class="text-white">{{ dbStoreInt.userList.data }}</div> -->
    <div class="flex w-full pt-16 overflow-hidden bg-black">
      <div
        id="main-content"
        class="relative w-full min-h-screen overflow-y-auto"
      >
        <main>
          <div class="px-20 pt-10">
            <div class="grid w-full h-full grid-cols-1 gap-4 2xl:grid-cols-7">
              <LeftChangeBox />
              <div
                class="p-4 bg-gray-900 rounded-lg shadow sm:p-6 xl:p-8 2xl:col-span-2"
              >
                <div class="flex items-center justify-between mb-4">
                  <div>
                    <h3 class="mb-2 text-xl font-bold text-gray-900">
                      Latest Transactions
                    </h3>
                    <span class="text-base font-normal text-gray-500"
                      >This is a list of latest transactions</span
                    >
                  </div>
                  <div class="flex-shrink-0">
                    <a
                      href="#"
                      class="p-2 text-sm font-medium rounded-lg text-cyan-600 hover:bg-gray-100"
                      >View all</a
                    >
                  </div>
                </div>
                <div class="flex flex-col mt-8">
                  <div class="overflow-x-auto rounded-lg">
                    <div class="inline-block min-w-full align-middle">
                      <div class="overflow-hidden shadow sm:rounded-lg">
                        <table class="min-w-full divide-y divide-gray-200">
                          <thead class="bg-black">
                            <tr>
                              <th
                                scope="col"
                                class="p-4 text-xs font-medium tracking-wider text-left text-gray-500 uppercase"
                              >
                                Transaction
                              </th>
                              <th
                                scope="col"
                                class="p-4 text-xs font-medium tracking-wider text-left text-gray-500 uppercase"
                              >
                                Date & Time
                              </th>
                              <th
                                scope="col"
                                class="p-4 text-xs font-medium tracking-wider text-left text-gray-500 uppercase"
                              >
                                Amount
                              </th>
                            </tr>
                          </thead>
                          <tbody class="bg-black">
                            <tr>
                              <td
                                class="p-4 text-sm font-normal text-gray-900 whitespace-nowrap"
                              >
                                Payment from
                                <span class="font-semibold">Bonnie Green</span>
                              </td>
                              <td
                                class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap"
                              >
                                Apr 23 ,2021
                              </td>
                              <td
                                class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap"
                              >
                                $2300
                              </td>
                            </tr>
                            <tr class="bg-gray-50">
                              <td
                                class="p-4 text-sm font-normal text-gray-900 rounded-lg whitespace-nowrap rounded-left"
                              >
                                Payment refund to
                                <span class="font-semibold">#00910</span>
                              </td>
                              <td
                                class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap"
                              >
                                Apr 23 ,2021
                              </td>
                              <td
                                class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap"
                              >
                                -$670
                              </td>
                            </tr>
                            <tr>
                              <td
                                class="p-4 text-sm font-normal text-gray-900 whitespace-nowrap"
                              >
                                Payment failed from
                                <span class="font-semibold">#087651</span>
                              </td>
                              <td
                                class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap"
                              >
                                Apr 18 ,2021
                              </td>
                              <td
                                class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap"
                              >
                                $234
                              </td>
                            </tr>
                            <tr class="bg-gray-50">
                              <td
                                class="p-4 text-sm font-normal text-gray-900 rounded-lg whitespace-nowrap rounded-left"
                              >
                                Payment from
                                <span class="font-semibold">Lana Byrd</span>
                              </td>
                              <td
                                class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap"
                              >
                                Apr 15 ,2021
                              </td>
                              <td
                                class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap"
                              >
                                $5000
                              </td>
                            </tr>
                            <tr>
                              <td
                                class="p-4 text-sm font-normal text-gray-900 whitespace-nowrap"
                              >
                                Payment from
                                <span class="font-semibold">Jese Leos</span>
                              </td>
                              <td
                                class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap"
                              >
                                Apr 15 ,2021
                              </td>
                              <td
                                class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap"
                              >
                                $2300
                              </td>
                            </tr>
                            <tr class="bg-gray-50">
                              <td
                                class="p-4 text-sm font-normal text-gray-900 rounded-lg whitespace-nowrap rounded-left"
                              >
                                Payment from
                                <span class="font-semibold"
                                  >THEMESBERG LLC</span
                                >
                              </td>
                              <td
                                class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap"
                              >
                                Apr 11 ,2021
                              </td>
                              <td
                                class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap"
                              >
                                $560
                              </td>
                            </tr>
                            <tr>
                              <td
                                class="p-4 text-sm font-normal text-gray-900 whitespace-nowrap"
                              >
                                Payment from
                                <span class="font-semibold">Lana Lysle</span>
                              </td>
                              <td
                                class="p-4 text-sm font-normal text-gray-500 whitespace-nowrap"
                              >
                                Apr 6 ,2021
                              </td>
                              <td
                                class="p-4 text-sm font-semibold text-gray-900 whitespace-nowrap"
                              >
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
import { onMounted } from "vue";
import { ref } from "vue";
import NavigationHeader from "../components/header/NavigationHeader.vue";
import LeftChangeBox from "../components/mainpage/LeftChangeBox.vue";
import { dbStore } from "../stores/db";
import axios from "axios";

export default {
  setup() {
    const dbStoreInst = dbStore();
    const error = ref(null);

    const fetchData = async () => {
      console.log("test");
      try {
        // axios.post('http://localhost:8080/api/main/group', {
        //    user: 1,
        //    group: 1
        // }
        // ).then((response) => {
        //    dbStoreInst.fullObject.value = response.data;
        // });

        const id = 4; // 아이디 설정할 것
        axios({
          url: `http://localhost:8080/api/main/group/${id}`,
          method: "GET",
          withCredentials: true,
          headers: {
            Cookie: getCookie("mySessionId")
          },
        }).then((response) => {
          console.log(response.data);
          dbStoreInst.fullObject.value = response.data;
        });
      } catch (err) {
        console.log(err);
        error.value = err.message;
      } finally {
        //임시 테스트용
        //console.log(dbStoreInst.fullObject.value.users)
        //console.log('User List:', dbStoreInst.userList);
      }
    };

    // 쿠키 가져오는 메소드 (안이쁨 ㅋㅋ)
    const getCookie = (key) => {
      var result = null;
      var cookie = document.cookie.split(";");
      cookie.some(function (item) {
        item = item.replace(" ", "");
        var dic = item.split("=");
        if (key === dic[0]) {
          result = dic[1];
          return true;
        }
      });
      return result;
    };

    onMounted(() => {
      fetchData();
    });

    return { dbStoreInst };
  },

  components: { NavigationHeader, LeftChangeBox },
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
