<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'code', validatorRules.code]" placeholder="请输入合同编号"></a-input>
        </a-form-item>
        <a-form-item label="合同名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入合同名称"></a-input>
        </a-form-item>
        <a-form-item label="合同类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['type']" :trigger-change="true" dictCode="contract_type" placeholder="请选择合同类型"/>
        </a-form-item>
        <a-form-item label="经办部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-depart v-decorator="['department']" :trigger-change="true"/>
        </a-form-item>
        <a-form-item label="经办人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-user-by-dep v-decorator="['person']" :trigger-change="true"/>
        </a-form-item>
        <a-form-item label="管理人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-user-by-dep v-decorator="['manager']" :trigger-change="true"/>
        </a-form-item>
        <a-form-item label="合同状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['state']" :trigger-change="true" dictCode="contract_state" placeholder="请选择合同状态"/>
        </a-form-item>
        <a-form-item label="合同移交状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['transferState']" :trigger-change="true" dictCode="contract_transfer" placeholder="请选择合同移交状态"/>
        </a-form-item>
        <a-form-item label="合同内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['content']" rows="4" placeholder="请输入合同内容"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "ContractMainModal",
    components: { 
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        code:{rules: [{ required: true, message: '请输入合同编号!' }]},
        name:{rules: [{ required: true, message: '请输入合同名称!' }]},
        type:{rules: [{ required: true, message: '请输入合同类型!' }]},
        department:{},
        person:{},
        manager:{},
        state:{},
        transferState:{},
        content:{},
        },
        url: {
          add: "/test/contractMain/add",
          edit: "/test/contractMain/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'code','name','type','department','person','manager','state','transferState','content'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'code','name','type','department','person','manager','state','transferState','content'))
      },

      
    }
  }
</script>