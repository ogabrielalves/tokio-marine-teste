<template>
  <div class="container">
    <h1 class="page-title">Transfers</h1>

    <div v-if="error" class="error-message" role="alert">
      <strong>Erro!</strong>
      <span> {{ error }}</span>
    </div>

    <div v-if="loading" class="loading-message text-center">
      <p>Carregando transferências...</p>
    </div>

    <div v-else-if="transfers.length === 0 && !error" class="no-data-message text-center">
      <p>Nenhuma transferência agendada encontrada.</p>
    </div>

    <div v-else-if="transfers.length > 0" class="table-container">
      <table class="transfers-table">
        <thead>
          <tr>
            <th>Conta de Origem</th>
            <th>Conta de Destino</th>
            <th>Valor</th>
            <th>Taxa</th>
            <th>Data de Agendamento</th>
            <th>Data da Transferência</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transfer in transfers" :key="transfer.id">
            <td>{{ transfer.originAccount }}</td>
            <td>{{ transfer.destinationAccount }}</td>
            <td>{{ formatCurrencyBRL(transfer.amount) }}</td>
            <td>{{ formatCurrencyBRL(transfer.fee) }}</td>
            <td>{{ formatDate(transfer.schedulingDate) }}</td>
            <td>{{ formatDate(transfer.transferDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getTransfers } from "../services/transferService";

const transfers = ref([]);
const error = ref(null);
const loading = ref(true);

const formatCurrencyBRL = (value) => {
  if (value === null || value === undefined || isNaN(Number(value))) return "-";
  const numberValue = Number(value);
  return numberValue.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
};

const formatDate = (dateString) => {
  if (!dateString) return "N/A";
  const date = new Date(dateString);
  const userTimezoneOffset = date.getTimezoneOffset() * 60000;
  const correctedDate = new Date(date.getTime() + userTimezoneOffset + (24*60*60*1000)); 
  return correctedDate.toLocaleDateString("pt-BR", { timeZone: "UTC" });
};

onMounted(async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await getTransfers();
    if (response.status === 200 && response.message === "OK") {
      transfers.value = response.data;
    } else {
      error.value = response.message || "Falha ao carregar transferências.";
      console.error("Erro da API:", response);
    }
  } catch (err) {
    console.error("Erro ao buscar transferências no componente:", err);
    error.value = "Não foi possível conectar ao servidor para buscar as transferências.";
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.page-title {
  font-size: 1.75rem; 
  font-weight: bold;
  margin-bottom: 1.5rem;
  color: var(--text-color);
}
</style>
