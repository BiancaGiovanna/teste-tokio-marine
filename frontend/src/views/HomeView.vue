  <!-- HomeView.vue -->
<template>
  <main class="bg-light">
    <h1>Histórico de agendamentos</h1>

    <TableComponent :transfers="transfers" />
    <div>
      <router-link to="/insert" class="btn btn-success mt-3">Novo agendamento</router-link>
    </div>
  </main>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import axios from 'axios';
import TableComponent from '@/components/TableComponent.vue';
import { Transfer } from '@/interfaces/Transfer';
import urlAPI from '@/utils/urlAPI';

export default defineComponent({
  name: 'HomeView',
  components: {
    TableComponent,
  },
  data() {
    return {
      transfers: [] as Transfer[],
    };
  },
  mounted() {
    this.getTransfers();
  },
  methods: {
    async getTransfers() {
      try {
        const response = await axios.get(`${urlAPI}/all`);
        this.transfers = response.data;
      } catch (error) {
        console.error('Erro ao obter transferências:', error);
      }
    },
  }
});
</script>

<style scoped >
main {
  height: 88vh;
  /* margin-bottom: 15px; */
  display: flex;
  align-items: center;
  flex-direction: column;
}
</style>
