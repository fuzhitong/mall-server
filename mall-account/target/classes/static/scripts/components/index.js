define(['components/CMessage', 'components/CAvatarCropper'], function(CMessage, AvatarCropper){
  return {
    install (Vue) {
      Vue.component('c-footer', {
        // 选项
        template: `
        <div class="n-footer">
            <div class="nf-link-area clearfix">
                <ul class="lang-select-list">
                    <li><a href="javascript:void(0)" class="lang-select-li current">简体</a>|</li>
                    <li><a href="#" data-lang="zh_TW" class="lang-select-li">繁体</a>|</li>
                    <li><a href="#" class="lang-select-li">English</a>|</li>
                    <li><a href="#" target="_blank">常见问题</a>|</li>
                    <li><a id="msg-privacy" href="#" target="_blank">隐私政策</a></li>
                </ul>
            </div>
            <p class="nf-intro">xx公司版权所有-京ICP备xxx-<a class="beian-record-link" target="_blank" href="#"><span><img
                    src="./assets/imgs/ghs.png"></span>京公网安备##号</a>-京ICP证##号</p>
        </div>
        `
      })

      Vue.component('c-form', {
        template: `
        <form @submit.prevent="onSubmit" @keyup.13="onEnter">
          <slot></slot>
        </form>
        `,
        methods: {
          onSubmit () {
            this.$emit('submit')
          },
          onEnter () {
            this.$emit('enter')
          }
        }
      })

      let id = 0
      Vue.component('c-form-input', {
        template: `
        <div class="form-section" :class="classes">
          <label class="input-label" :for="id">{{label}}</label>
          <input @focus="focus=true" :id="id" @blur="focus=false" class="input-text" type="text" :placeholder="placeholder" :value="value" @input="$emit('input', $event.target.value)">
        </div>
        `,
        data () {
          return {
            id: id++,
            focus: false
          }
        },
        props: {
          label: '',
          placeholder: '',
          value: ''
        },
        computed: {
          classes () {
            const classes = []
            if (this.focus) {
              classes.push('form-section-focus')
            }
            if (this.focus || this.value !== '') {
              classes.push('form-section-active')
            }
            return classes
          }
        }
      })
      Vue.component('c-message', CMessage)
      Vue.component('c-avatar-cropper', AvatarCropper)
    }
  }
  
})
