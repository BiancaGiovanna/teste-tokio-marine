<template>
  <div class="table-responsive table-container">
    <table class="table table-striped table-bordered">
      <thead class="table-dark">
        <tr>
          <!-- <th scope="col">#</th> -->
          <th scope="col">Conta de origem</th>
          <th scope="col">Conta de destino</th>
          <th scope="col">Valor transferido</th>
          <th scope="col">Valor da taxa aplicada</th>
          <th scope="col">Data da transferencia</th>
          <th scope="col">Data de agendamento</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="transfer in formattedTransfers" :key="transfer.id">
          <td>{{ transfer.originAccount }}</td>
          <td>{{ transfer.destinationAccount }}</td>
          <td>R$ {{ transfer.transferAmount }}</td>
          <td>R$ {{ calculateFee(transfer).toFixed(2) }}</td>
          <td>{{ transfer.transferDate }}</td>
          <td>{{ transfer.schedulingDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { Transfer } from '@/interfaces/Transfer';
import { defineComponent } from 'vue';
import { format } from 'date-fns';

export default defineComponent({
  name: 'TableComponent',
  props: {
    transfers: Array as () => Transfer[],
  },
  computed: {
    formattedTransfers() {
      if (this.transfers) {
        return this.transfers.map(transfer => ({
          ...transfer,
          transferDate: this.formatDate(transfer.transferDate),
          schedulingDate: this.formatDate(transfer.schedulingDate),
        }));
      }
      return [];
    },
  },
  methods: {
    formatDate(date: string) {
      return format(new Date(date), 'dd/MM/yyyy');
    },
    calculateFee(transfer: Transfer) {
      const fee = transfer.fee || 0;
      return (fee / 100) * transfer.transferAmount;
    },
  },
});
</script>
<style scoped>
.table-container {
  width: 90%;
  max-height: 70vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
