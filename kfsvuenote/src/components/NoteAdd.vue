<template>
  <div class="submit-form m-4 mx-auto p-3">
    <div v-if="!submitted">
      <v-form ref="form" lazy-validation>
        <v-text-field v-model="note.title"
                      :rules="[(v) => !!v || 'Title is required']"
                      label="Title" required/>
        <v-textarea v-model="note.description"
                    :rules="[(v) => !!v || 'Title is required']"
                    label="Description" required/>
        <v-text-field v-model="note.status"
                      label="Status"/>
      </v-form>
      <v-btn @click="saveNote" color="primary" class="mt-3">Create Note</v-btn>
    </div>
    <div v-else>
      <v-card>
        <v-card-title>You submitted successfully!</v-card-title>
        <v-card-actions>
          <v-btn color="success" @click="newNote">Add New Note</v-btn>
          <v-btn @click="goToNotes">Go to Notes</v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>

<script>
import NotesDataService from "../services/NotesDataService";

export default {
  name: "note-add",
  data() {
    return {
      note: {
        id: null,
        title: "",
        description: "",
        status: "",
        published: null
      },
      submitted: false
    };
  },
  methods: {
    saveNote() {
      let data = {
        title: this.note.title,
        description: this.note.description
      };

      NotesDataService.create(data)
          .then(response => {
            this.note.id = response.data.id;
            this.submitted = true;
          })
          .catch(e => {
            console.log(e);
          });
    },
    goToNotes() {
      this.$router.push("/notes");
    },
    newNote() {
      this.submitted = false;
      this.note = {};
    }
  }
};
</script>