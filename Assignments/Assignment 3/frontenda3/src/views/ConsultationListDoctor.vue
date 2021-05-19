<template>
  <v-card>
    <v-card-title>
      Doctor's consultations
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
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
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "ConsultationListDoctor",
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
      connected: false,
    };
  },
  methods: {
    editConsultation(consultation) {
      this.selectedItem = consultation;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.consultations = await api.consultations.allDoctorConsultations(
        this.$store.getters["auth/getName"]
      );
    },
  },
  created() {
    this.refreshList();
    this.socket = new SockJS("http://localhost:8091/gs-guide-websocket");
    this.stompClient = Stomp.over(this.socket);
    this.stompClient.connect(
      {},
      (frame) => {
        this.connected = true;
        console.log(frame);
        this.stompClient.subscribe(
          "/topic/messages/" + this.$store.getters["auth/getName"],
          (tick) => {
            console.log(tick);
          }
        );
        this.stompClient.subscribe(
          "/topic/checkin/" + this.$store.getters["auth/getName"],
          (tick) => {
            console.log(tick);
          }
        );
      },
      (error) => {
        console.log(error);
        this.connected = false;
      }
    );
  },
};
</script>

<style scoped></style>
