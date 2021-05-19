<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create patient" : "Edit patient" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="patient.name" label="Name" />
          <v-text-field v-model="patient.numberId" label="ID Number" />
          <v-text-field v-model="patient.pnc" label="PNC" />
          <v-text-field v-model="patient.birthDate" label="Birth Date (yyyy-mm-dd)" />
          <v-text-field v-model="patient.address" label="Address" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deletePatient">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ItemDialog",
  props: {
    patient: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.patients
          .create({
            name: this.patient.name,
            numberId: this.patient.numberId,
            pnc: this.patient.pnc,
            birthDate: this.patient.birthDate,
            address: this.patient.address
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.patients
          .edit({
            id: this.patient.id,
            name: this.patient.name,
            numberId: this.patient.numberId,
            pnc: this.patient.pnc,
            birthDate: this.patient.birthDate,
            address: this.patient.address
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deletePatient(){
      api.patients
        .delete({
          id: this.patient.id,
          name: this.patient.name,
          numberId: this.patient.numberId,
          pnc: this.patient.pnc,
          birthDate: this.patient.birthDate,
          address: this.patient.address
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.patient.id;
    },
  },
};
</script>

<style scoped></style>
