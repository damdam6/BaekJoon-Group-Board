import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const groupStore = defineStore("group", () => {
  const groupList = ref([]);
  const groupUserMap = ref(new Map());

  //   const fetchGroupList = async () => {
  //     try {
  //       axios({
  //         url: `http://localhost:8080/api/user-group/group`,
  //         method: "GET",
  //         withCredentials: true,
  //       })
  //         .then((response) => {
  //           groupList.value = response.data;
  //         })
  //         .finally(() => {
  //           groupList.value.forEach((group) => {
  //             fetchUsers(group["id"]);
  //           })
  //         });
  //     } catch (err) {
  //       console.log(err.message);
  //     }
  //   };

  const fetchGroupList = async () => {
    try {
      const response = await axios({
        url: "http://localhost:8080/api/user-group/group",
        method: "GET",
        withCredentials: true,
      });
      groupList.value = response.data;

      // 여기서 비동기 함수를 사용하여 각 그룹에 대한 사용자 정보를 가져오도록 수정
      await Promise.all(groupList.value.map((group) => fetchUsers(group.id)));
    } catch (err) {
      console.error(err.message);
    }
  };

  const fetchUsers = async (groupId) => {
    try {
      axios({
        url: `http://localhost:8080/api/user-group/group/admin/${groupId}`,
        method: "GET",
        withCredentials: true,
      }).then((response) => {
        console.log(response.data);
        groupUserMap.value.set(groupId, response.data);
        console.log(groupUserMap.value.get(groupId));
      });
    } catch (err) {
      console.log(err.message);
    }
  };

  const leaveGroup = async (groupId) => {
    if (!window.confirm("정말로 그룹을 나가시겠습니까?")) {
      return;
    }

    try {
      axios({
        url: `http://localhost:8080/api/user-group/group/leave/${groupId}`,
        method: "GET",
        withCredentials: true,
      }).then((response) => {
        console.log(response.data);
        groupUserMap.value.set(groupId, response.data);
        console.log(groupUserMap.value.get(groupId));
      });
    } catch (err) {
      console.log(err.message);
    }
  };

  return { fetchGroupList, fetchUsers, groupList, groupUserMap, leaveGroup };
});
