package com.example.wangsz.model;

public class Question {

    //题目内容
    private String mContent;

    //题目答案
    private String mAnswer;
    //题目图片信息
    private String mAnswerContentMessage;
    //题目文本信息
    private String mAnswerMessage;
    //题目混淆答案选项
    private String mWrongAnswer;

    //答案分类
    private String mType;
    //过关答安显示信息
    private String mPassMessage;

    //答案长度
    private int mAnswerLength;

    public char[] getAnswerChars() {
        return mAnswer.toCharArray();
    }

    public char[] getWrongAnswerChars() {
        return mWrongAnswer.toCharArray();
    }

    public String getContent() {
        return mContent;
    }

    public String getAnserMessag() {
        return mAnswerContentMessage;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public void setAnserMessage(String messageContent) {
        this.mAnswerContentMessage = messageContent;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public String getAnswerMessageText() {
        return mAnswerMessage;
    }

    public void setAnswer(String answer) {
        this.mAnswer = answer;
        this.mAnswerLength = answer.length();
    }

    public void setAnswerMessageText(String answerMessage) {
        this.mAnswerMessage = answerMessage;

    }

    public String getWrongAnswer() {
        return mWrongAnswer;
    }

    public void setWrongAnswer(String wrongAnswer) {
        this.mWrongAnswer = wrongAnswer;
    }

    public String getType() {
        return mType;
    }

    public String getPassAnswerMessage() {
        return mPassMessage;
    }

    public void setPassAnswerMessage(String Message){
        this.mPassMessage = Message;
    }




	public void setType(String type) {
		this.mType = type;
	}

	public int getAnswerLength() {
		return mAnswerLength;
	}
	
}
