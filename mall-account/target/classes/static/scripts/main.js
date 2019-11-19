require.config({
  baseUrl: 'scripts',
  map: {
    '*': {
      'css': 'lib/require.css.min'
    }
  },
  paths: {
    'vue': 'lib/vue',
    'vuex': 'lib/vuex.min',
    'vue-router': 'lib/vue-router',
    'qs': 'lib/qs.min',
    'lodash': 'lib/lodash.min',
    'axios': 'lib/axios.min',
    '$components': 'components/index',
    '$services': 'services/index',
    '$utils': 'utils/index',
    '$store': 'store/index',
    'router': 'router',
    '@mixins': 'view/mixins',
    '@css': '..'
  }
})

require(['vue', '$components', 'router', '$services', '$utils', '$store', 'services/serviceFactory'], function (Vue, components, router, services, utils, store, factory) {
  // 加载自定义组件
  Vue.use(components)
  Vue.use(services)
  Vue.use(utils)
  const vm = new Vue({
    router,
    store
  }).$mount('#app')
  factory.setContext(vm)
});