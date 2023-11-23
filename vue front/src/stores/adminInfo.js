import { ref } from "vue";
import { defineStore } from "pinia";
import { groupStore } from "../stores/group";
import axios from "axios";

export const adminStore = defineStore("adminStore", () => {
  const group = ref({});
  const allUserList = ref([]);
  const groupUserList = ref([]); // groupStore에서 가져오기

  // group : groupId
  // password : password
  const adminLogin = async (groupId, password) => {
    try {
      const jsonData = {
        group: groupId,
        password: password,
      };

      const response = await axios({
        url: `http://localhost:8080/api/user-group/group/admin`,
        method: "POST",
        withCredentials: true,
        data: jsonData,
      });

      console.log(response.data);

      return response.data;
    } catch (err) {
      console.error(err.message);
    }
  };

  const getAllUser = async () => {
    try {
      const response = await axios({
        url: "http://localhost:8080/api/user-group/users",
        method: "GET",
        withCredentials: true,
      });
      allUserList.value = response.data;
    } catch (err) {
      console.error(err.message);
    }
  };

  const getGroupUser = async (groupId) => {
    try {
      axios({
        url: `http://localhost:8080/api/user-group/group/admin/${groupId}`,
        method: "GET",
        withCredentials: true,
      }).then((response) => {
        groupUserList.value = response.data;
      });
    } catch (err) {
      console.log(err.message);
    }
  };

  // jsonData
  // "group" : groupId
  // "user" : userId

  const removeUser = async (groupId, userId) => {
    const jsonData = {
      group: groupId,
      user: userId,
    };

    try {
      const response = await axios({
        url: "http://localhost:8080/api/user-group/group/admin/remove-user",
        method: "POST",
        withCredentials: true,
        data: jsonData,
      });

      if (response.data.status == "400") {
        alert("오류가 발생하였습니다. 담비한테 물어보세요.");
      } else {
        alert(`유저가 방출완료되었습니다.`);
      }
    } catch (err) {
      console.error(err.message);
    }

    // 다시 페이지로 돌아가야함
  };

  // jsonData
  // "group" : groupId
  // "user" : userId

  const addUser = async (groupId, userId) => {
    const jsonData = {
      group: groupId,
      user: userId,
    };

    try {
      const response = await axios({
        url: "http://localhost:8080/api/user-group/group/admin/add-user",
        method: "POST",
        withCredentials: true,
        data: jsonData,
      });

      if (response.data.status == "400") {
        alert("오류가 발생하였습니다. 담비한테 물어보세요.");
      } else {
        alert(`유저가 그룹에 등록되었습니다.`);
      }
    } catch (err) {
      console.error(err.message);
    }

    // 다시 페이지로 돌아가야함
  };

  const deleteGroup = async (groupId) => {
    try {
      const response = await axios({
        url: `http://localhost:8080/api/user-group/group/admin/${groupId}`,
        method: "DELETE",
        withCredentials: true,
      });

      if (response.data.status == "400") {
        alert("오류가 발생하였습니다. 담비한테 물어보세요.");
      } else {
        alert(`그룹이 정상적으로 삭제되었습니다.`);
      }
    } catch (err) {
      console.error(err.message);
    }
  };

  return {
    allUserList,
    groupUserList,
    getAllUser,
    getGroupUser,
    removeUser,
    addUser,
    deleteGroup,
    adminLogin,
  };
});
