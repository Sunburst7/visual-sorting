import Vue from 'vue'
import Router from 'vue-router'
import mainWindow from "../views/mainWindow";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component:mainWindow,
    }
  ]
})
