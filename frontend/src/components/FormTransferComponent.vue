<template>
  <div class="container mt-5">
    <h1 class="mb-4">Agendamento de Transferência</h1>
    <form @submit.prevent="handleSubmit" class="bg-light p-4 rounded">

      <div class="mb-3">
        <label for="originAccount" class="form-label">Conta de Origem</label>
        <input v-model="formData.originAccount" type="text" class="form-control" id="originAccount" required
          pattern="\d{10}" title="Digite o numero da sua conta" @input="validateNumeric" minlength="10" maxlength="10">
        <span v-if="!isOriginAccountValid" class="text-danger">Digite apenas números e exatamente 10 dígitos.</span>
      </div>
      <div class="mb-3">
        <label for="destinationAccount" class="form-label">Conta de Destino</label>
        <input v-model="formData.destinationAccount" type="text" class="form-control" id="destinationAccount" required
          minlength="10" maxlength="10" @input="validateNumeric">
        <span v-if="!isDestinationAccountValid" class="text-danger">Digite apenas números e exatamente 10 dígitos.</span>
      </div>


      <div class="mb-3">
        <label for="transferAmount" class="form-label">Valor da Transferência</label>
        <div class="input-group">
          <span class="input-group-text">R$</span>
          <input v-model="formData.transferAmount" type="text" class="form-control" id="transferAmount" required
            @input="validateNumeric">
        </div>
        <span v-if="!isTransferAmountValid" class="text-danger">Digite apenas números e use uma vírgula para os
          centavos.</span>
      </div>

      <div class="mb-3">
        <label for="transferDate" class="form-label">Data da Transferência</label>
        <input type="date" v-model="formData.transferDate" class="form-control" @input="validateDate">
        <span v-if="!isDateValid" class="text-danger">Selecione uma data entre hoje e 50 dias no futuro.</span>
      </div>

      <button type="submit" class="btn btn-primary">Agendar Transferência</button>
    </form>
  </div>
</template>

<script lang="ts">
import urlAPI from '@/utils/urlAPI';
import { defineComponent, ref } from 'vue';
import axios from 'axios';
import { Transfer } from '@/interfaces/Transfer';
import router from '@/router';

export default defineComponent({
  setup() {
    const formData = ref<Transfer>({
      originAccount: '',
      destinationAccount: '',
      transferAmount: 0,
      transferDate: '',
      schedulingDate: '',
    });
    const isOriginAccountValid = ref(true);
    const isDestinationAccountValid = ref(true);
    const isTransferAmountValid = ref(true);
    const isDateValid = ref(true);

    const validateNumeric = () => {
      formData.value.originAccount = formData.value.originAccount.replace(/\D/g, '');
      isOriginAccountValid.value = /^\d{10}$/.test(formData.value.originAccount);

      formData.value.destinationAccount = formData.value.destinationAccount.replace(/\D/g, '');
      isDestinationAccountValid.value = /^\d{10}$/.test(formData.value.destinationAccount);
      const transferAmountString = formData.value.transferAmount.toString();
      const isValid = /^\d+(\.\d{1,2})?$/.test(transferAmountString);

      isTransferAmountValid.value = isValid;
      if (!isValid) {
        formData.value.transferAmount = parseFloat(transferAmountString.replace(/[^\d.]/g, ''));
      }
    };
    const validateDate = () => {
      const selectedDate = new Date(formData.value.transferDate);
      const currentDate = new Date();

      const minAllowedDate = new Date();
      const maxAllowedDate = new Date();
      minAllowedDate.setDate(currentDate.getDate() - 1)
      maxAllowedDate.setDate(currentDate.getDate() + 50);

      isDateValid.value = selectedDate >= minAllowedDate && selectedDate <= maxAllowedDate;
    };

    const handleSubmit = async () => {
      try {
        const response = await axios.post(`${urlAPI}/schedule`, formData.value);
        if (response.data) {
          console.log('Dados do formulário enviados com sucesso!');
          router.push('/');
        } else {
          console.error(response.statusText);
        }
      } catch (error) {
        console.error('Ocorreu um erro ao enviar dados do formulário:', error);
      }
    };
    return {
      formData,
      isOriginAccountValid,
      isDestinationAccountValid,
      validateNumeric,
      isTransferAmountValid,
      validateDate,
      isDateValid,
      handleSubmit,
    };
  },
});
</script>

<style scoped>
.container {
  max-width: 600px;
}

.bg-light {
  background-color: #f8f9fa !important;
}
</style>
