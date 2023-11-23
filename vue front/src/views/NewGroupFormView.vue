<template>
  <div
    class="flex items-center justify-center min-h-screen from-blue-900 via-indigo-800 to-indigo-500 bg-gradient-to-br"
  >
    <div
      class="w-full max-w-lg px-10 py-8 mx-auto bg-white border rounded-lg shadow-2xl"
    >
      <div class="max-w-md mx-auto space-y-3">
        <h3 class="text-lg font-semibold">&#128540; Make New Group!!</h3>
        <div>
          <label class="block py-1">Group Name</label>
          <input
            v-model="groupName"
            type="text"
            class="border w-full py-2 px-2 rounded shadow hover:border-indigo-600 ring-1 ring-inset ring-gray-300 font-mono"
          />
          <p class="text-sm mt-2 px-2 hidden text-gray-600">Text helper</p>
        </div>
        <div>
          <label class="block py-1">Password</label>
          <input
            v-model="password"
            type="password"
            class="border w-full py-2 px-2 rounded shadow hover:border-indigo-600 ring-1 ring-inset ring-gray-300 font-mono"
          />
        </div>
        <div class="flex gap-3 pt-3 items-center">
          <button
            @click="addgroup()"
            class="border hover:border-indigo-600 px-4 py-2 rounded-lg shadow ring-1 ring-inset ring-indigo-700"
          >
            Make New Group
          </button>
          <button
            @click="goBackToGroupPage()"
            class="border hover:border-indigo-600 px-4 py-2 rounded-lg shadow ring-1 ring-inset ring-gray-300"
          >
            Go Back
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();

const groupName = ref("");
const password = ref("");

const addgroup = async () => {
  try {
    const jsonData = {
      groupName: groupName.value,
      password: password.value,
    };

    console.log(jsonData);

    const response = await axios({
      url: `http://localhost:8080/api/user-group/group/add`,
      method: "POST",
      withCredentials: true,
      data: jsonData,
    });

    console.log(response.data);

    if (!response.data) {
      alert("오류가 생겼습니다. 담비한테 물어보세용!");
    }
    router.push({ name: "group" });
  } catch (err) {
    console.error(err.message);
  }
};

const goBackToGroupPage = () => {
  router.push({ name: "group" });
};
</script>

<style scoped></style>
