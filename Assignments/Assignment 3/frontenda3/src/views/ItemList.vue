<template>
  <v-card>
    <v-card-title>
      Patients
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addPatient">Add Patient</v-btn>
      <v-btn @click="viewConsultations">View Consultations</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="patients"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :patient="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";
import router from "@/router";

export default {
  name: "ItemList",
  components: { ItemDialog },
  data() {
    return {
      patients: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "ID Number", value: "numberId" },
        { text: "PNC", value: "pnc" },
        { text: "Birth Date", value: "birthDate" },
        { text: "Address", value: "address" }
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(patient) {
      this.selectedItem = patient;
      this.dialogVisible = true;
    },
    addPatient() {
      this.dialogVisible = true;
    },
    viewConsultations(){
      router.push("/consultations")
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.patients = await api.patients.allPatients();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
