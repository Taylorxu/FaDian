package com.powerge.wise.powerge.operationProjo.net.net.response;

/**
 * Created by ycs on 2016/12/20.
 */

public class OpenKnowDetailResponse extends BaseResponse {

    /**
     * state : true
     * formDocument : &lt;?xml version="1.0" encoding="UTF-8"?&gt;
     &lt;BMForm-config&gt;
     &lt;BMForm bmModelName="KNOWLEDGE" superBmModelName="ACCOUNT_FORM" bmIcon="" bmHelp="" bmDisplayName="知识库表单" width="700" height="600" order="0" modelName="KNOWLEDGE" dmDisplayName="知识库" hasFlowChart="false" hasDataRelation="false" hasAuditRecord="false" hasOperLog="false" hasKB="false" hasbmHelp="false"&gt;
     &lt;BMFormValidate bmValidateJson="[{'componentsJson':'K_NUMBER','methodDesc':'是否唯一','methodName':'isUnique','checkJson':'[{conditionId_key_0:K_NUMBER}]','mustPass':'false','mx_internal_uid':'C2AFA70C-A8D8-F640-2CDC-830CAD87C433'}]"/&gt;
     &lt;BMFormProcessor bmProcessorJson=""/&gt;
     &lt;BMFormDataLinkage dataLinkageJson=""/&gt;
     &lt;Section ID="Section0" label="详情" isCurrent="true"&gt;
     &lt;TextInput value="2016-5" modified="false" bmModelName="KNOWLEDGE" dmAttrDisplayName="知识编号" ID="textinput_1" isVisible="true" maxChars="85" x="21" identityRule="@YEAR@-@IDENTITY@" name="知识编号" required="false" width="201" seeHisWorkOrderInfo="false" y="16" height="23" hasBusinessKey="true" isDispatcherEvent="false" dmAttrName="K_NUMBER"/&gt;
     &lt;TextInput value="改过" modified="false" bmModelName="KNOWLEDGE" dmAttrDisplayName="知识标题" ID="textinput_2" isVisible="true" maxChars="85" x="20" identityRule="null" name="知识标题" required="true" width="650" seeHisWorkOrderInfo="false" y="103" height="23" hasBusinessKey="false" isDispatcherEvent="false" dmAttrName="K_TITLE"/&gt;
     &lt;TreeData value="mt:cf11cc05-6898-4c1b-87b0-0a025cda6306" srclib="KNOWLEDGE_TYPE" modified="false" isMult="true" dmAttrDisplayName="知识分类" ID="treedata_1" isVisible="true" srclib_label="知识分类" bmModelName="KNOWLEDGE" x="372" value_label="" name="知识分类" required="false" width="297" seeHisWorkOrderInfo="false" y="14" height="24" hasBusinessKey="false" isDispatcherEvent="false" dmAttrName="K_KNOW_CAT"/&gt;
     &lt;TextInput value="123" modified="false" bmModelName="KNOWLEDGE" dmAttrDisplayName="关键字" ID="textinput_3" isVisible="true" maxChars="85" x="33" identityRule="null" name="关键字" required="false" width="637" seeHisWorkOrderInfo="false" y="133" height="23" hasBusinessKey="false" isDispatcherEvent="false" dmAttrName="K_KEYWORD"/&gt;
     &lt;TextArea value="嘎嘎嘎" seeHisWorkOrderInfo="false" modified="false" bmModelName="KNOWLEDGE" dmAttrDisplayName="知识描述" ID="textarea_1" isVisible="true" maxChars="0" x="21" addToItemIds="" required="false" width="650" name="知识描述" y="165" height="150" hasBusinessKey="false" isDispatcherEvent="false" dmAttrName="K_DESC"/&gt;
     &lt;Attachment value="" isDeleteAttachment="true" bmModelName="KNOWLEDGE" dmAttrDisplayName="知识附件" ID="attachment_1" isVisible="true" modified="false" templateJson="" x="86" name="知识附件" required="false" width="585" y="322" height="187" hasBusinessKey="false" dmAttrName="K_ATTACHMENT"/&gt;
     &lt;DataDisplayDate value="2016-12-15 16:02:24.0" modified="false" bmModelName="KNOWLEDGE" displayName="2016-12-15 16:02:24" dmAttrDisplayName="提交时间" ID="datadisplaydate_1" width="240" x="427" value_label="当前时间" name="提交时间" required="false" isVisible="true" y="523.65" height="23" hasBusinessKey="false" dmAttrName="K_SUBMIT_TIME"/&gt;
     &lt;DataDisplayUser value="bd93c152-a32b-400e-af8b-fd26b26ac752" modified="false" bmModelName="KNOWLEDGE" displayName="" dmAttrDisplayName="提交人" ID="datadisplayuser_1" width="240" x="100" value_label="创建人" name="提交人" required="false" isVisible="true" y="523.65" height="23" hasBusinessKey="false" dmAttrName="K_SUBMIT_USER"/&gt;
     &lt;NumericStepper value="2" precision="2" inputLength="6" bmModelName="KNOWLEDGE" dmAttrDisplayName="点击量" ID="numericstepper_1" isVisible="true" modified="false" x="33" value_label="" name="点击量" y="44" width="190" seeHisWorkOrderInfo="false" required="false" height="23" hasBusinessKey="false" isDispatcherEvent="false" dmAttrName="KNO_DIANJL"/&gt;
     &lt;NumericStepper value="4.0" precision="1" inputLength="5" bmModelName="KNOWLEDGE" dmAttrDisplayName="评分" ID="numericstepper_2" isVisible="true" modified="false" x="45" value_label="" name="评分" y="72" width="177" seeHisWorkOrderInfo="false" required="false" height="23" hasBusinessKey="false" isDispatcherEvent="false" dmAttrName="KNO_PINGF"/&gt;
     &lt;NumericStepper value="2.0" precision="1" inputLength="5" bmModelName="KNOWLEDGE" dmAttrDisplayName="平均分" ID="numericstepper_3" isVisible="true" modified="false" x="384" value_label="" name="平均分" y="72" width="187" seeHisWorkOrderInfo="false" required="false" height="23" hasBusinessKey="false" isDispatcherEvent="false" dmAttrName="KNO_PJF"/&gt;
     &lt;/Section&gt;
     &lt;Button ID="submit"/&gt;
     &lt;/BMForm&gt;
     &lt;/BMForm-config&gt;
     */

    private boolean state;
    private String formDocument;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getFormDocument() {
        return formDocument;
    }

    public void setFormDocument(String formDocument) {
        this.formDocument = formDocument;
    }
}
