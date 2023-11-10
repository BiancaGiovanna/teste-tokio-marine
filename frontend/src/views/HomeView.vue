<!-- HomeView.vue -->
<template>
  <main class="bg-light">
    <h1>Lista de Usuários</h1>
    <TableComponent :users="users" />
  </main>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TableComponent from '@/components/TableComponent.vue';

interface User {
  name: string;
  email: string;
}

export default defineComponent({
  name: 'HomeView',
  components: {
    TableComponent,
  },
  data() {
    return {
      users: [
        { name: 'Usuário 1', email: 'usuario1@example.com' },
        { name: 'Usuário 2', email: 'usuario2@example.com' }
      ] as User[]
    };
  },
});
</script>

<style scoped>
main {
  height: 88vh;
  display: flex;
  align-items: center;
  flex-direction: column;
}
</style>
