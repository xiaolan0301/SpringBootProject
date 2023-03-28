import axios from "axios";

// 创建axios实例
const service = axios.create({
  baseURL: "http://oaalan.free.idcfengye.com", // api 的 base_url
  timeout: 30000 // 请求超时时间
});

// http request 拦截器
service.interceptors.request.use(config => {
    let token = window.localStorage.getItem("token") || "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNLMwNDY1trAw0lEqLU4t8kwBikGYeYm5qUAtiSm5mXlKtQDH7sj-QgAAAA.T1-j9DfQgLHy3N4_LWtZl1-0T0qHPULb58lYYTtOE8MnkLNv4D5DiXJWlT5kv5k8lBAL07ggFukiZGIXP4o0SA";
    if (token != "") {
      config.headers["token"] = token;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  });
// http response 拦截器
service.interceptors.response.use(response => {
    if (response.data.code == 204) {
      // debugger
      // 替换# 后台获取不到#后面的参数
      let url = window.location.href.replace('#', 'guiguoa')
      window.location = '	http://oaalan.free.idcfengye.com/admin/wechat/authorize?returnUrl=' + url
    } else {
      if (response.data.code == 200) {
        return response.data;
      } else {
        // 209没有权限 系统会自动跳转授权登录的，已在App.vue处理过，不需要提示
        if (response.data.code != 209) {
          alert(response.data.message || "error");
        }
        return Promise.reject(response);
      }
    }
  },
  error => {
    return Promise.reject(error.response);   // 返回接口返回的错误信息
  });

export default service;
