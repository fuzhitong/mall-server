define(['services/src/accountService'], function(account){
  return {
    install (Vue) {
      Vue.prototype.$service = {
        account
      }
    }
  }
})
