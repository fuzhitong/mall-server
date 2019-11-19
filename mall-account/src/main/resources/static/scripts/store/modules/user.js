define(function () {
  return {
    namespaced: true,
    state: {
      isLogin: false,
      userInfo: {
        userName: 'test',
        mobile: '',
        email: '',
        icon: './assets/imgs/photo.jpg'
      },
      departments: [],
      authorityInfo: []
    },
    actions: {
      login ({ commit }) {
        commit('setLoginStatus', true)
      },
      logout ({ commit }) {
        commit('setLoginStatus', false)
      },
      setUserInfo ({ commit }, data) {
        commit('setUserInfo', data)
      }
    },
    mutations: {
      setLoginStatus (state, data) {
        state.isLogin = data
      },
      setUserInfo (state, data) {
        state.userInfo = data
      }
    }
  }

})