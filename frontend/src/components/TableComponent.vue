<template>
  <div class="table-responsive table-container">
    <table class="table table-striped table-bordered">
      <thead class="table-dark">
        <tr>
          <th scope="col">#</th>
          <th scope="col">Nome</th>
          <th scope="col">E-mail</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(user, index) in users" :key="index">
          <th scope="row">{{ index + 1 }}</th>
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

interface User {
  name: string;
  email: string;
}

export default defineComponent({
  name: 'TableComponent',
  props: {
    users: Array as () => User[],
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
