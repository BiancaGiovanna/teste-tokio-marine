
<template>
  <HeaderComponent />
  <router-view></router-view>
  <FooterComponent />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import HeaderComponent from './components/HeaderComponent.vue';
import FooterComponent from './components/FooterComponent.vue';
export default defineComponent({
  components: {
    HeaderComponent,
    FooterComponent,
  },
})

</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
