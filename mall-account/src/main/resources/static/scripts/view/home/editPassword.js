define(['css!@css/assets/css/pwd'], function() {
  return {
    // 选项
    template: `
    <div class="n-frame">
      <c-form @keyup.native="onKey" @enter="onSave">
        <div class="regbox">
          <h4 class="tit_normal">
            旧密码
          </h4>
          <div class="listwrap">
            <div class="inputbg">
              <label class="labelbox" :class="errPassword" style="width: 100%"><input placeholder="请输入旧密码" type="password" v-model="form.password"></label>
            </div>
          </div>
          <h4 class="tit_normal">
            密码
          </h4>
          <div class="listwrap">
            <div class="inputbg"><label class="labelbox" :class="errNewPassword" style="width: 100%"><input placeholder="请输入新密码" type="password" v-model="form.newPassword"></label></div>
            <div class="inputbg"><label class="labelbox" style="width: 100%"><input placeholder="请确认重复密码" type="password" v-model="form.rePassword"></label></div>
            <div class="err_tip" style="display: block;" v-show="errShow">
              <div class="dis_box"><em class="icon_error"></em><span>{{err.msg}}</span></div>
            </div>
          </div>
          <div class="err_tip send-left-times"></div>
          <div style="text-align: center">
            <a href="javascript:void(0);" class="btn btn-primary" @click="onSave">确定</a>
          </div>
        </div>
      </c-form>
    </div>
    `,
    data () {
      return {
        form: {
          password: '',
          newPassword: '',
          rePassword: ''
        },
        err: {
          password: false,
          newPassword: false,
          rePassword: false,
          msg: ''
        }
      }
    },
    computed: {
      errPassword () {
        return this.err.password ? 'err_label' : ''
      },
      errNewPassword () {
        return this.err.newPassword ? 'err_label' : ''
      },
      errShow () {
        return this.err.password || this.err.newPassword || this.err.rePassword
      }
    },
    created () {
    },
    methods: {
      clearForm () {
        this.form.password = ''
        this.form.newPassword = ''
        this.form.rePassword = ''
      },
      onKey () {
        this.err.password = false
        this.err.newPassword = false
      },
      validate () {
        if (this.$lodash.isEmpty(this.form.password)) {
          this.err.password = true
          this.err.msg = '请输入密码'
          return false
        }
        if (this.$lodash.isEmpty(this.form.newPassword)) {
          this.err.newPassword = true
          this.err.msg = '请输入新密码'
          return false
        }
        if (this.form.newPassword !== this.form.rePassword) {
          this.err.newPassword = true
          this.err.msg = '您两次输入的密码不一致'
          return false
        }
        return true
      },
      onSave () {
        if (this.validate()) {
          this.$service.account.setPassword({
            originalPwd: this.form.password,
            newPwd: this.form.newPassword,
            conPwd: this.form.rePassword
          }).then(() => {
            this.$emit('ok')
            this.clearForm()
            this.$alert('修改成功')
          }).catch(res => {
            this.err.password = true
            this.err.msg = res.msg
          })
        }
      }
    }
  }
})