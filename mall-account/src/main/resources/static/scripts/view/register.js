define(['css!@css/assets/css/login'], function () {
  return {
    // 选项
    template: `
    <div>
        <div class="wrapper">
            <div class="wrap">
                <div class="layout_panel">
                    <div class="layout">
                        <div class="mainbox">
                            <!-- header s -->
                            <div class="lgnheader">
                                <div class="header_tit t_c">
                                    <img src="./assets/imgs/logo.png" class="milogo">
                                    <h4 class="header_tit_txt">帐号注册</h4>
                                    <div class="site_info"></div>
                                </div>
                            </div>
                            <div class="tabs-con tabs_con now">
                                <div>
                                    <div class="login_area">
                                        <c-form @keyup.native="onKey" @enter="handleRegister">
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
                                                               v-model="form.password" autocomplete="off">
                                                    </label>
                                                    <label class="labelbox pwd_panel c_b">
                                                        <input class="item_account" type="password" placeholder="重复密码"
                                                               v-model="rePassword" autocomplete="off">
                                                    </label>
                                                </div>
                                                <!-- 错误信息 -->
                                                <div class="err_tip" v-show="errShow" style="display: block;">
                                                    <div><em class="icon_error"></em><span class="error-con">{{err.msg}}</span></div>
                                                </div>
                                                <!-- 登录频繁 -->
                                                <div class="btns_bg">
                                                    <input class="btnadpt" @click="handleRegister" type="button" value="注册">
                                                </div>
                                                <div class="other_panel clearfix">
                                                    <div class="reverse">
                                                        <div class="n_links_area">
                                                            <router-link class="outer-link" to="/login">返回登录</router-link>
                                                        </div>
                                                    </div>
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
        form: {
          userName: '',
          password: ''
        },
        rePassword: '',
        err: {
          userName: false,
          password: false,
          rePassword: false,
          msg: ''
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
        return this.err.userName || this.err.password || this.err.rePassword
      }
    },
    methods: {
      onKey () {
        this.err.userName = false
        this.err.password = false
      },
      validate () {
        if (this.$lodash.isEmpty(this.form.userName)) {
          this.err.userName = true
          this.err.msg = '请输入帐号'
          return false
        }
        if (this.$lodash.isEmpty(this.form.password)) {
          this.err.password = true
          this.err.msg = '请输入密码'
          return false
        }
        if (this.form.password !== this.rePassword) {
          this.err.password = true
          this.err.msg = '您两次输入的密码不一致'
          return false
        }
        return true
      },
      handleRegister () {
        if (this.validate()) {
          this.$service.account.register(this.form).then(() => {
            alert('注册成功')
            this.$router.push('/login')
          }).catch(res => {
            this.err.userName = true
            this.err.msg = res.msg
          })
        }
      }
    }
  }
})