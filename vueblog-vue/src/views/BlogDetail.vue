<template>
  <div>
    <Header/>
    <div class="m-blog">
      <h2>{{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-show="isOwnBlog">
        <router-link class="link" :to="{name: 'BlogEdit', params: {blogId: blog.id}}">编辑</router-link>
      </el-link>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header"
import 'github-markdown-css/github-markdown-light.css'

export default {
  name: "BlogDetail.vue",
  components: {Header},
  created() {
    const blogId = this.$route.params.blogId
    const MarkdownIt = require('markdown-it')
    const md = new MarkdownIt();
    this.$axios
        .get('/blog/' + blogId)
        .then(res => {
          const blog = res.data.data
          this.blog.id = blog.id
          this.blog.title = blog.title
          this.blog.content = md.render(blog.content)
          this.isOwnBlog = blog.userId === this.$store.getters.getUser.id
        })
  },
  data() {
    return {
      blog: {
        id: '',
        title: '',
        content: ''
      },
      isOwnBlog: false
    }
  }
}
</script>

<style scoped>
.m-blog {
  width: 100%;
  /*min-height: 700px;*/
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 25px 20px;
}

.link {
  color: #606266;
}

.link:hover {
  color: #5A9CF8
}
</style>
