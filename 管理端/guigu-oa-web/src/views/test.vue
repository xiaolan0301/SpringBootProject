<template>
  <div>
    <div>账号切换</div>
    <button @click="wjl()" type="default" size="mini">王经理</button>
    <button @click="zhangsan()" type="default" size="mini">张三经理</button>
    <button @click="admin()" type="default" size="mini">小王</button>
    <button @click="lisi()" type="default" size="mini">李四总经理</button>
    <div>当前token：{{ token }}</div>
  </div>
</template>

<script>
export default {
  name: "Test",

  data() {
    return {
      token: ''
    };
  },

  created(){
    this.token = window.localStorage.getItem('token')
  },

  methods: {
    wjl() {
      window.localStorage.setItem('token', '');
      let token = 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDM3NjMwNDIx1FEqLU4t8kxRsjKCMPMSc1OBWsqzcpRqAb4pVZBAAAAA.-bW4hJ5UptFiCRsL6jVUa4XbgVwdsfVGfP8gJ-tOVbp7D3JOb8sylbBDLUhTZt6FJsYhlA5uYz8HSRck_pbaCA'
      window.localStorage.setItem('token', token);
      this.token = window.localStorage.getItem('token')
    },

    zhangsan() {
      window.localStorage.setItem('token', '');
      let token = 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNLMwNDY3NDY11lEqLU4t8kxRsjKHMPMSc1OBWqoyEvPSixPzlGoBYZ37H0UAAAA.5Is6E0qpYmDpyK3CI3tJPMNBc2n0WY87fs9cttT6KGnzODXOFIyxJPUM2iQ-vlmqFWqMi7mz1hsDcCohkYXGow'
      window.localStorage.setItem('token', token);
      this.token = window.localStorage.getItem('token')
    },

    admin() {
      window.localStorage.setItem('token', '');
      let token = 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNLMwNDY3NLIw01EqLU4t8kwBikGYeYm5qUAtiSm5mXlKtQDKX_iMQgAAAA.-CCU08ar4XOjpWvTKRb_0MGHDL0BGnjfztbRyf1ZlkypSP8rRo3-kPZzNqMR4TaAAkXyVIgbj5xAM4NSojrryA'
      window.localStorage.setItem('token', token);
      this.token = window.localStorage.getItem('token')
    },

    lisi() {
      window.localStorage.setItem('token', '');
      let token = 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNLMwNDY3NDY011EqLU4t8kxRsjKBMPMSc1OBWnIyizOVagFpa8h2QQAAAA._kfJ7xvLx71jOD9-oMLFT4EHowsrMNXZPwCF36qFw1IRWVsy6mwcuLSF3h2PKD2XXoSyeGsfCBO7qXoBzEaRaA'
      window.localStorage.setItem('token', token);
      this.token = window.localStorage.getItem('token')
    }
  }
}
</script>
