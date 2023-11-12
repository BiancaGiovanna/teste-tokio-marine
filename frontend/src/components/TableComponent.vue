<template>
  <div class="table-responsive table-container">
    <table class="table table-striped table-bordered">
      <thead class="table-dark">
        <tr>
          <th scope="col">Conta de origem</th>
          <th scope="col">Conta de destino</th>
          <th scope="col">Valor transferido</th>
          <th scope="col">Valor da taxa aplicada</th>
          <th scope="col">Data de agendamento</th>
          <th scope="col">Data da transferencia</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="transfer in formattedTransfers" :key="transfer.id">
          <td>{{ transfer.originAccount }}</td>
          <td>{{ transfer.destinationAccount }}</td>
          <td>R$ {{ transfer.transferAmount }}</td>
          <td v-if="transfer.fee !== null">R$ {{ transfer.fee }}</td>
          <td v-else>-</td>
          <td>{{ transfer.schedulingDate }}</td>
          <td>{{ transfer.transferDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { Transfer } from '@/interfaces/Transfer';
import { defineComponent } from 'vue';
// import { format } from 'date-fns-tz';
import { format, parseISO } from 'date-fns';

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
    formatDate(date: string | Date | undefined | null) {
      if (date instanceof Date) {
        // Converte para UTC antes de formatar
        const utcDate = new Date(Date.UTC(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate()));
        return format(utcDate, 'dd/MM/yyyy');
      } else if (date && typeof date === 'string') {
        const parsedDate = parseISO(date); // Converte para objeto de data
        const utcDate = new Date(Date.UTC(parsedDate.getUTCFullYear(), parsedDate.getUTCMonth(), parsedDate.getUTCDate()));
        return format(utcDate, 'dd/MM/yyyy');
      }
      // Retorna vazio ou outra string indicando que a data não está disponível
      return '';
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
