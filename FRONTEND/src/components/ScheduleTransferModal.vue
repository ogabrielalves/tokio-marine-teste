<template>
  <div>
    <button @click="openModal" class="fab-open-modal">
      <PlusIcon class="icon" />
      <span>Agendar Transferência</span>
    </button>

    <div v-if="isModalOpen" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title">Agendar Nova Transferência</h3>
          <button @click="closeModal" class="modal-close-button">
            <XMarkIcon class="icon-close" />
          </button>
        </div>

        <form @submit.prevent="handleSubmit" class="modal-body">
          <div class="form-group">
            <label for="originAccount">Conta de Origem</label>
            <input type="text" inputmode="numeric" pattern="[0-9]*" id="originAccount" v-model="form.originAccount"
              @input="handleAccountInput('originAccount', $event)" maxlength="10" class="form-control"
              placeholder="0000000000">
            <p v-if="errors.originAccount" class="form-error-message">{{ errors.originAccount }}</p>
          </div>

          <div class="form-group">
            <label for="destinationAccount">Conta de Destino</label>
            <input type="text" inputmode="numeric" pattern="[0-9]*" id="destinationAccount"
              v-model="form.destinationAccount" @input="handleAccountInput('destinationAccount', $event)" maxlength="10"
              class="form-control" placeholder="0000000000">
            <p v-if="errors.destinationAccount" class="form-error-message">{{ errors.destinationAccount }}</p>
          </div>

          <div class="form-group">
            <label for="amount">Valor (R$)</label>
            <input type="text" id="amount" v-model="formattedAmount" @input="handleAmountInput"
              @blur="formatAmountOnBlur" class="form-control" placeholder="R$ 0,00">
            <p v-if="errors.amount" class="form-error-message">{{ errors.amount }}</p>
          </div>

          <div class="form-group">
            <label for="transferDate">Data da Transferência</label>
            <input type="date" id="transferDate" v-model="form.transferDate" :min="minDate" class="form-control">
            <p v-if="errors.transferDate" class="form-error-message">{{ errors.transferDate }}</p>
          </div>

          <div class="modal-footer">
            <button @click="closeModal" type="button" class="button button-secondary">
              Cancelar
            </button>
            <button id="confirm-schedule" type="submit" :disabled="isSubmitting" class="button">
              {{ isSubmitting ? "Agendando..." : "Agendar" }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, watch, inject, nextTick } from "vue";
import { PlusIcon, XMarkIcon } from "@heroicons/vue/24/solid";
import { scheduleTransfer } from "../services/transferService";

const emit = defineEmits(["transferScheduled"]);
const addNotification = inject("addNotification");

const isModalOpen = ref(false);
const isSubmitting = ref(false);
const formattedAmount = ref("");

const form = reactive({
  originAccount: "",
  destinationAccount: "",
  amount: null,
  transferDate: "",
});

const errors = reactive({
  originAccount: "",
  destinationAccount: "",
  amount: "",
  transferDate: "",
});

const minDate = computed(() => {
  const today = new Date();
  today.setDate(today.getDate() + 1);
  return today.toISOString().split("T")[0];
});

const handleAccountInput = (field, event) => {
  const value = event.target.value;
  form[field] = value.replace(/[^0-9]/g, "");
};

const formatCurrencyBRL = (value) => {
  if (value === null || value === undefined || isNaN(Number(value))) return "";
  const numberValue = Number(value);
  return numberValue.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
};

const parseCurrencyBRLToNumber = (formattedValue) => {
  if (!formattedValue) return null;
  const numericString = String(formattedValue).replace(/[^0-9]/g, "");
  if (numericString === "") return null;
  return parseFloat(numericString) / 100;
};

const handleAmountInput = (event) => {
  const inputElement = event.target;
  let rawValue = inputElement.value;
  const cursorPosition = inputElement.selectionStart;
  const originalLength = rawValue.length;

  let numbersOnly = rawValue.replace(/[^0-9]/g, "");

  if (numbersOnly.length > 1 && numbersOnly.startsWith("0")) {
    numbersOnly = numbersOnly.replace(/^0+/, "");
    if (numbersOnly === "") numbersOnly = "0";
  }

  form.amount = numbersOnly ? parseFloat(numbersOnly) / 100 : null;

  let newFormattedValue = "";
  if (numbersOnly.length === 0) {
    newFormattedValue = "";
  } else if (numbersOnly.length === 1) {
    newFormattedValue = "R$ 0,0" + numbersOnly;
  } else if (numbersOnly.length === 2) {
    newFormattedValue = "R$ 0," + numbersOnly;
  } else {
    const integerPart = numbersOnly.slice(0, -2);
    const decimalPart = numbersOnly.slice(-2);
    const formattedInteger = integerPart.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
    newFormattedValue = `R$ ${formattedInteger},${decimalPart}`;
  }

  formattedAmount.value = newFormattedValue;

  nextTick(() => {
    const newLength = newFormattedValue.length;
    const diff = newLength - originalLength;
    if (inputElement.value !== newFormattedValue) {
      inputElement.value = newFormattedValue;
    }
    try {
      inputElement.setSelectionRange(cursorPosition + diff, cursorPosition + diff);
    } catch (e) { }
  });
};

const formatAmountOnBlur = () => {
  if (form.amount !== null && !isNaN(form.amount)) {
    formattedAmount.value = formatCurrencyBRL(form.amount);
  } else if (formattedAmount.value.replace(/[^0-9]/g, "") === "") {
    formattedAmount.value = "";
    form.amount = null;
  } else {
    const parsed = parseCurrencyBRLToNumber(formattedAmount.value);
    if (parsed !== null && !isNaN(parsed)) {
      form.amount = parsed;
      formattedAmount.value = formatCurrencyBRL(parsed);
    } else {
      formattedAmount.value = "";
      form.amount = null;
    }
  }
};

watch(() => form.amount, (newValue) => {
}, { deep: true });

const openModal = () => {
  resetForm();
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
};

const resetForm = () => {
  form.originAccount = "";
  form.destinationAccount = "";
  form.amount = null;
  formattedAmount.value = "";
  form.transferDate = "";
  errors.originAccount = "";
  errors.destinationAccount = "";
  errors.amount = "";
  errors.transferDate = "";
  isSubmitting.value = false;
};

const validateForm = () => {
  let isValid = true;
  errors.originAccount = "";
  errors.destinationAccount = "";
  errors.amount = "";
  errors.transferDate = "";

  const accountRegex = /^[0-9]+$/;

  if (!form.originAccount || form.originAccount.length !== 10 || !accountRegex.test(form.originAccount)) {
    errors.originAccount = "Conta de origem deve ter 10 caracteres numéricos.";
    isValid = false;
  }
  if (!form.destinationAccount || form.destinationAccount.length !== 10 || !accountRegex.test(form.destinationAccount)) {
    errors.destinationAccount = "Conta de destino deve ter 10 caracteres numéricos.";
    isValid = false;
  }
  if (form.originAccount === form.destinationAccount && form.originAccount !== "") {
    errors.destinationAccount = "Conta de destino não pode ser igual à conta de origem.";
    isValid = false;
  }

  const currentNumericAmount = parseCurrencyBRLToNumber(formattedAmount.value);
  if (currentNumericAmount !== null) {
    form.amount = currentNumericAmount;
  }

  if (form.amount === null || form.amount <= 0) {
    errors.amount = "Valor deve ser positivo e válido.";
    isValid = false;
  }

  if (!form.transferDate) {
    errors.transferDate = "Data da transferência é obrigatória.";
    isValid = false;
  } else {
    const transferDt = new Date(form.transferDate + "T00:00:00");
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    if (transferDt <= today) {
      errors.transferDate = "Data da transferência deve ser futura.";
      isValid = false;
    }
  }
  return isValid;
};

const handleSubmit = async () => {
  if (!validateForm()) {
    let firstErrorField = null;
    if (errors.originAccount) firstErrorField = document.getElementById("originAccount");
    else if (errors.destinationAccount) firstErrorField = document.getElementById("destinationAccount");
    else if (errors.amount) firstErrorField = document.getElementById("amount");
    else if (errors.transferDate) firstErrorField = document.getElementById("transferDate");

    if (firstErrorField) firstErrorField.focus();
    const firstErrorMessage = errors.originAccount || errors.destinationAccount || errors.amount || errors.transferDate;
    if (firstErrorMessage) {
      addNotification(firstErrorMessage, "error");
    }
    return;
  }
  isSubmitting.value = true;
  try {
    const response = await scheduleTransfer({
      originAccount: form.originAccount,
      destinationAccount: form.destinationAccount,
      amount: form.amount,
      transferDate: form.transferDate,
    });

    if (response.status === 500 && response.message === "No applicable rate found for this date") {
      addNotification("Não são permitidas transferências acima de 50 dias.", "error");
    } else if (response.success) {
      addNotification("Transferência agendada com sucesso!", "success");
      emit("transferScheduled");
      closeModal();
    } else {
      addNotification(response.message || "Falha ao agendar transferência.", "error");
    }
  } catch (error) {
    console.error("Erro ao submeter formulário:", error);
    addNotification(error.message || "Ocorreu um erro inesperado ao tentar agendar.", "error");
  } finally {
    isSubmitting.value = false;
  }
};

</script>

<style scoped>
.icon {
  width: 20px;
  height: 20px;
  display: inline-block;
  vertical-align: middle;
}

.icon-close {
  width: 24px;
  height: 24px;
}
</style>
