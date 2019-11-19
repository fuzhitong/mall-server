define(['vue', 'vuex', 'store/modules/index'],function (vue, vuex, modules) {
  vue.use(vuex)
  return new vuex.Store({
    modules,
    strict: true
  })
})
