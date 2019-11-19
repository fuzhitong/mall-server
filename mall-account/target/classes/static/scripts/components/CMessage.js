define(function(){
  return {
    template: `
        <div>
          <div class="modal fade modal-alert" :class="show?'in':''"  v-if="visible">
            <div class="modal-bd">
              <div class="text">
                <h3>{{message}}</h3>
              </div>
              <div class="actions">
                <button class="btn btn-gray" @click="handleAction('cancel')" v-if="$type!=='alert'">{{cancelButtonText}}</button>
                <button class="btn btn-primary" @click="handleAction('confirm')">{{confirmButtonText}}</button>
              </div>
              <a class="close" href="javascript: void(0);" @click="handleAction('cancel')"><i class="iconfont">&#58882;</i></a>
            </div>
          </div>
          <div class="modal-backdrop fade" :class="show?'in':''" v-if="visible"></div>
        </div>
        `,
    props: {
      modal: {
        default: true
      },
      lockScroll: {
        default: true
      },
      showClose: {
        type: Boolean,
        default: true
      },
      closeOnClickModal: {
        default: true
      },
      closeOnPressEscape: {
        default: true
      }
    },
    data () {
      return {
        $type: '',
        visible: false,
        show: false,
        uid: 1,
        title: undefined,
        message: '',
        type: '',
        customClass: '',
        showInput: false,
        inputValue: null,
        inputPlaceholder: '',
        inputPattern: null,
        inputValidator: null,
        inputErrorMessage: '',
        showConfirmButton: true,
        showCancelButton: false,
        action: '',
        confirmButtonText: '',
        cancelButtonText: '',
        confirmButtonLoading: false,
        cancelButtonLoading: false,
        confirmButtonClass: '',
        confirmButtonDisabled: false,
        cancelButtonClass: '',
        editorErrorMessage: null,
        callback: null,
        isOnComposition: false
      }
    },
    watch: {
      visible (val) {
        if (val) this.uid++
        setTimeout(() => {
          this.show = val
        }, 100)
      }
    },
    methods: {
      handleAction (action) {
        this.action = action
        this.doClose()
      },
      doClose () {
        if (!this.visible) return
        this.visible = false
        if (this.action) this.callback(this.action, this)
      }
    }
  }
})