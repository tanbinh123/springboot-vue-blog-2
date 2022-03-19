import axios from "axios"
import ElementUI from 'element-ui'
import router from './router'
import store from './store'

axios.defaults.baseURL = 'http://localhost:8081'

// axios 全局拦截器
// 前置拦截
axios.interceptors.request.use(config => {
  // 判断token是否存在，即是否登录，如果已登录请求时候在head中添加token
  if (store.state.token) {
    config.headers.common['Authorization'] = store.state.token
  }
  return config
})

// 后置拦截
axios.interceptors.response.use(
  // 成功返回处理
  response => {
    let res = response.data
    console.log(res)

    if (res.code === 200) {
      return response
    }

    ElementUI.Message.error(res.msg, {duration: 3 * 1000})
    return Promise.reject(response.data.msg)
  },
  // 返回报错处理
  error => {
    console.log(error)
    if (error.response.data) {
      error.message = error.response.data.msg
    }

    if (error.response.status === 401) {
      store.commit('REMOVE_USERINFO')
      router.push('/login')
    }
    ElementUI.Message.error(error.message, {duration: 3 * 1000})
    return Promise.reject(error)
  }
)
