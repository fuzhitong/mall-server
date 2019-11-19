define(['@mixins/user',
  'css!@css/assets/css/layout',
  'css!@css/assets/css/modal',
  'css!@css/assets/css/reset'], function (user) {
  return {
    mixins: [user],
    // 选项
    template: `
    <div>
        <div class="wrapper blockimportant">
            <div class="wrap">
                <div class="layout bugfix_ie6 dis_none">
                    <div class="n-logo-area clearfix">
                        <a id="logoutLink" class="fl-r logout"
                            @click="onLogout"
                           href="javascript:void(0)">
                            退出
                        </a>
                    </div>
                    <!--头像 名字-->
                    <div class="n-account-area-box">
                        <div class="n-account-area clearfix">
                            <div class="na-info">
                                <p class="na-num"></p>
                                <p class="na-name">{{$_user_userInfo.userName}}</p>
                            </div>
                            <div class="na-img-area fl-l">
                                <!--na-img-bg-area不能插入任何子元素-->
                                <div class="na-img-bg-area">
                                    <img :src="$_user_userInfo.icon">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        
                <div class="layout">
                    <div class="n-main-nav clearfix">
                        <ul>
                            <li :class="$route.path | active('/home/userInfo')">
                                <router-link title="个人信息" to="/home/userInfo">个人信息</router-link>
                                <em class="n-nav-corner"></em>
                            </li>
                            <li :class="$route.path | active('/home/editPassword')">
                                <router-link title="修改密码" to="/home/editPassword">修改密码</router-link>
                                <em class="n-nav-corner"></em>
                            </li>
                        </ul>
                    </div>
                    <router-view></router-view>
                </div>
                <div class="logout_wap">
                    <a class="btnadpt bg_white"
                       href="javascript:void(0)">退出</a>
                </div>
            </div>
        </div>
        <c-footer/>
    </div>
    `,
    filters: {
      active (val, cVal) {
        if (val === cVal) {
          return 'current'
        }
        return ''
      }
    },
    data () {
      return {
        dialog: {
          editUser: false
        }
      }
    },
      created(){
          this.$service.account.refreshUserInfo()
      },

    methods: {
      onLogout () {
        this.$service.account.logout().then(() => {
          this.$router.push('/login')
        })
      }
    }
  }
})