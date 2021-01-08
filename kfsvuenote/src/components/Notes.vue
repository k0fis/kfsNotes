<template>
  <v-row align="center" class="list px-3 m-4">
    <v-col cols="12" md="8">
      <v-text-field v-model="title" label="Search by Title" v-on:change="searchTitle"/>
    </v-col>
    <v-col cols="12" md="4">
      <v-btn small @click="searchTitle">
        Search
      </v-btn>
    </v-col>

    <v-col cols="12" sm="12">
      <v-card class="mx-auto" tile>
        <v-data-table
            :headers="headers"
            :items="notes"
            disable-pagination
            :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mr-2" @click="editNote(item.id)">mdi-pencil</v-icon>
            <v-icon small @click="deleteNote(item.id)">mdi-delete</v-icon>
          </template>
        </v-data-table>
      </v-card>
    </v-col>

    <v-col cols="12" sm="12" v-if="this.totalCount > this.pageSize">
      <v-row>
        <v-col cols="4" sm="3">
          <v-select
              v-model="pageSize"
              :items="pageSizes"
              label="Items per Page"
              @change="handlePageSizeChange"
              next-icon="mdi-menu-right"
              prev-icon="mdi-menu-left"
          ></v-select>
        </v-col>

        <v-col cols="12" sm="9">
          <v-pagination
              v-model="this.currentPage"
              :length="totalPages"
              total-visible="7"
              next-icon="mdi-menu-right"
              prev-icon="mdi-menu-left"
              @input="handlePageChange"
          ></v-pagination>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
import NotesDataService from "@/services/NotesDataService";

export default {
  name: "notes",
  data() {
    return {
      notes: [],
      title: "",
      totalPages: 0,
      totalCount: 0,
      pageSize: 8,
      pageSizes: [4, 8, 12, 16],
      currentPage: 1,
      headers: [
        { text: "Title", align: "start", sortable: false, value: "title" },
        { text: "Description", value: "description", sortable: false },
        { text: "Status", value: "status", sortable: false },
        { text: "Published", value: "published", sortable: true},
        { text: "Actions", value: "actions", sortable: false },
      ],
    };
  },
  methods: {
    retrieveNotes() {
      NotesDataService.getByTitlePaging(this.title, this.currentPage, this.pageSize)
          .then(response => {
            this.notes = response.data.notes.map(this.getDisplayNoteVersion);
            this.currentPage = response.data.currPage;
            this.totalPages = response.data.totalPages;
            this.totalCount = response.data.totalCount;
          })
          .catch(e => {
            console.log(e);
          });
    },
    handlePageChange(value) {
      this.currentPage = value;
      this.retrieveNotes();
    },
    handlePageSizeChange(value) {
      this.pageSize = value;
      this.retrieveNotes();
    },
    refreshList() {
      this.retrieveNotes();
    },
    searchTitle() {
      this.currentPage = 1;
      this.retrieveNotes();
    },

    editNote(noteId) {
      this.$router.push( {name: "NoteEdit", params: { noteId: noteId } })
    },

    deleteNote(noteId) {
      this.$confirm(
          {
            message: `Are you sure?`,
            button: {
              no: 'No',
              yes: 'Yes'
            },
            callback: confirm => {
              if (confirm) {
                NotesDataService.delete(noteId).then(this.refreshList).catch((e)=>console.log(e))
              }
            }
          }
      );
    },

    getDisplayNoteVersion(note) {
      return {
        id: note.id,
        title: note.title.length > 30 ? note.title.substr(0, 30) + "..." : note.title,
        description: note.description.length > 30 ? note.description.substr(0, 30) + "..." : note.description,
        status: note.status,
        published: note.published?note.published:'-'
      };
    }
  },
  mounted() {
    this.retrieveNotes()
  }
}
</script>
