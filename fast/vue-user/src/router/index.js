import { createWebHistory, createRouter } from 'vue-router'

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index.vue')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '/',
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/HomePage/index'),
        redirect: '/index/home',
        children: [
          {
            path: 'profile',
            component: () => import('@/views/system/user/profile/index'),
            hidden: true
          },
          {
            path: 'home',
            component: () => import('@/views/HomePage/home'),
            hidden: true,
          },
          {
            path: 'books',
            component: () => import('@/views/HomePage/books'),
            hidden: true,
          },
          {
            path: 'bookDetail/:id',
            component: () => import('@/views/HomePage/bookDetail'),
            hidden: true,
          },
          {
            path: 'cart',
            component: () => import('@/views/HomePage/cart'),
            hidden: true,
          },
          {
            path: 'checkout',
            component: () => import('@/views/HomePage/checkout'),
            hidden: true,
          },
            {
            path: 'recharge' ,
            component: () => import('@/views/HomePage/recharge'),
            hidden: true,
          },
        ],
        hidden: true
      }
    ]
  },
]


const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    return { top: 0 }
  },
})

export default router
