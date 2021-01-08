import Vue from 'vue'
import VueRouter from 'vue-router'
import Notes from "@/components/Notes"
import NoteAdd from "@/components/NoteAdd";
import NoteEdit from "@/components/NoteEdit";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    component: () => import("@/views/Login")
  },
  {
     path: "/register",
    component: () => import("@/views/Register")
  },
  {
      path: "/profile",
    component: () => import("@/views/Profile")
  },
  {
    path: '/notes',
    name: "Notes",
    component: Notes
  },
  {
    path: '/noteEdit/:noteId',
    name: "NoteEdit",
    props: true,
    component: NoteEdit
  },
  {
    path: '/noteAdd',
    name: "NoteAdd",
    component:  NoteAdd
  },
  {
    path: '/about',
    alias: '/',
    name: "About",
    component: () => import('@/views/About.vue')
  }
]


const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register', '/about'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('user');

  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    next('/login');
  } else {
    next();
  }
});

export default router
