<template>
  <div class="about">
    <v-card>
      <v-card-title>kfs NOTE's application</v-card-title>
      <v-card-text>Simple text notes. App for mental exercise for create web VueJS app with JWT token.
        <a href="https://github.com/k0fis/kfsNotes.git">src</a>.
      </v-card-text>
      <v-card-actions v-if="this.$store.state.auth.user">
        <div class="m-2">
          <router-link to="/notes"><font-awesome-icon icon="home" class="mr-1" /> User Note's</router-link>
        </div>
        <div class="m-2">
          <router-link to="/noteAdd"><font-awesome-icon icon="plus-square" class="mr-1"/>Add new Note</router-link>
        </div>
        <div class="m-2">
          <router-link to="/profile"><font-awesome-icon icon="user" class="mr-1"/>User Profile</router-link>
        </div>
      </v-card-actions>
      <v-card-actions v-if="!this.$store.state.auth.user">
        <div class="m-2">
          <router-link to="/register"><font-awesome-icon icon="user-plus" class="mr-1"/>Register</router-link>
        </div>
        <div class="m-2">
          <router-link to="/login"><font-awesome-icon icon="sign-in-alt" class="mr-1"/>Login</router-link>
        </div>
      </v-card-actions>
    </v-card>
  </div>
</template>

