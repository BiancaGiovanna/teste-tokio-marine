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
        <tr v-for="transfer in transfers" :key="transfer.id">
          <td>{{ transfer.originAccount }}</td>
          <td>{{ transfer.destinationAccount }}</td>
          <td>R$ {{ transfer.transferAmount }}</td>
          <td v-if="transfer.fee !== null">R$ {{ transfer.fee }}</td>
          <td v-else>-</td>
          <td>{{ formatDate(transfer.schedulingDate) }}</td>
          <td>{{ formatDate(transfer.transferDate) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { Transfer } from '@/interfaces/Transfer';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'TableComponent',
  props: {
    transfers: Array as () => Transfer[],
  },

  methods: {
    formatDate(data: string) {
      if (data) {
        const date = new Date(data);
        const day = (date.getDate() + 1).toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear();
        return `${day}/${month}/${year}`;
      }
      return 'Data inválida';
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
