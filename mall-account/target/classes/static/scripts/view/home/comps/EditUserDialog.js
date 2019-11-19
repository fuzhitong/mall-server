define(['@mixins/user'], function(user) {
  return {
    mixins: [user],
    // 选项
    template: `
    <div>
      <div class="modal fade modal-edit-user" :class="dialog.show?'in':false" v-show="backdrop.open">
        <div class="modal-header">
          <span class="title">修改个人信息</span>
          <a class="close" href="javascript: void(0);" @click="close"><i class="iconfont">&#58882;</i></a>
        </div>
        <div class="modal-body">
          <div class="form-box clearfix">
            <c-form-input class="form-name" label="姓名" placeholder="姓名" v-model="form.realName"/>
            <c-form-input class="form-phone" label="手机号" placeholder="11位手机号" v-model="form.mobile"/>
            <c-form-input class="form-address-detail" label="邮箱" placeholder="example@example.com" v-model="form.email"/>
          </div>
        </div>
        <div class="modal-footer">
          <a href="javascript:void(0);" class="btn btn-primary" @click="onSave">保存</a>
          <a href="javascript:void(0);" class="btn btn-gray" @click="close">取消</a>
        </div>
      </div>
      <div class="modal-backdrop fade" :class="backdrop.show?'in':false" v-if="backdrop.open"></div>
    </div>
    `,
    model: {
      prop: 'visible',
      event: 'change'
    },
    props: {
      visible: Boolean,
      data: {
        type: Object,
        default () {
          return {
            userName: '',
            realName: '',
            mobile: '',
            email: ''
          }
        }
      }
    },
    data () {
      return {
        backdrop: {
          open: false,
          show: false
        },
        dialog: {
          show: false
        },
        form: {
          userName: '',
          realName: '',
          mobile: '',
          email: ''
        }
      }
    },
    computed: {
      showClass () {
        return this.visible ? 'in' : ''
      }
    },
    created () {
      if (this.visible) {
        this._open()
      }
    },
    watch: {
      visible (val, old) {
        if (old !== val) {
          if (val) {
            this._open()
          } else {
            this._close()
          }
        }
      }
    },
    methods: {
      _open () {
        this.form = {
          userName: this.$_user_userInfo.userName,
          realName: this.$_user_userInfo.realName,
          mobile: this.$_user_userInfo.mobile,
          email: this.$_user_userInfo.email
        }
        this.backdrop.open = true
        setTimeout(() => {
          this.backdrop.show = true
          this.dialog.show = true
        }, 100)
      },
      _close () {
        this.dialog.show = false
        setTimeout(() => {
          this.backdrop.show = false
          this.backdrop.open = false
        }, 400)
      },
      open () {
        this.$emit('change', true)
      },
      close () {
        this.$emit('change', false)
      },
      onSave () {
        this.$service.account.updateUserInfo(this.form).then(() => {
          this.$service.account.refreshUserInfo()
          this.close()
        })
      }
    }
  }
})