<template>
  <div v-if="currentNote" class="edit-form py-3 m-4">
    <p class="headline">Edit Note</p>
    <v-form ref="form" lazy-validation>
      <v-text-field
          v-model="currentNote.title"
          :rules="[(v) => !!v || 'Title is required']"
          label="Title"
          required
      />
      <v-textarea
          v-model="currentNote.description"
          :rules="[(v) => !!v || 'Description is required']"
          label="Description"
          required
      />
      <v-text-field
          v-model="currentNote.status"
          label="Status"
      />
      <v-text-field v-model="currentNote.published" label="Published" readonly disabled />
      <v-divider class="my-5"></v-divider>
      <v-btn color="error" small class="mr-2" @click="deleteNote">Delete</v-btn>
      <v-btn color="success" small @click="updateNote">Update</v-btn>
      <v-divider class="my-5"></v-divider>
    </v-form>

    <p class="mt-3">{{ this.message }}</p>
  </div>
  <div v-else>
    <v-card>
      <v-card-title>There is no note to edit</v-card-title>
      <v-card-actions>
        <v-btn @click="newNote">Add New Note</v-btn>
        <v-btn @click="goToNotes">Go to Notes</v-btn>
      </v-card-actions>
    </v-card>

  </div>
</template>

<script>
import NotesDataService from "../services/NotesDataService";

export default {
  props: ["noteId"],
  data () {
    return {
      currentNote: null,
      message: ''
    }
  },
  methods: {
    getNote(id) {
      NotesDataService.get(id)
          .then(response => {
            this.currentNote = response.data;
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
    },
    goToNotes() {
      this.$router.push("/notes")
    },
    newNote() {
      this.$router.push("/noteAdd")
    },
    updateNote() {
      NotesDataService.update(this.currentNote.id, this.currentNote)
          .then(response => {
            console.log(response.data);
            this.message = 'The tutorial was updated successfully!';
            this.goToNotes();
          })
          .catch(e => {
            console.log(e);
          });
    },

    deleteNote() {
      this.$confirm(
          {
            message: `Are you sure?`,
            button: {
              no: 'No',
              yes: 'Yes'
            },
            callback: confirm => {
              if (confirm) {
                NotesDataService.delete(this.currentNote.id)
                    .then(()=>{
                      this.message = "Note was deleted"
                      this.currentNote = null
                    })
                    .catch((e)=>console.log("Cannot delete note:" + e))

              }
            }
          }
      )
    }
  },
  mounted() {
    if (this.noteId != null) {
      this.getNote(this.noteId);
    } else {
      this.currentNote = null;
    }
  }
};
</script>
