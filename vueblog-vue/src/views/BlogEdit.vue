<template>
  <div>
    <Header/>
    <div class="m-content">
      <el-form :model="blogForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="blogForm.title"></el-input>
        </el-form-item>
        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="blogForm.description"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="blogForm.content"></mavon-editor>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">发表</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header"

export default {
  name: "BlogEdit.vue",
  components: {Header},
  created() {
    const blogId = this.$route.params.blogId
    console.log(blogId)
    if (blogId) {
      this.$axios
          .get('/blog/' + blogId)
          .then(res => {
            const blog = res.data.data
            this.blogForm.id = blog.id
            this.blogForm.title = blog.title
            this.blogForm.description = blog.description
            this.blogForm.content = blog.content
          })
    }
  },
  data() {
    return {
      blogForm: {
        id: '',
        title: '',
        description: '',
        content: ''
      },
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
          {min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入摘要', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
              .post('/blog/edit', this.blogForm)
              .then(res => {
                console.log(res)
                this.$alert('操作成功', '提示', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push('/blogs')
                  }
                })
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  }
}
</script>

<style scoped>
.m-content {
  text-align: center;
}
</style>
