<template>
  <v-app>
    <v-app-bar app dark>
      <v-btn to="/about" text><font-awesome-icon icon="info-circle" class="mr-1"/>About</v-btn>
      <div v-if="currentUser">
        <v-btn to="/notes" text><font-awesome-icon icon="home" class="mr-1" /> Notes</v-btn>
        <v-btn to="/noteAdd" text><font-awesome-icon icon="plus-square" class="mr-1"/>Add Note</v-btn>
      </div>
      <div class="flex-grow-1"></div>
      <div v-if="currentUser">
        <v-btn to="/profile"><font-awesome-icon icon="user" class="mr-1"/>Profile</v-btn>
        <v-btn v-on:click="logOut"><font-awesome-icon icon="sign-out-alt" class="mr-1"/>Logout</v-btn>
      </div>
      <div v-if="!currentUser">
        <v-btn to="/register"><font-awesome-icon icon="user-plus" class="mr-1"/>Register</v-btn>
        <v-btn to="/login"><font-awesome-icon icon="sign-in-alt" class="mr-1"/>Login</v-btn>
      </div>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
    <vue-confirm-dialog/>
  </v-app>
</template>

<script>
export default {
  name: "app",
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>