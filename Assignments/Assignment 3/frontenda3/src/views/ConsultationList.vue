<template>
  <v-card>
    <v-card-title>
      Consultations
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addConsultation">Add Consultation</v-btn>
      <v-btn @click="viewPatients">View Patients</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="consultations"
      :search="search"
      @click:row="editConsultation"
    ></v-data-table>
    <ConsultDialog
      :opened="dialogVisible"
      :consultation="selectedItem"
      @refresh="refreshList"
    ></ConsultDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ConsultDialog from "../components/ConsultDialog";
import router from "@/router";

export default {
  name: "ConsultationList",
  components: { ConsultDialog },
  data() {
    return {
      consultations: [],
      search: "",
      headers: [
        {
          text: "Date",
          align: "start",
          sortable: false,
          value: "date",
        },
        { text: "Patient", value: "patientId" },
        { text: "Doctor", value: "doctorId" },
        { text: "Details", value: "details" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editConsultation(consultation) {
      this.selectedItem = consultation;
      this.dialogVisible = true;
    },
    addConsultation() {
      this.dialogVisible = true;
    },
    viewPatients() {
      router.push("/patients");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.consultations = await api.consultations.allConsultations();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
