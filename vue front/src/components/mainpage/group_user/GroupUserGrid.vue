<template>
    <div class="flex items-center justify-center">
        <div class="grid grid-cols-10 gap-3">
            <img v-for="n in 30" :key="n" class="rounded-md w-7 h-7"
                src="https://demo.themesberg.com/windster/images/users/neil-sims.png" alt="Neil image">
        </div>
    </div>
</template>

<script>
export default {
    // 여기에 필요한 스크립트를 추가할 수 있습니다.
}
</script>
