import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home'
import store from '@/store'
import {Message} from 'element-ui'
import {getToken} from '@/request/token'

Vue.use(Router)

const originalPush = Router.prototype.push
Router.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject){
    return originalPush.call(this, location, onResolve, onReject)
  }
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/write/:id?',
    component: r => require.ensure([], () => r(require('@/views/blog/BlogWrite')), 'blogwrite'),
    meta: {
      requireLogin: true
    },
  },
  {
    path: '',
    name: 'Home',
    component: Home,
    children: [
        {
          path:'/',
          component: r => require.ensure([], () => r(require('@/views/Index')), 'index')
        },
        {
          path: '/view/:id',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogView')), 'blogview')
        },
        {
          path:'/:type/all',
          component: r => require.ensure([], () => r(require('@/views/category/BlogAllCategoryTag')),
              'blogallcategorytag')
        },
        {
          path:'/:type/:id',
          component: r => require.ensure([], () => r(require('@/views/category/BlogCategoryTag')),
          'blogcategorytag')
        },
        {
          path:'/archives/:year?/:month?',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogArchive')), 'blogarchive')
        }
    ]
  },
  {
    path: '/register',
    component: r => require.ensure([], () => r(require('@/views/Register')), 'register')
  },
  {
    path: '/login',
    component: r => require.ensure([], () => r(require('@/views/Login')), 'login')
}
  
]

const router = new Router({
  routes
}
)

router.beforeEach((to, from, next) => {
  
  if (getToken()) {
    if (to.path === '/login') {
      next({path: '/'});
    } else {
      // 判断是否存在用户信息
      if (store.state.account.length === 0) {
        store.dispatch('getUserInfo').then(
          res => {
            next();
          }
        ).catch(() => {
            Message({
              type: 'warning',
              showClose: true,
              message: '登录已过期'
            });
            next({path: '/'});
        })
      } else {
        next();
      }
    }
  } else {
    if (to.matched.some(r => r.meta.requireLogin)) {
      Message({
        type: 'warning',
        showClose: true,
        message: '请先登录哦'
      })
    } else {
      next();
    }
  }
})



export default router
