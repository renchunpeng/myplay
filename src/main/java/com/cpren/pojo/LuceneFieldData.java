package com.cpren.pojo;

/**
 * @Author hjgu@iyunwen.com
 * @Date 2018/10/19 11:27
 */
public abstract class LuceneFieldData {

    // 知识id
    public final static String LU_FIELD_KNOWLEDGE_ID = "knowledgeId";

    // 知识id 字符串类型
    public final static String LU_FIELD_KNOWLEDGE_ID_STRING = "knowledgeIdString";

    // 知识所属库
    public final static String LU_FIELD_DOMAIN_ID = "domainId";

    // 知识状态
    public final static String LU_FIELD_STATUS = "status";

    // 知识是否对客户开放
    public final static String LU_FIELD_OPEN_TO_CUSTOMER = "openToCustomer";

    // 所属词条
    public final static String LU_FIELD_ENTRY = "entry";

    // 关键词
    public final static String LU_FIELD_KEYWORD = "keyword";

    // 知识领域
    public final static String LU_FIELD_KNOWLEDGE_FIELD = "knowledgeField";

    // 是否对全部领域开放
    public final static String LU_FIELD_OPEN_TO_FIELD = "openToField";

    // 失效是否可看
    public final static String LU_FIELD_READ_EXPIRE = "readExpire";

    // 创建人
    public final static String LU_FIELD_CREATE_USER = "createUser";

    // 创建时间
    public final static String LU_FIELD_CREATE_TIME = "createTime";

    // 更新人
    public final static String LU_FIELD_UPDATE_USER = "updateUser";

    // 更新时间
    public final static String LU_FIELD_UPDATE_TIME = "updateTime";

    // 问题id
    public final static String LU_FIELD_QUESTION_ID = "questionId";

    // 摘要保留
    public final static String LU_FIELD_SUMMARY = "summary";

    // 摘要分词 分词+拼音 保留
    public final static String LU_FIELD_SUMMARY_SEG_ALL = "summarySegAll";

    // 摘要分词 保留
    public final static String LU_FIELD_SUMMARY_SEG = "summarySeg";

    // 摘要（分词后）的拼音结果
    public final static String LU_FIELD_SUMMARY_PINYIN_SEG = "summaryPinyinSeg";

    // 文件字号 保留
    public final static String LU_FIELD_FILE_SIZE = "fileSize";

    // 文件字号分词 保留
    public final static String LU_FIELD_FILE_SIZE_SEG = "fileSizeSeg";

    // 文件字号拼音分词 保留
    public final static String LU_FIELD_FILE_SIZE_PINYIN_SEG = "fileSizePinyinSeg";

    // 文件字号分词 分词+拼音 保留
    public final static String LU_FIELD_FILE_SIZE_SEG_ALL = "fileSizeSegAll";

    // 答案id
    public final static String LU_FIELD_ANSWER_ID = "answerId";

    // 问题
    public final static String LU_FIELD_QUESTION = "question";

    // 问题分词
    public final static String LU_FIELD_QUESTION_SEG = "segQuestion";

    // 问题拼音字符串
    public final static String LU_FIELD_QUESTION_PINYIN = "questionPinyin";

    // 问题拼音的首字母拼接
    public final static String LU_FIELD_QUESTION_HEADCHARS = "questionHeadChars";

    // 问题（分词后）的拼音结果
    public final static String LU_FIELD_QUESTION_PINYIN_SEG = "questionPinyinSeg";

    // 问题的每个字拼音
    public final static String LU_FIELD_QUESTION_PINYIN_LIST = "questionPinyinList";

    // 问题：每个词、每个词的拼音、每个字、每个字的拼音拼接
    public final static String LU_FIELD_QUESTION_ALL = "questionAll";

    // 问题类型
    public final static String LU_FIELD_QUESTION_TYPE = "questionType";

    // 答案
    public final static String LU_FIELD_ANSWER = "answer";

    // 答案类型 保留
    public final static String LU_FIELD_ANSWER_TYPE = "answerType";

    // 站点id
    public final static String LU_FIELD_WEB_ID = "webId";

    // 标签名称 保留（Faq功能需要根据标签查询）
    public final static String LU_FIELD_LABEL_NAME = "labelName";

    // 分类id 保留
    public final static String LU_FIELD_CLASSES_ID = "classesId";

    // 知识属性 保留
    public final static String LU_FIELD_LABEL_ID = "labelId";

    // 发文单位 保留
    public final static String LU_FIELD_DISPATCH_UNIT = "dispatchUnit";

    // 发文时间 保留
    public final static String LU_FIELD_DISPATCH_TIME = "dispatchTime";

    // 知识的生效开始时间 保留
    public final static String LU_FIELD_START_TIME = "startTime";

    // 知识的生效结束时间 保留
    public final static String LU_FIELD_END_TIME = "endTime";

    // 知识原始知识id 保留
    public final static String LU_FIELD_ORIGINAL_KNOWLEDGE_ID = "originalKnowledgeId";

    // 知识的所属词条/关键词 保留
    public final static String LU_FIELD_ENTRY_WORD = "entryWord";

    // 问题内权重 保留
    public final static String LU_FIELD_ALL_WEIGHT = "allWeight";

    // 点击量 保留
    public final static String LU_FIELD_HITS = "hits";

    // 生效渠道 保留
    public final static String LU_FIELD_EFFECT_CHANNEL = "effectChannel";

    // 用户分类 保留
    public final static String LU_FIELD_USER_CLASSES = "userClasses";
}
