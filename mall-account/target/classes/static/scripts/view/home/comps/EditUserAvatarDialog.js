define(['@mixins/user'], function(user) {
  return {
    mixins: [user],
    // 选项
    template: `
    <div>
      <div class="modal fade modal-edit-user" :class="dialog.show?'in':false" v-show="backdrop.open">
        <div class="modal-header">
          <span class="title">修改头像</span>
          <a class="close" href="javascript: void(0);" @click="close"><i class="iconfont">&#58882;</i></a>
        </div>
        <div class="modal-body">
          <div style="text-align: center">
            <div class="na-img-bg-area"><img v-if="userAvatar" :src="userAvatar" style="width: 200px;height:200px"></div>
            <button type="button" class="btn btn-primary" id="set-avatar">上传头像</button>
          </div>
          <c-avatar-cropper
              trigger="#set-avatar"
              upload-url="./api/upload"
              @uploaded="handleUploaded"
          ></c-avatar-cropper>
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
        userAvatar: null,
        backdrop: {
          open: false,
          show: false
        },
        dialog: {
          show: false
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
        this.userAvatar = this.$_user_userInfo.icon
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
        this.$service.account.updateUserInfo({
          icon: this.userAvatar
        }).then(() => {
          this.$service.account.refreshUserInfo()
          this.close()
        })
      },
      handleUploaded(res) {
        this.userAvatar = res.data.fileUrl;
      }
    }
  }
})