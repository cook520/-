<template>
  <div class="classroom-reservation">
    <!-- 顶部标题栏 -->
    <div class="header">
      <div class="header-title">教室预约</div>
      <div class="header-user">
        <span class="user-info">学号：{{ userInfo?.studentNo || '未登录' }}</span>
        <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
      </div>
    </div>

    <!-- 查询条件面板 -->
    <div class="search-panel">
      <el-form :model="searchForm" label-width="100px">
        <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="8">
            <el-form-item label="*学年学期">
              <el-select v-model="searchForm.semester" placeholder="请选择学年学期" style="width: 100%">
                <el-option label="2025-2026-2" value="2025-2026-2" />
                <el-option label="2025-2026-1" value="2025-2026-1" />
                <el-option label="2024-2025-2" value="2024-2025-2" />
              </el-select>
            </el-form-item>
          </el-col>
          
          <el-col :xs="24" :sm="12" :md="8" :lg="8">
            <el-form-item label="*校区">
              <el-select v-model="searchForm.campus" placeholder="请选择校区" style="width: 100%">
                <el-option label="三亚校区" value="三亚校区" />
                <el-option label="五指山校区" value="五指山校区" />
              </el-select>
            </el-form-item>
          </el-col>
          
          <el-col :xs="24" :sm="12" :md="8" :lg="8">
            <el-form-item label="楼号">
              <el-select v-model="searchForm.buildingNo" placeholder="请选择楼号" style="width: 100%">
                <el-option label="全部" value="全部" />
                <el-option label="1号楼" value="1号楼" />
                <el-option label="2号楼" value="2号楼" />
                <el-option label="3号楼" value="3号楼" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="8">
            <el-form-item label="场地类别">
              <el-select v-model="searchForm.category" placeholder="请选择场地类别" style="width: 100%">
                <el-option label="全部" value="全部" />
                <el-option label="普通教室" value="普通教室" />
                <el-option label="多媒体教室" value="多媒体教室" />
                <el-option label="实验室" value="实验室" />
              </el-select>
            </el-form-item>
          </el-col>
          
          <el-col :xs="24" :sm="12" :md="8" :lg="8">
            <el-form-item label=" ">
              <el-select v-model="searchForm.subCategory" placeholder="请选择场地二级类别" style="width: 100%">
                <el-option label="全部" value="全部" />
                <el-option label="理论教室" value="理论教室" />
                <el-option label="多媒体教室" value="多媒体教室" />
                <el-option label="计算机实验室" value="计算机实验室" />
                <el-option label="阶梯教室" value="阶梯教室" />
              </el-select>
            </el-form-item>
          </el-col>
          
          <el-col :xs="24" :sm="12" :md="8" :lg="8">
            <el-form-item label="座位数">
              <div class="seat-range">
                <el-input-number v-model="searchForm.minSeats" :min="0" placeholder="" controls-position="right" style="flex: 1" />
                <span class="separator">至</span>
                <el-input-number v-model="searchForm.maxSeats" :min="0" placeholder="" controls-position="right" style="flex: 1" />
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第三行 - 场地名称 -->
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="8">
            <el-form-item label="场地名称">
              <el-input 
                v-model="searchForm.classroomName" 
                placeholder="可按场地名称和编号搜索"
                clearable
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第四行 - 周次、星期、节次 -->
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="8" :lg="8">
            <el-form-item label="周次">
              <div class="selector-box">
                <div class="button-group">
                  <el-button 
                    v-for="week in 18" 
                    :key="week"
                    :type="selectedWeeks.includes(week) ? 'primary' : 'default'"
                    size="small"
                    @click="toggleWeek(week)"
                  >
                    {{ week }}
                  </el-button>
                </div>
                <div class="clear-btn-wrapper">
                  <el-button size="small" @click="clearWeeks">清空</el-button>
                </div>
              </div>
            </el-form-item>
          </el-col>
          
          <el-col :xs="24" :sm="24" :md="8" :lg="8">
            <el-form-item label="星期">
              <div class="selector-box">
                <div class="button-group">
                  <el-button 
                    v-for="day in 7" 
                    :key="day"
                    :type="selectedDays.includes(day) ? 'primary' : 'default'"
                    size="small"
                    @click="toggleDay(day)"
                  >
                    {{ day }}
                  </el-button>
                </div>
                <div class="clear-btn-wrapper">
                  <el-button size="small" @click="clearDays">清空</el-button>
                </div>
              </div>
            </el-form-item>
          </el-col>
          
          <el-col :xs="24" :sm="24" :md="8" :lg="8">
            <el-form-item label="节次">
              <div class="selector-box">
                <div class="period-section">
                  <div class="period-row">
                    <span class="period-label">第1-2节</span>
                    <div class="button-group">
                      <el-button 
                        :type="selectedPeriods.includes(1) ? 'primary' : 'default'"
                        size="small"
                        @click="togglePeriod(1)"
                      >
                        1
                      </el-button>
                    </div>
                  </div>
                  <div class="period-row">
                    <span class="period-label">第3-4节</span>
                    <div class="button-group">
                      <el-button 
                        :type="selectedPeriods.includes(2) ? 'primary' : 'default'"
                        size="small"
                        @click="togglePeriod(2)"
                      >
                        2
                      </el-button>
                    </div>
                  </div>
                  <div class="period-row">
                    <span class="period-label">第5-6节</span>
                    <div class="button-group">
                      <el-button 
                        :type="selectedPeriods.includes(3) ? 'primary' : 'default'"
                        size="small"
                        @click="togglePeriod(3)"
                      >
                        3
                      </el-button>
                    </div>
                  </div>
                  <div class="period-row">
                    <span class="period-label">第7-8节</span>
                    <div class="button-group">
                      <el-button 
                        :type="selectedPeriods.includes(4) ? 'primary' : 'default'"
                        size="small"
                        @click="togglePeriod(4)"
                      >
                        4
                      </el-button>
                    </div>
                  </div>
                  <div class="period-row">
                    <span class="period-label">第9-10节</span>
                    <div class="button-group">
                      <el-button 
                        :type="selectedPeriods.includes(5) ? 'primary' : 'default'"
                        size="small"
                        @click="togglePeriod(5)"
                      >
                        5
                      </el-button>
                    </div>
                  </div>
                </div>
                <div class="clear-btn-wrapper">
                  <el-button size="small" @click="clearPeriods">清空</el-button>
                </div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 提示信息 -->
        <el-row>
          <el-col :span="24">
            <div class="tip-text">
              已选中的周次、星期、节次可按住Ctrl键且左点击鼠标可取消！
            </div>
          </el-col>
        </el-row>

        <!-- 查询按钮 -->
        <el-row>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" style="text-align: right;">
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="handleExport">导出</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- 数据表格 -->
    <div class="table-panel">
      <div class="table-wrapper">
        <el-table 
          :data="tableData" 
          style="width: 100%" 
          border
          stripe
          v-loading="loading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="classroomNo" label="场地编号" min-width="120" sortable />
          <el-table-column prop="classroomName" label="场地名称" min-width="150" />
          <el-table-column prop="campus" label="校区" min-width="100" />
          <el-table-column prop="category" label="场地类别" min-width="120" />
          <el-table-column prop="seats" label="座位数" min-width="80" />
          <el-table-column prop="examSeats" label="考试座位数" min-width="110" />
          <el-table-column prop="buildingNo" label="楼号" min-width="80" />
          <el-table-column prop="floorNo" label="楼层号" min-width="80" />
          <el-table-column prop="borrowType" label="场地借用类型" min-width="130" />
          <el-table-column prop="remark" label="场地备注信息" min-width="150" show-overflow-tooltip />
          <el-table-column prop="cdgly" label="cdgly" min-width="100" />
          <el-table-column prop="department" label="使用部门" min-width="100" />
          <el-table-column prop="classGrade" label="使用班级" min-width="100" />
          <el-table-column prop="subCategory" label="场地二级类别" min-width="130" />
          <el-table-column prop="area" label="建筑面积" min-width="100" />
          <el-table-column prop="managedDepartment" label="托管部门" min-width="120" />
        </el-table>
      </div>

      <!-- 预约按钮 -->
      <div class="action-bar">
        <el-button type="primary" @click="showReserveDialog" :disabled="selectedRows.length === 0">
          预约教室
        </el-button>
        <span class="selected-info" v-if="selectedRows.length > 0">
          已选择 {{ selectedRows.length }} 个教室
        </span>
      </div>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[15, 30, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>

    <!-- 预约对话框 -->
    <el-dialog
      v-model="reserveDialogVisible"
      title="预约教室"
      width="600px"
    >
      <!-- 显示选择的周次、星期、节次 -->
      <el-alert
        title="预约时间信息"
        type="info"
        :closable="false"
        style="margin-bottom: 20px;"
      >
        <div v-if="selectedWeeks.length > 0">
          <strong>周次：</strong>{{ selectedWeeks.join('、') }}
        </div>
        <div v-if="selectedDays.length > 0">
          <strong>星期：</strong>{{ selectedDays.map(d => '周' + ['日','一','二','三','四','五','六'][d]).join('、') }}
        </div>
        <div v-if="selectedPeriods.length > 0">
          <strong>节次：</strong>{{ formatPeriods() }}
        </div>
      </el-alert>
      
      <el-form :model="reserveForm" label-width="100px">
        <el-form-item label="申请人姓名">
          <el-input v-model="reserveForm.applicantName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="reserveForm.applicantPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="预约用途">
          <el-input v-model="reserveForm.purpose" type="textarea" placeholder="请输入预约用途" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reserveDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleReserve" :loading="reserveLoading">
          确认预约
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

const userInfo = ref(null)

const searchForm = reactive({
  semester: '2025-2026-2',
  campus: '三亚校区',
  buildingNo: '全部',
  category: '全部',
  subCategory: '全部',
  minSeats: null,
  maxSeats: null,
  classroomName: ''
})

const selectedWeeks = ref([])
const selectedDays = ref([])
const selectedPeriods = ref([])

const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(15)

const selectedRows = ref([])
const reserveDialogVisible = ref(false)
const reserveLoading = ref(false)

const reserveForm = reactive({
  applicantName: '',
  applicantPhone: '',
  purpose: ''
})

// 周次选择
const toggleWeek = (week) => {
  const index = selectedWeeks.value.indexOf(week)
  if (index > -1) {
    selectedWeeks.value.splice(index, 1)
  } else {
    selectedWeeks.value.push(week)
  }
}

const clearWeeks = () => {
  selectedWeeks.value = []
}

// 星期选择
const toggleDay = (day) => {
  const index = selectedDays.value.indexOf(day)
  if (index > -1) {
    selectedDays.value.splice(index, 1)
  } else {
    selectedDays.value.push(day)
  }
}

const clearDays = () => {
  selectedDays.value = []
}

// 节次选择（双选）
const togglePeriodPair = (period1, period2) => {
  const hasBoth = selectedPeriods.value.includes(period1) && selectedPeriods.value.includes(period2)
  
  if (hasBoth) {
    // 如果两个都已选中，则取消
    selectedPeriods.value = selectedPeriods.value.filter(p => p !== period1 && p !== period2)
  } else {
    // 否则添加这两个
    if (!selectedPeriods.value.includes(period1)) {
      selectedPeriods.value.push(period1)
    }
    if (!selectedPeriods.value.includes(period2)) {
      selectedPeriods.value.push(period2)
    }
  }
}

const togglePeriod = (period) => {
  const index = selectedPeriods.value.indexOf(period)
  if (index > -1) {
    selectedPeriods.value.splice(index, 1)
  } else {
    selectedPeriods.value.push(period)
  }
}

const clearPeriods = () => {
  selectedPeriods.value = []
}

const clearAll = () => {
  selectedWeeks.value = []
  selectedDays.value = []
  selectedPeriods.value = []
}

// 查询教室
const handleSearch = async () => {
  loading.value = true
  try {
    const params = {
      semester: searchForm.semester,
      campus: searchForm.campus,
      buildingNo: searchForm.buildingNo,
      category: searchForm.category,
      subCategory: searchForm.subCategory,
      minSeats: searchForm.minSeats,
      maxSeats: searchForm.maxSeats,
      classroomName: searchForm.classroomName,
      weeks: selectedWeeks.value,
      days: selectedDays.value,
      periods: selectedPeriods.value,
      page: currentPage.value - 1,
      size: pageSize.value
    }
    
    console.log('========== 前端发送的查询参数 ==========')
    console.log('weeks:', selectedWeeks.value)
    console.log('days:', selectedDays.value)
    console.log('periods:', selectedPeriods.value)
    console.log('完整参数:', params)
    console.log('=====================================')
    
    // 使用 paramsSerializer 来正确处理数组参数
    const response = await axios.get('/api/classrooms/search', { 
      params,
      paramsSerializer: {
        indexes: null // 使用重复参数名的方式：weeks=1&weeks=2
      }
    })
    console.log('查询结果数量:', response.data.total)
    console.log('查询结果数据:', response.data.data)
    if (response.data.data && response.data.data.length > 0) {
      console.log('第一条数据:', response.data.data[0])
      console.log('教室编号:', response.data.data[0].classroomNo)
      console.log('教室名称:', response.data.data[0].classroomName)
    }
    tableData.value = response.data.data
    total.value = response.data.total
  } catch (error) {
    console.error('查询失败:', error)
    ElMessage.error('查询失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 导出功能
const handleExport = () => {
  ElMessage.info('导出功能开发中...')
}

// 选择变化
const handleSelectionChange = (selection) => {
  selectedRows.value = selection
}

// 显示预约对话框
const showReserveDialog = () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要预约的教室')
    return
  }
  if (selectedWeeks.value.length === 0 || selectedDays.value.length === 0 || selectedPeriods.value.length === 0) {
    ElMessage.warning('请先选择周次、星期和节次')
    return
  }
  reserveForm.applicantName = ''
  reserveForm.applicantPhone = ''
  reserveForm.purpose = ''
  reserveDialogVisible.value = true
}

// 格式化节次显示
const formatPeriods = () => {
  const sorted = [...selectedPeriods.value].sort((a, b) => a - b)
  return sorted.map(p => `${p}节`).join('、')
}

// 预约教室
const handleReserve = async () => {
  if (!reserveForm.applicantName || !reserveForm.applicantPhone || !reserveForm.purpose) {
    ElMessage.warning('请填写完整的预约信息')
    return
  }
  
  if (selectedWeeks.value.length === 0 || selectedDays.value.length === 0 || selectedPeriods.value.length === 0) {
    ElMessage.warning('请选择周次、星期和节次')
    return
  }
  
  reserveLoading.value = true
  try {
    // 为每个选中的教室预约所有选中的时间段
    const promises = []
    for (const row of selectedRows.value) {
      for (const week of selectedWeeks.value) {
        for (const day of selectedDays.value) {
          for (const period of selectedPeriods.value) {
            promises.push(
              axios.post('/api/classrooms/reserve', {
                classroomId: row.id,
                semester: searchForm.semester,
                campus: searchForm.campus,
                week: week,
                dayOfWeek: day,
                period: period,
                applicantName: reserveForm.applicantName,
                applicantPhone: reserveForm.applicantPhone,
                purpose: reserveForm.purpose
              })
            )
          }
        }
      }
    }
    
    await Promise.all(promises)
    ElMessage.success('预约成功')
    reserveDialogVisible.value = false
    
    // 重新查询，已预约的教室会消失
    handleSearch()
  } catch (error) {
    console.error('预约失败:', error)
    ElMessage.error('预约失败，请稍后重试')
  } finally {
    reserveLoading.value = false
  }
}

// 分页
const handleSizeChange = (val) => {
  pageSize.value = val
  handleSearch()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  handleSearch()
}

// 初始化加载数据
onMounted(() => {
  // 检查用户是否登录
  const storedUserInfo = localStorage.getItem('userInfo')
  if (!storedUserInfo) {
    ElMessage.warning('请先登录')
    window.location.href = '/index.html'
    return
  }
  userInfo.value = JSON.parse(storedUserInfo)
  handleSearch()
})

// 退出登录
const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('userInfo')
    ElMessage.success('已退出登录')
    window.location.href = '/index.html'
  }).catch(() => {
    // 取消操作
  })
}
</script>

<style scoped>
.classroom-reservation {
  font-family: 'Microsoft YaHei', Arial, sans-serif;
  min-width: 320px;
}

.header {
  background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);
  color: white;
  padding: 15px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  flex-wrap: wrap;
  gap: 10px;
}

.header-title {
  font-size: 20px;
  font-weight: bold;
}

.header-user {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}

.user-info {
  font-size: 14px;
}

.header-info {
  font-size: 14px;
  text-align: right;
}

.search-panel {
  background: white;
  padding: 15px;
  margin: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.seat-range {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
}

.separator {
  color: #909399;
}

.button-group {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}

:deep(.button-group .el-button) {
  margin: 0 !important;
  padding: 5px 8px;
  min-width: 28px;
  font-size: 12px;
}

.selector-box {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 8px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  background-color: #fff;
  flex-wrap: wrap;
  gap: 8px;
}

.clear-btn-wrapper {
  flex-shrink: 0;
  margin-left: 8px;
}

.period-section {
  display: flex;
  flex-direction: column;
  gap: 4px;
  flex: 1;
  min-width: 200px;
}

.period-row {
  display: flex;
  align-items: center;
  gap: 5px;
  flex-wrap: wrap;
}

.period-label {
  font-size: 12px;
  color: #606266;
  font-weight: bold;
  white-space: nowrap;
  min-width: 30px;
}

.tip-text {
  color: #e6a23c;
  font-size: 13px;
  margin: 10px 0;
  padding: 8px;
  background: #fdf6ec;
  border-radius: 4px;
  line-height: 1.5;
}

.table-panel {
  background: white;
  padding: 15px;
  margin: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  overflow-x: auto;
}

.table-wrapper {
  width: 100%;
  overflow-x: auto;
}

.action-bar {
  margin-top: 15px;
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}

.selected-info {
  color: #1890ff;
  font-size: 14px;
  font-weight: 500;
}

.pagination {
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
  flex-wrap: wrap;
  gap: 10px;
}

:deep(.el-form-item__label) {
  font-weight: 500;
  font-size: 13px;
}

:deep(.el-button--primary) {
  background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);
  border: none;
}

:deep(.el-button--primary:hover) {
  background: linear-gradient(135deg, #40a9ff 0%, #1890ff 100%);
}

/* 响应式设计 - 平板设备 */
@media (max-width: 1024px) {
  .header-title {
    font-size: 18px;
  }
  
  .search-panel {
    padding: 12px;
    margin: 10px;
  }
  
  .table-panel {
    padding: 12px;
    margin: 10px;
  }
  
  :deep(.el-table) {
    font-size: 13px;
  }
}

/* 响应式设计 - 手机设备 */
@media (max-width: 768px) {
  .header {
    padding: 12px 15px;
    flex-direction: column;
    align-items: flex-start;
  }
  
  .header-title {
    font-size: 16px;
    margin-bottom: 8px;
  }
  
  .header-user {
    width: 100%;
    justify-content: space-between;
  }
  
  .user-info {
    font-size: 12px;
  }
  
  .search-panel {
    padding: 10px;
    margin: 8px;
  }
  
  .table-panel {
    padding: 10px;
    margin: 8px;
  }
  
  .selector-box {
    flex-direction: column;
  }
  
  .clear-btn-wrapper {
    margin-left: 0;
    margin-top: 8px;
    align-self: flex-end;
  }
  
  .period-section {
    min-width: 100%;
  }
  
  .tip-text {
    font-size: 12px;
    padding: 6px;
  }
  
  .action-bar {
    flex-direction: column;
    align-items: stretch;
  }
  
  .pagination {
    justify-content: center;
  }
  
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
  
  :deep(.el-form-item__label) {
    font-size: 12px;
  }
  
  :deep(.el-input__inner),
  :deep(.el-select .el-input__inner) {
    font-size: 13px;
  }
  
  :deep(.el-button) {
    font-size: 12px;
    padding: 8px 12px;
  }
}

/* 超小屏幕优化 */
@media (max-width: 480px) {
  .header-title {
    font-size: 14px;
  }
  
  .search-panel,
  .table-panel {
    padding: 8px;
    margin: 5px;
  }
  
  .button-group {
    gap: 2px;
  }
  
  :deep(.button-group .el-button) {
    padding: 4px 6px;
    font-size: 11px;
    min-width: 24px;
  }
  
  .period-label {
    font-size: 11px;
  }
  
  :deep(.el-table) {
    font-size: 12px;
  }
}
</style>
