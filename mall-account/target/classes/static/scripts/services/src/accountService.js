define(['services/serviceFactory', '$store'], function(factory, store){
  return {
    login: factory.makePost('/api/system/login', {failedW: false}),
    logout: factory.makePost('/api/logout', {failedW: false}),
    register: factory.makePost('/api/user/register', {failedW: false}),
    updateUserInfo: factory.makePost('/api/personal/updateUserInfo'),
    setPassword: factory.makePost('/api/user/updatePassword', {failedW: false}),
    getUserInfo: factory.makeGet('/api/personal/getPersonalInfo', {failedW: false}),
    refreshUserInfo () {
      return new Promise(resolve => {
        this.getUserInfo().then(res => {
          const data = res.data
          if (res.code === factory.ResponseCode.SUCCESS) {

            store.dispatch('user/setUserInfo', data.userInfo)
            resolve(res)
          }
        })
      })
    }
  }
})
