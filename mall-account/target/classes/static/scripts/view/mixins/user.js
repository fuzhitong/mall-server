/**
 * 用户信息
 */
define(['vuex'], function ({mapActions, mapState}) {
  return {
    computed: {
      ...mapState('user', {
        $_user_userInfo: state => state.userInfo,
        $_user_isLogin: state => state.isLogin
      })
    },
    methods: {
      ...mapActions('user', {
        $_user_login: 'login',
        $_user_logout: 'logout'
      })
    }
  }
})
