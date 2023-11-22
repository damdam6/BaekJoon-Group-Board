import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const loginStore = defineStore("login", () => {
  const loginUser = ref({});

  const login = async (userName) => {
    try {
      axios({
        url: `http://localhost:8080/api/user-group/login/${userName}`,
        method: "GET",
        withCredentials: true,
      }).then((response) => {
        console.log(response.data);
        loginUser.value = response.data;
      });
    } catch (err) {
      console.log(err.message);
    }
  };

  const regist = async (userName) => {
    try {
      axios({
        url: `http://localhost:8080/api/user-group/user/add/${userName}`,
        method: "GET",
        withCredentials: true,
      }).then(() => {
        alert.apply("등록 완료");
      });
    } catch (err) {
      console.log(err.message);
    }
  };

  return { login, loginUser, regist };
});
