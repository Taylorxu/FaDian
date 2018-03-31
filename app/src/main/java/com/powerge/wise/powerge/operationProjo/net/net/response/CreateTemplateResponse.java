package com.powerge.wise.powerge.operationProjo.net.net.response;

/**
 * Created by ycs on 2016/12/6.
 */

public class CreateTemplateResponse extends BaseResponse {

    /**
     * formDocument : &lt;?xml version="1.0" encoding="UTF-8"?&gt;
     &lt;BMForm-config&gt;
     &lt;BMForm bmModelName="chatest" superBmModelName="PROCESS_BASE" bmIcon="" bmHelp="" bmDisplayName="变更流程" width="700" height="1700" order="0" modelName="CHAPRO" dmDisplayName="变更流程" hasFlowChart="false" hasDataRelation="false" hasAuditRecord="false" hasOperLog="false" hasKB="false" hasbmHelp="true" conditionJudgment="" sortingSet="null" specificValueUpdate="{'values':[{'formItemValue':{'value':'bgzt:e7bd772c-608e-4e6d-8320-4c9abfc44b54','name':'新建'},'formItem':{'ID':'combobox_2','precision':null,'id':'combobox_2','maxChars':null,'seeHisWorkOrderInfo':false,'isCanDelete':true,'configModifiedField':null,'bmModelName':'chatest','dataProvider':null,'dmAttrDisplayName':'变更状态','isCanUpdate':true,'modelName':'CHAPRO','isFilterUser':false,'parentDeptJSON':null,'allColumnsJson':null,'isCanAdd':true,'isVisible':true,'value':'','modified':false,'name':'变更状态','sectionId':'Section0','dmAttrName':'CO_STAT','isExecute':false,'isMult':false,'required':false,'nodeType':'ComboBox','srclib':'bgzt','srclib_label':'变更状态','value_label':null}}]}"&gt;
     &lt;BMFormValidate bmValidateJson=""/&gt;
     &lt;BMFormProcessor bmProcessorJson="null"/&gt;
     &lt;BMFormDataLinkage dataLinkageJson="null"/&gt;
     &lt;Section ID="Section0" label="变更基本信息" isCurrent="true"&gt;
     &lt;BorderGroup height="120" x="43" y="22.3" width="575" name="项目信息" ID="bordergroup_1" styleJson="null" isVisible="true"/&gt;
     &lt;TextInput height="23" maxChars="85" y="149" value="" dmAttrName="TITLE" modified="true" x="25" bmModelName="chatest" seeHisWorkOrderInfo="false" required="true" width="583" ID="textinput_1" dmAttrDisplayName="工单标题" name="变更标题" identityRule="null" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextArea height="87" addToItemIds="" y="180" value="" dmAttrName="CO_CRE" modified="true" x="25" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="583" ID="textarea_1" maxChars="85" dmAttrDisplayName="变更原因" name="变更原因" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextArea height="87" addToItemIds="" y="274" value="" dmAttrName="CO_CPL" modified="true" x="24" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="586" ID="textarea_2" maxChars="0" dmAttrDisplayName="变更方案" name="变更方案" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;DateFeild height="25" x="19" width="278" y="381" value="" dmAttrName="CO_EFT" hasDateTime="true" ID="datefeild_1" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" isUpdateBeforeDate="false" modified="true" isUpdateLaterDate="false" dmAttrDisplayName="希望完成时间" name="期望完成时间" value_label="null" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TreeData height="23" x="338" width="277" y="381" value="" dmAttrName="CO_CTY" modified="true" ID="treedata_1" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="mt" isMult="false" srclib_label="事件分类" dmAttrDisplayName="变更分类" isDispatcherEvent="false" value_label="null" hasBusinessKey="false" isVisible="true" name="变更分类"/&gt;
     &lt;ComboBox height="23" x="43" width="251" y="413" value="" dmAttrName="CO_CT" modified="true" ID="combobox_1" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="bglx" srclib_label="变更类型" dmAttrDisplayName="变更类型" isDispatcherEvent="false" value_label="null" hasBusinessKey="false" isVisible="true" name="变更类型"/&gt;
     &lt;ComboBox height="23" x="338" width="238" y="413" value="bgzt:e7bd772c-608e-4e6d-8320-4c9abfc44b54" dmAttrName="CO_STAT" modified="false" ID="combobox_2" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="bgzt" srclib_label="变更状态" dmAttrDisplayName="变更状态" isDispatcherEvent="false" value_label="null" hasBusinessKey="false" isVisible="true" name="变更状态"/&gt;
     &lt;Attachment height="207" x="23" hasBusinessKey="false" y="447" value="" dmAttrName="CO_UPLOAD" modified="true" ID="attachment_1" bmModelName="chatest" dmAttrDisplayName="上传" width="627" required="false" name="变更方案上传" isDeleteAttachment="false" isVisible="true" templateJson="" seeHisWorkOrderInfo="false"/&gt;
     &lt;ResModelSelect height="25" realTimeUpdate="true" y="48.3" dmAttrDisplayName="项目信息" dmAttrName="OBJ_PROI" queryScope="" modified="true" ID="resmodelselect_1" resModelValueJson="{'dmAttrName':'OBJ_PROI','dmDisplayName':'合同信息','toBmModelName':'CONTRACTS','bmModelName':'chatest','bmDisplayName':'合同信息','configValueJson':[{'fromDmAttrName':{'id':'textinput_6','dmAttrName':'CO_PRONO'},'toFmAttrName':{'id':'textinput_4','dmAttrName':'PROJ_NO'},'mx_internal_uid':'9F6015B6-66F7-39E7-9A09-C872E8E6A68F'},{'fromDmAttrName':{'id':'textinput_7','dmAttrName':'CO_PRONA'},'toFmAttrName':{'id':'textinput_5','dmAttrName':'PROJ_NAME'},'mx_internal_uid':'A7C3700D-1F8F-F9A8-2C1D-C873089B0CC8'},{'fromDmAttrName':{'id':'textinput_5','dmAttrName':'CO_NO'},'toFmAttrName':{'id':'textinput_1','dmAttrName':'CUST_NO'},'mx_internal_uid':'61EBB80E-D851-8F30-06F5-7B6DB4C3EDEE'},{'fromDmAttrName':{'id':'combobox_4','dmAttrName':'CO_CUVIP'},'toFmAttrName':{'id':'combobox_4','dmAttrName':'WHETHER_VIP'},'mx_internal_uid':'C7452FC5-3A12-8EB0-A64A-96458328AC46'}]}" value="" isVisible="true" isMult="false" x="490" bmModelName="chatest" allColumnsJson="null" isDispatcherEvent="false" hasBusinessKey="false" width="170" name="项目选择" required="false" seeHisWorkOrderInfo="false"/&gt;
     &lt;TextInput height="23" maxChars="85" y="48.3" value="" dmAttrName="CO_NO" modified="false" x="58" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="200" ID="textinput_5" dmAttrDisplayName="客户编码" name="客户编码" identityRule="null" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextInput height="23" maxChars="85" y="76" value="" dmAttrName="CO_PRONO" modified="false" x="63" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="200" ID="textinput_6" dmAttrDisplayName="项目编码" name="项目编码" identityRule="null" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextInput height="23" maxChars="85" y="76" value="" dmAttrName="CO_PRONA" modified="false" x="266" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="334" ID="textinput_7" dmAttrDisplayName="项目名称" name="项目名称" identityRule="null" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;ComboBox height="23" x="266" width="200" y="48.3" value="" dmAttrName="CO_CUVIP" modified="false" ID="combobox_4" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="yesorno" srclib_label="是否" dmAttrDisplayName="是否VIP用户" isDispatcherEvent="false" value_label="null" hasBusinessKey="false" isVisible="true" name="是否VIP用户"/&gt;
     &lt;DataDisplayUser height="23" x="92" y="705" value="" dmAttrName="CO_BGQQR" modified="false" ID="datadisplayuser_2" bmModelName="chatest" dmAttrDisplayName="变更请求人" isVisible="true" required="false" name="变更申请人" value_label="null" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;TextInput height="23" maxChars="16" y="878" value="" dmAttrName="PIKEY" modified="false" x="169" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="200" ID="textinput_8" dmAttrDisplayName="单号" name="单号" identityRule="null" hasBusinessKey="false" isVisible="false" isDispatcherEvent="false"/&gt;
     &lt;DataDisplayDate height="23" x="120" y="924.5" value="currentTime" dmAttrName="CREATEDATE" modified="false" ID="datadisplaydate_8" bmModelName="chatest" dmAttrDisplayName="创建时间" isVisible="false" required="false" name="创建时间" value_label="当前时间" hasBusinessKey="false" width="240" displayName="2016-12-06 15:26:19" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayUser height="23" x="121" y="954" value="" dmAttrName="CREATOR" modified="false" ID="datadisplayuser_8" bmModelName="chatest" dmAttrDisplayName="创建人" isVisible="false" required="false" name="创建人" value_label="创建人" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayDate height="23" x="380" y="705" value="currentTime" dmAttrName="CO_BGQQT" modified="false" ID="datadisplaydate_9" bmModelName="chatest" dmAttrDisplayName="变更请求时间" isVisible="true" required="false" name="变更申请时间" value_label="当前时间" hasBusinessKey="false" width="240" displayName="2016-12-06 15:26:19" seeHisWorkOrderInfo="false"/&gt;
     &lt;Position height="23" maxChars="85" y="993" value="" dmAttrName="CUST_LOCATE" modified="false" x="184" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="200" ID="position_1" dmAttrDisplayName="客户位置" name="位置" identityRule="null" hasBusinessKey="false" isVisible="false" isDispatcherEvent="false"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section1" label="变更审核"&gt;
     &lt;RadioButtons height="23" x="56" width="200" y="26" value="" dmAttrName="CO_AG" modified="false" ID="radiobuttons_1" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="yesorno" srclib_label="是否" dmAttrDisplayName="是否同意变更" isDispatcherEvent="false" value_label="" hasBusinessKey="false" isVisible="true" name="是否同意变更"/&gt;
     &lt;TextArea height="110" addToItemIds="" y="60" value="" dmAttrName="CO_INYJ" modified="false" x="80" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="531" ID="textarea_3" maxChars="0" dmAttrDisplayName="审核意见" name="审核意见" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;RadioButtons height="23" x="58" width="200" y="182" value="" dmAttrName="CO_CIM" modified="false" ID="radiobuttons_2" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="yesorno" srclib_label="是否" dmAttrDisplayName="是否紧急变更" isDispatcherEvent="false" value_label="" hasBusinessKey="false" isVisible="true" name="是否紧急变更"/&gt;
     &lt;DataDisplayUser height="23" x="81" y="235" value="" dmAttrName="CO_BGJL" modified="false" ID="datadisplayuser_3" bmModelName="chatest" dmAttrDisplayName="变更经理" isVisible="true" required="false" name="变更经理" value_label="请选择" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayDate height="23" x="373" y="235" value="" dmAttrName="CO_SHT" modified="false" ID="datadisplaydate_10" bmModelName="chatest" dmAttrDisplayName="审核时间" isVisible="true" required="false" name="变更审核时间" value_label="" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section2" label="变更评审"&gt;
     &lt;TextArea height="97" addToItemIds="" y="20" value="" dmAttrName="CO_RWC" modified="false" x="74" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="556" ID="textarea_4" maxChars="0" dmAttrDisplayName="评审意见" name="评审意见" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;RadioButtons height="23" x="75" width="200" y="130.25" value="" dmAttrName="CO_TBC" modified="false" ID="radiobuttons_3" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="yesorno" srclib_label="是否" dmAttrDisplayName="回退方案是否完善" isDispatcherEvent="false" value_label="" hasBusinessKey="false" isVisible="true" name="回退方案是否完善"/&gt;
     &lt;RadioButtons height="23" x="350" width="200" y="130" value="" dmAttrName="CO_IM" modified="false" ID="radiobuttons_4" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="yesorno" srclib_label="是否" dmAttrDisplayName="是否可实施" isDispatcherEvent="false" value_label="" hasBusinessKey="false" isVisible="true" name="是否可实施"/&gt;
     &lt;Attachment height="173" x="65" hasBusinessKey="false" y="150.1" value="" dmAttrName="CO_UPLOADB" modified="false" ID="attachment_2" bmModelName="chatest" dmAttrDisplayName="上传附件副" width="587" required="false" name="文件上传" isDeleteAttachment="false" isVisible="true" templateJson="" seeHisWorkOrderInfo="false"/&gt;
     &lt;MultUserChoose height="183" x="86" y="330" dmAttrDisplayName="参与评审人员" dmAttrName="CO_IMME" modified="false" ID="multuserchoose_1" bmModelName="chatest" value="" width="547" isShowBorder="true" required="false" isDispatcherEvent="false" personInfo="[{'label':'部门','id':'department'},{'label':'姓名','id':'userName'},{'label':'移动电话','id':'userCellphone'}]" hasBusinessKey="false" isVisible="true" name="参与评审人员" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayUser height="23" x="118" y="550.3" value="" dmAttrName="CO_PSZZ" modified="false" ID="datadisplayuser_4" bmModelName="chatest" dmAttrDisplayName="评审组长" isVisible="true" required="false" name="评审组长" value_label="请选择" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayDate height="23" x="393" y="550.3" value="" dmAttrName="CO_PST" modified="false" ID="datadisplaydate_11" bmModelName="chatest" dmAttrDisplayName="评审时间" isVisible="true" required="false" name="评审时间" value_label="" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section3" label="变更测试"&gt;
     &lt;TextArea height="86" addToItemIds="" y="17" value="" dmAttrName="CO_TEIC" modified="false" x="82" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="501" ID="textarea_5" maxChars="0" dmAttrDisplayName="变更测试结论" name="变更测试结论" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;RadioButtons height="23" x="96" width="200" y="110" value="" dmAttrName="CO_IMB" modified="false" ID="radiobuttons_5" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="yesorno" srclib_label="是否" dmAttrDisplayName="是否可实施副" isDispatcherEvent="false" value_label="" hasBusinessKey="false" isVisible="true" name="是否可实施"/&gt;
     &lt;Attachment height="193" x="71" hasBusinessKey="false" y="135" value="" dmAttrName="CO_UPLOADC" modified="false" ID="attachment_3" bmModelName="chatest" dmAttrDisplayName="上传附件副二" width="568" required="false" name="上传附件" isDeleteAttachment="false" isVisible="true" templateJson="" seeHisWorkOrderInfo="false"/&gt;
     &lt;MultUserChoose height="155" x="85" y="345" dmAttrDisplayName="变更测试参与人员" dmAttrName="CO_TEME" modified="false" ID="multuserchoose_2" bmModelName="chatest" value="" width="549" isShowBorder="true" required="false" isDispatcherEvent="false" personInfo="[{'label':'部门','id':'department'},{'label':'姓名','id':'userName'},{'label':'移动电话','id':'userCellphone'}]" hasBusinessKey="false" isVisible="true" name="变更测试参与人员" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayUser height="23" x="113" y="575" value="" dmAttrName="CO_CESHIR" modified="false" ID="datadisplayuser_5" bmModelName="chatest" dmAttrDisplayName="测试人" isVisible="true" required="false" name="变更测试人" value_label="请选择" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayDate height="23" x="377" y="575" value="" dmAttrName="CO_CESHIT" modified="false" ID="datadisplaydate_12" bmModelName="chatest" dmAttrDisplayName="测试时间" isVisible="true" required="false" name="变更测试时间" value_label="" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section4" label="变更实施"&gt;
     &lt;TextArea height="90" addToItemIds="" y="18" value="" dmAttrName="CO_IMCM" modified="false" x="18" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="598" ID="textarea_6" maxChars="0" dmAttrDisplayName="客户对实施方案意见" name="客户对实施方案意见" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;DateFeild height="25" x="54" width="275" y="117" value="" dmAttrName="CO_ST" hasDateTime="true" ID="datefeild_2" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" isUpdateBeforeDate="false" modified="false" isUpdateLaterDate="false" dmAttrDisplayName="变更开始时间" name="变更开始时间" value_label="" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;DateFeild height="25" x="349" width="266" y="117" value="" dmAttrName="CO_ET" hasDateTime="true" ID="datefeild_3" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" isUpdateBeforeDate="false" modified="false" isUpdateLaterDate="false" dmAttrDisplayName="变更结束时间" name="变更结束时间" value_label="" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextInput height="23" maxChars="85" y="151" value="" dmAttrName="CO_EQT" modified="false" x="77" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="537" ID="textinput_2" dmAttrDisplayName="设备型号" name="设备型号" identityRule="null" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextInput height="23" maxChars="85" y="181" value="" dmAttrName="CO_EQSN" modified="false" x="65" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="549" ID="textinput_3" dmAttrDisplayName="设备序列号" name="设备序列号" identityRule="null" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextArea height="75" addToItemIds="" y="210" value="" dmAttrName="CO_IMRE" modified="false" x="77" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="537" ID="textarea_7" maxChars="0" dmAttrDisplayName="实施结果" name="实施结果" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextArea height="75" addToItemIds="" y="290" value="" dmAttrName="CO_CUREC" modified="false" x="17" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="596" ID="textarea_8" maxChars="0" dmAttrDisplayName="客户对实施结果意见" name="客户对实施结果意见" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;TextInput height="23" maxChars="85" y="376" value="" dmAttrName="CO_CURP" modified="false" x="77" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="233" ID="textinput_4" dmAttrDisplayName="客户代表" name="客户代表" identityRule="null" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;DateFeild height="25" x="333" width="291" y="375" value="" dmAttrName="CO_RST" hasDateTime="true" ID="datefeild_4" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" isUpdateBeforeDate="false" modified="false" isUpdateLaterDate="false" dmAttrDisplayName="客户代表签字时间" name="客户代表签字时间" value_label="" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;Attachment height="193" x="46" hasBusinessKey="false" y="414" value="" dmAttrName="CO_UPLOADD" modified="false" ID="attachment_4" bmModelName="chatest" dmAttrDisplayName="文件上传副四" width="600" required="false" name="附件上传" isDeleteAttachment="false" isVisible="true" templateJson="" seeHisWorkOrderInfo="false"/&gt;
     &lt;MultUserChoose height="209" x="59" y="622" dmAttrDisplayName="变更测试参与人员副" dmAttrName="CO_TEMEB" modified="false" ID="multuserchoose_3" bmModelName="chatest" value="" width="592" isShowBorder="true" required="false" isDispatcherEvent="false" personInfo="[{'label':'部门','id':'department'},{'label':'姓名','id':'userName'},{'label':'移动电话','id':'userCellphone'}]" hasBusinessKey="false" isVisible="true" name="变更实施参与人员" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayUser height="23" x="115" y="894.3" value="" dmAttrName="CO_SHISHIR" modified="false" ID="datadisplayuser_6" bmModelName="chatest" dmAttrDisplayName="实施人" isVisible="true" required="false" name="变更实施人" value_label="请选择" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayDate height="23" x="383" y="894.3" value="" dmAttrName="CO_SHISHIT" modified="false" ID="datadisplaydate_13" bmModelName="chatest" dmAttrDisplayName="实施时间" isVisible="true" required="false" name="变更实施时间" value_label="" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section5" label="配置确认"&gt;
     &lt;TextArea height="100" addToItemIds="" y="21" value="" dmAttrName="CO_CONRE" modified="false" x="52" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="555" ID="textarea_9" maxChars="0" dmAttrDisplayName="配置变更结果" name="配置变更结果" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;Attachment height="185" x="72" hasBusinessKey="false" y="137" value="" dmAttrName="CO_CONREUL" modified="false" ID="attachment_5" bmModelName="chatest" dmAttrDisplayName="配置变更通知单" width="588" required="false" name="配置变更通知单" isDeleteAttachment="false" isVisible="true" templateJson="" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayUser height="23" x="72" y="369" value="" dmAttrName="CO_CONP" modified="false" ID="datadisplayuser_1" bmModelName="chatest" dmAttrDisplayName="配置管理员" isVisible="true" required="false" name="配置管理员签名" value_label="请选择" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayDate height="23" x="388" y="369" value="" dmAttrName="CO_PZQRT" modified="false" ID="datadisplaydate_14" bmModelName="chatest" dmAttrDisplayName="配置确认时间" isVisible="true" required="false" name="配置确认时间" value_label="" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section6" label="变更关闭"&gt;
     &lt;TextArea height="96" addToItemIds="" y="33" value="" dmAttrName="CO_IMRW" modified="false" x="56" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" width="554" ID="textarea_10" maxChars="0" dmAttrDisplayName="实施后评审信息" name="实施后评审信息" hasBusinessKey="false" isVisible="true" isDispatcherEvent="false"/&gt;
     &lt;ComboBox height="23" x="95.5" width="200" y="151.1" value="" dmAttrName="CO_CLCO" modified="false" ID="combobox_3" bmModelName="chatest" seeHisWorkOrderInfo="false" required="false" srclib="co_clco" srclib_label="变更关闭代码" dmAttrDisplayName="变更关闭代码" isDispatcherEvent="false" value_label="null" hasBusinessKey="false" isVisible="true" name="变更关闭代码"/&gt;
     &lt;DataDisplayUser height="23" x="85" y="257" value="" dmAttrName="CO_BGJLA" modified="false" ID="datadisplayuser_7" bmModelName="chatest" dmAttrDisplayName="变更经理" isVisible="true" required="false" name="变更经理" value_label="null" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;DataDisplayDate height="23" x="400" y="257" value="" dmAttrName="CO_BGGBT" modified="false" ID="datadisplaydate_15" bmModelName="chatest" dmAttrDisplayName="变更关闭时间" isVisible="true" required="false" name="变更关闭时间" value_label="" hasBusinessKey="false" width="240" displayName="" seeHisWorkOrderInfo="false"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section7" label="关联工单"&gt;
     &lt;WORelating height="275" x="24" y="5" value="" dmAttrName="RELEATED_WORKORDERS" modified="false" ID="worelating_1" bmModelName="chatest" dmAttrDisplayName="关联工单" width="648" required="false" name="关联工单组件" hasBusinessKey="false" isVisible="true" isShowBorder="true" seeHisWorkOrderInfo="false"/&gt;
     &lt;/Section&gt;
     &lt;Button ID="submit"&gt;&lt;nextNode name="填写变更请求--变更审核" to="变更审核" isDefaultPath="true" taskStrategy="{'strategyKey':'role','strategyValue':'{\'roleId\':\'5c6c82db-64eb-4190-a1d7-036fbddd5797\',\'roleName\':\'变更经理\'}'}" specificValueUpdate="{'values':[{'formItemValue':{'name':'待审批','value':'bgzt:ff076164-d4c6-4fa9-bd3f-9c091645952b'},'formItem':{'isFilterUser':false,'required':false,'isVisible':true,'dmAttrName':'CO_STAT','maxChars':null,'modified':false,'id':'combobox_2','sectionId':'Section0','isExecute':false,'isMult':false,'name':'变更状态','nodeType':'ComboBox','srclib':'bgzt','srclib_label':'变更状态','isCanAdd':true,'isCanUpdate':true,'value_label':null,'ID':'combobox_2','isCanDelete':true,'precision':null,'bmModelName':'chatest','seeHisWorkOrderInfo':false,'dmAttrDisplayName':'变更状态','modelName':'CHAPRO','dataProvider':null,'value':'','configModifiedField':null,'parentDeptJSON':null,'allColumnsJson':null}}]}" nameDesc="提交变更审核" isDependCondition="false"/&gt;&lt;/Button&gt;
     &lt;/BMForm&gt;
     &lt;/BMForm-config&gt;
     * PROCESS_KEY : change
     */

    private ReturnValueBean returnValue;

    public ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String formDocument;
        private String PROCESS_KEY;

        public String getFormDocument() {
            return formDocument;
        }

        public void setFormDocument(String formDocument) {
            this.formDocument = formDocument;
        }

        public String getPROCESS_KEY() {
            return PROCESS_KEY;
        }

        public void setPROCESS_KEY(String PROCESS_KEY) {
            this.PROCESS_KEY = PROCESS_KEY;
        }
    }
}
