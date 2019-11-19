function getView(name) {
	return function (resolve) {
		require(['view/' + name], function (obj) {
			resolve(obj)
		})
	}
}



define(['vue', 'vue-router'], function (Vue, VueRouter) {
	// 把vue-router组件加载到vue上去
	Vue.use(VueRouter)
  const routes = [
    {
      path: '',
      redirect: '/login'
    },
    {
      path: '/login',
      component: getView('login')
    },
    {
      path: '/register',
      component: getView('register')
    },
    {
      path: '/home',
      component: getView('home'),
      children: [
        {
          path: '',
          redirect: 'userInfo',
        },
        {
          path: 'userInfo',
          component: getView('home/userInfo')
        },
        {
          path: 'editPassword',
          component: getView('home/editPassword')
        }
      ]
    }
  ]
  return new VueRouter({
		routes: routes
	})
});