<template>
   <nav class="fixed z-30 w-full bg-black border-b border-gray-200">
      <div class="px-3 py-3 lg:px-5 lg:pl-3">
         <div class="flex items-center justify-between">
            <LogoTitle />
            <GroupName />

         </div>
      </div>
   </nav>
</template>
<script>
import GroupName from '../icons/groupName.vue';

import LogoTitle from '../icons/logoTitle.vue';

export default {
   components: { LogoTitle, GroupName }
}
</script>