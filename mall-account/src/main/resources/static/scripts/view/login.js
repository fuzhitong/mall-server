define(['css!@css/assets/css/login'], function () {
  return {
    // 选项
    template: `
    <div style="height: 100%;width: 100%;">
        <div class="wrapper">
            <div class="wrap">
                <div class="layout_panel">
                    <div class="layout_login">
                        <div class="mainbox">
                            <!-- header s -->
                            <div class="lgnheader">
                                <div class="header_tit t_c">
                                    <img src="./assets/imgs/logo.png" class="milogo">
                                    <h4 class="header_tit_txt">帐号登录</h4>
                                    <div class="site_info"></div>
                                </div>
                            </div>
                            <div class="tabs-con tabs_con now">
                                <div>
                                    <div class="login_area">
                                        <c-form @keyup.native="onKey" @enter="handleLogin">
                                            <div class="loginbox c_b">
                                                <!-- 输入框 -->
                                                <div class="lgn_inputbg c_b login-panel pwdLogin">
                                                    <!--验证用户名-->
                                                    <label class="labelbox login_user c_b" :class="errUserName">
                                                        <input class="item_account" autocomplete="off" type="text"
                                                               v-model="form.userName" placeholder="用户名">
                                                    </label>
                                                    <label class="labelbox pwd_panel c_b" :class="errPassword">
                                                        <input class="item_account" type="password" placeholder="密码"
                                                               v-model="form.password" autocomplete="off" name="password">
                                                    </label>
                                                </div>
                                                <!-- 错误信息 -->
                                                <div class="err_tip" v-show="errShow" style="display: block;">
                                                    <div><em class="icon_error"></em><span class="error-con">{{err.msg}}</span></div>
                                                </div>
                                                <!-- 登录频繁 -->
                                                <div class="btns_bg">
                                                    <input class="btnadpt" @click="handleLogin" type="button" value="登录">
                                                </div>
                                                <div class="other_panel clearfix">
                                                    <div class="reverse">
                                                        <div class="n_links_area">
                                                            <router-link class="outer-link" to="/register">立即注册</router-link>
                                                        </div>
                                                    </div>
                                                    <!-- 其他登录方式 s -->
                                                    <!-- 其他登录方式 e -->
                                                </div>
                                            </div>
                                        </c-form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c-footer/>
    </div>
    `,
    data () {
      return {
        err: {
          msg: '',
          userName: false,
          password: false
        },
        form: {
          userName: '',
          password: ''
        }
      }
    },
    computed: {
      errUserName () {
        return this.err.userName ? 'error_bg' : ''
      },
      errPassword () {
        return this.err.password ? 'error_bg' : ''
      },
      errShow () {
        return this.err.userName || this.err.password
      }
    },
    created() {
      this.$service.account.refreshUserInfo().then(() => {
        this.onNextPage()
      })
    },
    methods: {
      onKey () {
        this.err.userName = false
        this.err.password = false
      },
      onNextPage() {
        const redirect = this.$route.query['redirect']
        if (this.$lodash.isEmpty(redirect)) {
          this.$router.replace('/home')
        } else {
          window.location.href = redirect
        }
      },
      handleLogin () {
        if (this.$lodash.isEmpty(this.form.userName)) {
          this.err.userName = true
          this.err.msg = '请输入帐号'
          return
        }
        if (this.$lodash.isEmpty(this.form.password)) {
          this.err.password = true
          this.err.msg = '请输入密码'
          return
        }
        this.$service.account.login(this.form).then(res => {
          this.onNextPage()
        }).catch(res => {
          this.err.userName = true
          this.err.msg = res.msg
        })
      }
    }
  }
})