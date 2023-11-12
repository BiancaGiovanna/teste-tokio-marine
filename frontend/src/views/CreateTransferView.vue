
<template>
  <main>
    <FormTransferComponent />
  </main>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import FormTransferComponent from '@/components/FormTransferComponent.vue';

export default defineComponent({
  name: 'CreateTransferView',
  components: {
    FormTransferComponent,
  },
})

</script>
<style scoped>
main {
  height: 88vh;
  display: flex;
  align-items: center;
  flex-direction: column;
}
</style>