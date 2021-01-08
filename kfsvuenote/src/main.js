import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugin/vuetify';
import 'bootstrap';
import VeeValidate from 'vee-validate';
import 'bootstrap/dist/css/bootstrap.min.css';
import VueConfirmDialog from 'vue-confirm-dialog';
import './registerServiceWorker'
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt,
  faInfoCircle,
  faPlusSquare,
} from '@fortawesome/free-solid-svg-icons';

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt, faInfoCircle, faPlusSquare);

Vue.config.productionTip = false
Vue.use(VueConfirmDialog)
Vue.use(VeeValidate)
Vue.component('vue-confirm-dialog', VueConfirmDialog.default)
Vue.component('font-awesome-icon', FontAwesomeIcon);

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
