define(function () {
  return {
    template: `
        <div>
            <input ref="input" type="file" :accept="mimes" @change="uploadImage" style="display:none">
        </div>
        `,
    props: {
      trigger: {
        type: [String, Element],
        required: true
      },
      uploadHandler: {
        type: Function,
      },
      uploadUrl: {
        type: String,
      },
      uploadHeaders: {
        type: Object,
      },
      uploadFormName: {
        type: String,
        default: 'file'
      },
      uploadFormData: {
        type: Object,
        default () {
          return {}
        }
      },
      cropperOptions: {
        type: Object,
        default () {
          return {
            aspectRatio: 1,
            autoCropArea: 1,
            viewMode: 1,
            movable: false,
            zoomable: false,
          }
        }
      },
      outputOptions: {
        type: Object,
        default () {
          return {
            width: 512,
            height: 512
          }
        }
      },
      outputMime: {
        type: String,
        default: 'image/jpeg'
      },
      outputQuality: {
        type: Number,
        default: 0.9
      },
      mimes: {
        type: String,
        default: 'image/png, image/gif, image/jpeg, image/bmp, image/x-icon'
      },
      labels: {
        type: Object,
        default () {
          return {
            submit: "提交",
            cancel: "取消"
          }
        }
      }
    },
    data () {
      return {}
    },
    mounted() {
      // listen for click event on trigger
      let trigger = typeof this.trigger == 'object' ? this.trigger : document.querySelector(this.trigger)
      if (!trigger) {
        this.$emit('error', 'No avatar make trigger found.', 'user')
      } else {
        trigger.addEventListener('click', this.pickImage)
      }
    },
    methods: {
      pickImage() {
        this.$refs.input.click()
      },
      uploadImage () {
        const fileInput = this.$refs.input
        const file = fileInput.files[0]
        const form = new FormData()
        const xhr = new XMLHttpRequest()
        const data = Object.assign({}, this.uploadFormData)
        for (let key in data) {
          form.append(key, data[key])
        }
        form.append(this.uploadFormName, file)
        this.$emit('uploading', form, xhr)
        xhr.open('POST', this.uploadUrl, true)
        for (let header in this.uploadHeaders) {
          xhr.setRequestHeader(header, this.uploadHeaders[header])
        }
        xhr.onreadystatechange = () => {
          if (xhr.readyState === 4) {
            let response = ''
            try {
              response = JSON.parse(xhr.responseText)
            } catch (err) {
              response = xhr.responseText
            }
            this.$emit('completed', response, form, xhr)
            if ([200, 201, 204].indexOf(xhr.status) > -1) {
              this.$emit('uploaded', response, form, xhr)
            } else {
              this.$emit('error', 'Image upload fail.', 'upload', xhr)
            }
          }
        }
        xhr.send(form);
      }
    }
  }
})