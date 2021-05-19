<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create consultation" : "Edit consultation" }}
        </v-toolbar>
        <v-form>
          <v-text-field
            v-model="consultation.date"
            label="Date (yyyy-mm-dd hh)"
          />
          <v-text-field v-model="consultation.patientId" label="Patient" />
          <v-text-field v-model="consultation.doctorId" label="Doctor" />
          <v-text-field v-model="consultation.details" label="Details" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteConsultation">Delete</v-btn>
          <v-btn v-if="!isNew" @click="patientArrived">Patient arrived</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ConsultDialog",
  props: {
    consultation: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.consultations
          .create({
            date: this.consultation.date,
            patientId: this.consultation.patientId,
            doctorId: this.consultation.doctorId,
            details: this.consultation.details,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.consultations
          .edit({
            id: this.consultation.id,
            date: this.consultation.date,
            patientId: this.consultation.patientId,
            doctorId: this.consultation.doctorId,
            details: this.consultation.details,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteConsultation() {
      api.consultations
        .delete({
          id: this.consultation.id,
          date: this.consultation.date,
          patientId: this.consultation.patientId,
          doctorId: this.consultation.doctorId,
          details: this.consultation.details,
        })
        .then(() => this.$emit("refresh"));
    },
    patientArrived() {
      api.consultations
        .checkin({
          id: this.consultation.id,
          date: this.consultation.date,
          patientId: this.consultation.patientId,
          doctorId: this.consultation.doctorId,
          details: this.consultation.details,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.consultation.id;
    },
  },
};
</script>

<style scoped></style>
