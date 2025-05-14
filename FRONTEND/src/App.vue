<template>
  <div id="app-container">
    <TransfersTable :key="tableKey" />
    <ScheduleTransferModal @transferScheduled="refreshTable" />
    <NotificationProvider ref="notificationProvider" />
  </div>
</template>

<script setup>
import { ref, provide } from "vue";
import TransfersTable from "./components/TransfersTable.vue";
import ScheduleTransferModal from "./components/ScheduleTransferModal.vue";
import NotificationProvider from "./components/NotificationProvider.vue";

const tableKey = ref(0);
const notificationProvider = ref(null);

const refreshTable = () => {
  tableKey.value += 1;
};

provide("addNotification", (message, type, duration) => {
  if (notificationProvider.value) {
    notificationProvider.value.addNotification(message, type, duration);
  }
});

</script>

<style scoped>
#app-container {
  padding-bottom: 80px; 
}
</style>
