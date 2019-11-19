define(['lodash'], function(lodash){
  return {
    install (Clazz) {
      Clazz.prototype.$lodash = lodash
      const isEmpty = lodash.isEmpty
      // 日期类型的Empty,数字类型的Empty
      Clazz.prototype.$lodash.isEmpty = (obj) => {
        return !(typeof obj === 'number') && !(obj instanceof Date) && isEmpty(obj)
      }
      Clazz.prototype.$trim = lodash.trim
    }
  }
})
