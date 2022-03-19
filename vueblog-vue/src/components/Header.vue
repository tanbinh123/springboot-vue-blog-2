<template>
  <div class="m-content">
    <h1 v-if="hasLogin">{{user.username}} 的博客</h1>
    <h1 v-else>全部博客</h1>
    <div class="block">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
      <div style="font-weight: bold">{{ user.username }}</div>
    </div>
    <div class="m-action">
      <span><el-link href="/blogs">主页</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span><el-link type="success" href="/blog/add">发表博客</el-link></span>

      <el-divider direction="vertical"></el-divider>
      <span v-show="!hasLogin"><el-link type="primary" href="/login">登录</el-link></span>
      <span v-show="hasLogin"><el-link type="danger" @click="logout">退出</el-link></span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: {
        username: '请登录',
        avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
      },
      hasLogin: false
    }
  },
  methods: {
    logout() {
      this.$axios.post('/logout').then(res => {
        this.$store.commit('REMOVE_USERINFO')
        this.$router.push('/login')
      })
    }
  },
  created() {
    if (this.$store.getters.getUser.username) {
      this.user.username = this.$store.getters.getUser.username
      this.hasLogin = true
    }
    if (this.$store.getters.getUser.avatar) {
      this.user.avatar = this.$store.getters.getUser.avatar
    }
  }
}
</script>

<style scoped>
.m-content {
  max-width: 960px;
  margin: 0 auto;
  text-align: center;
}

.m-action {
  margin: 10px 0;
}
</style>
