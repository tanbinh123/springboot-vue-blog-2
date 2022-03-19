<template>
  <div>
    <Header/>

    <div class="block">
      <el-timeline>
        <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
          <el-card>
            <h3>
              <router-link class="link" :to="{name: 'BlogDetail', params: {blogId: blog.id}}">{{
                  blog.title
                }}
              </router-link>
            </h3>
            <p>{{ blog.description }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>

    <el-pagination
        class="m-page"
        background
        layout="prev, pager, next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        @current-change="page">
    </el-pagination>
  </div>
</template>

<script>
import Header from "../components/Header"

export default {
  name: "Blogs.vue",
  components: {
    Header
  },
  data() {
    return {
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5
    }
  },
  methods: {
    page(currentPage) {
      this.$axios.get('/blogs?pageNumber=' + currentPage).then(res => {
        this.blogs = res.data.data.records
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.pageSize = res.data.data.size
      })
    }
  },
  created() {
    this.page(1)
  }
}
</script>

<style scoped>
.m-page {
  text-align: center;
}

.link {
  color: #000;
  text-decoration: none;
}

.link:hover {
  color: #5A9CF8;
}
</style>
