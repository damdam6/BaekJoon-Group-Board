import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import VueCookies from "vue-cookies"


import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(VueCookies);
//쿠키의 만료일은 7일이다. (글로벌 세팅)
app.$cookies.config("7d");

app.mount('#app')
