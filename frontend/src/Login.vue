<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h1>教室预约系统</h1>
      </div>
      
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" class="login-form">
        <el-form-item prop="studentNo">
          <el-input 
            v-model="loginForm.studentNo" 
            placeholder="请输入学号"
            prefix-icon="User"
            size="large"
            clearable
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            size="large" 
            class="login-button"
            @click="handleLogin"
            :loading="loading"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  studentNo: '',
  password: ''
})

const rules = {
  studentNo: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        console.log('开始登录，学号:', loginForm.studentNo)
        const response = await axios.post('/api/auth/login', {
          studentNo: loginForm.studentNo,
          password: loginForm.password
        })
        
        console.log('登录响应:', response.data)
        
        if (response.data.success) {
          ElMessage.success('登录成功')
          // 保存用户信息到localStorage
          localStorage.setItem('userInfo', JSON.stringify(response.data.data))
          console.log('保存的用户信息:', response.data.data)
          // 跳转到主页面
          window.location.href = '/main.html'
        } else {
          ElMessage.error(response.data.message || '登录失败')
        }
      } catch (error) {
        console.error('登录失败详情:', error)
        console.error('错误响应:', error.response)
        if (error.response) {
          ElMessage.error('服务器错误：' + (error.response.data?.message || '请稍后重试'))
        } else if (error.request) {
          ElMessage.error('无法连接到服务器，请检查后端是否启动')
        } else {
          ElMessage.error('登录失败：' + error.message)
        }
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: auto;
  padding: 20px;
}

/* 背景装饰 */
.login-container::before {
  content: '';
  position: absolute;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 1px, transparent 1px);
  background-size: 50px 50px;
  animation: moveBackground 20s linear infinite;
}

@keyframes moveBackground {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(50px, 50px);
  }
}

.login-box {
  background: white;
  border-radius: 20px;
  padding: 40px 30px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: 450px;
  position: relative;
  z-index: 1;
  animation: slideIn 0.5s ease-out;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  color: #1890ff;
  font-size: 28px;
  font-weight: bold;
  margin: 0;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.login-form {
  margin-top: 20px;
}

.login-button {
  width: 100%;
  height: 45px;
  font-size: 18px;
  font-weight: bold;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 8px;
  transition: all 0.3s;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
}

.login-tips {
  margin-top: 30px;
  padding: 15px;
  background: #f0f9ff;
  border-radius: 8px;
  border-left: 4px solid #1890ff;
}

.login-tips p {
  margin: 5px 0;
  color: #666;
  font-size: 14px;
}

.login-tips p:first-child {
  font-weight: bold;
  color: #1890ff;
  margin-bottom: 10px;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 8px 15px;
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 2px 12px rgba(102, 126, 234, 0.3);
}

/* 响应式设计 - 平板设备 */
@media (max-width: 768px) {
  .login-box {
    padding: 30px 25px;
  }
  
  .login-header h1 {
    font-size: 24px;
  }
  
  .login-button {
    font-size: 16px;
    height: 42px;
  }
}

/* 响应式设计 - 手机设备 */
@media (max-width: 480px) {
  .login-container {
    padding: 15px;
  }
  
  .login-box {
    padding: 25px 20px;
    border-radius: 15px;
  }
  
  .login-header {
    margin-bottom: 20px;
  }
  
  .login-header h1 {
    font-size: 22px;
  }
  
  .login-form {
    margin-top: 15px;
  }
  
  .login-button {
    font-size: 16px;
    height: 40px;
  }
  
  :deep(.el-input__wrapper) {
    padding: 6px 12px;
  }
  
  :deep(.el-form-item) {
    margin-bottom: 15px;
  }
}
</style>
