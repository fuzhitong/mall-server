define(['utils/ajax'],function (ajax) {
  let vm = null
  const CTX = '/mall-account'
  const ResponseCode = {
    SUCCESS: '0000',
    NO_PERMISSION: '1111',
    FAIL: '9999',
    UNLOGIN: '2323',
    SYSTEM_EXCEPTION_ERROR: '-1'
  }

  function handleResponse (response, success, failed, {failedW}) {
    const data = response.data
    if (data.code === ResponseCode.SUCCESS) {
      success(data)
    } else if (data.code === ResponseCode.UNLOGIN) {
      if (vm.$route.path !== '/login') {
        vm.$router.push({
          path: '/login',
          query: {
            redirect: window.location.href
          }
        })
      }
    } else {
      if (failedW) {
        alert(data.msg)
      }
      failed(data)
    }
  }

  return {
    setContext(context) {
      vm = context
    },
    ResponseCode: ResponseCode,
    /**
     * post默认成功失败都有弹窗
     * successW 成功是否弹窗
     * failedW 失败是否弹窗
     * extra 额外的参数
     */
    makePost (url, options = {}) {
      let _options = {extra: {}, successW: true, failedW: true}
      Object.assign(_options, options)
      return function (params = {}) {
        return new Promise((resolve, reject) => {
          ajax.fPost(CTX + url, {
            ..._options.extra,
            ...params
          }).then(response => {
            handleResponse(response, res => {
              resolve(res)
            }, res => reject(res), _options)
          }).catch(() => {
            if (_options.failedW) {
              alert('网络异常')
            }
            reject({
              msg: '网络异常'
            })
          })
        })
      }
    },
    /**
     * post Json格式的数据
     * post默认成功失败都有弹窗
     * successW 成功是否弹窗
     * failedW 失败是否弹窗
     * extra 额外的参数
     */
    makeJPost (url, options = {}) {
      let _options = {extra: {}, successW: true, failedW: true}
      Object.assign(_options, options)
      return function (params = {}) {
        return new Promise((resolve, reject) => {
          ajax.jPost(CTX + url, {
            ..._options.extra,
            ...params
          }).then(response => {
            handleResponse(response, res => {
              resolve(res)
            }, res => reject(res), _options)
          }).catch(() => {
            if (_options.failedW) {
              alert('网络异常')
            }
            reject({
              msg: '网络异常'
            })
          })
        })
      }
    },
    /**
     * get默认成功无弹窗，失败有弹窗
     * successW 成功是否弹窗
     * failedW 失败是否弹窗
     * extra 额外的参数
     */
    makeGet (url, options = {}) {
      let _options = {extra: {}, successW: false, failedW: true}
      Object.assign(_options, options)
      return function (params = {}) {
        return new Promise((resolve, reject) => {
          ajax.get(CTX + url, {
            params: {
              _r: new Date().getTime(),
              ..._options.extra,
              ...params
            }
          }).then(response => {
            handleResponse(response, res => {
              resolve(res)
            }, res => reject(res), _options)
          }).catch(() => {
            if (_options.failedW) {
              alert('网络异常')
            }
            reject({
              msg: '网络异常'
            })
          })
        })
      }
    },
    /**
     * get默认成功无弹窗，失败有弹窗
     * successW 成功是否弹窗
     * failedW 失败是否弹窗
     * extra 额外的参数
     */
    makeGetWithPath (url, options = {}) {
      let _options = {extra: {}, successW: false, failedW: true}
      Object.assign(_options, options)
      return function (path, params = {}) {
        return new Promise((resolve, reject) => {
          ajax.get(CTX + url + '/' + path, {
            params: {
              _r: new Date().getTime(),
              ..._options.extra,
              ...params
            }
          }).then(response => {
            handleResponse(response, res => {
              resolve(res)
            }, res => reject(res))
          }).catch(() => {
            if (_options.failedW) {
              alert('网络异常')
            }
            reject({
              msg: '网络异常'
            })
          })
        })
      }
    }
  }

})
