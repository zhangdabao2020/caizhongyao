package com.example.wangsz;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangsz.data.Const;
import com.example.wangsz.model.IAlertDialogButtonListener;
import com.example.wangsz.model.IWordButtonClickListener;
import com.example.wangsz.model.Question;
import com.example.wangsz.model.WordButton;
import com.example.wangsz.myui.MyGridView;
import com.example.wangsz.util.Util;

import com.example.wangsz.R;

public class MainActivity extends Activity implements IWordButtonClickListener,
		OnClickListener {

	/** 答案正确 */
	public final static int STATUS_ANSWER_RIGHT = 1;
	/** 答案错误 */
	public final static int STATUS_ANSWER_WRONG = 2;
	/** 答案不完整 */
	public final static int STATUS_ANSWER_LACK = 3;

	/** 闪烁次数 */
	public final static int SPASH_TIMES = 4;

	/** 删除错误文字对话框标志 */
	public final static int ID_DIALOG_DELETE_WORD = 4;
	/** 答案提示对话框标志 */
	public final static int ID_DIALOG_TIP_ANSWER = 5;
	/** 金币不足对话框标志 */
	public final static int ID_DIALOG_LACK_COINS = 6;
	/** 退出游戏对话框 */
	public final static int ID_DIALOG_ALT_F4 = 7;

	// 文字框容器
	private ArrayList<WordButton> mAllWords;

	// 已选文字框
	private ArrayList<WordButton> mBtnSelectWords;

	// 待选文字框布局
	private MyGridView mMyGridView;

	// 已选择文字框UI容器
	private LinearLayout mViewWordsContainer;

	// 过关界面
	private View mPassView;
	//查阅更多信息界面
	private View mFindMoreMessage;

	// 当前题目
	private Question mCurrentQuestion;

	// 当前关索引
	private int mCurrentStageIndex = -1;

	// 当前金币数量
	private int mCurrentCoins = Const.TOTAL_COINT;

	// 返回View
	private ImageButton mBack;
	// 金币View
	private TextView mViewCurrentCoins;
	//查看更多题目信息
	private ImageButton mFindMore;
	//关于作者
	private ImageButton mAboutAuthor;
	//关数按钮
	private ImageButton mStagesPics;
	// 删除错误答案View
	private ImageButton mViewDelete;
	// 答案提示View
	private ImageButton mViewTip;
	// 题目微信分享
	private ImageButton mViewShare;
	// 主界面当前关索引
	private TextView mCurrentStageView;
	// 题目
	private ImageView mCurrentQuestionBG;
	// 提示
	private TextView mCurrentQuestionTip;

	// 过关界面
	// 当前关索引显示
	private TextView mCurrentStagePassView;
	// 当前关答案
	private TextView mCurrentAnswerPassView;
	// 下一关View
	private ImageButton mViewNext;
	// 微信分享View
	private ImageButton mViewSharedWeixin;

	//查阅题目更多信息界面
	//查阅题目的更多图片
	private ImageView mFingMoreMessageBG;
	//查阅题目的更多介绍提示
	private TextView mFingMoreMessageTextView;
	//返回按钮
	private ImageButton mFingMessageBack;
	//浮动按钮界面
	private View mFloatButtons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 读取保存数据
		int[] datas = Util.loadData(this);
		mCurrentStageIndex = datas[Const.INDEX_LOAD_DATA_STAGE];
		mCurrentCoins = datas[Const.INDEX_LOAD_DATA_COINS];

		// 初始化控件
		mBack = (ImageButton) findViewById(R.id.back);
		mBack.setOnClickListener(this);

		mFindMore = (ImageButton) findViewById(R.id.fing_more);
		mFindMore.setOnClickListener(this);

		mAboutAuthor = (ImageButton) findViewById(R.id.about_author);
		mAboutAuthor.setOnClickListener(this);
		
		mStagesPics = (ImageButton) findViewById(R.id.stage_index_pics);
		mStagesPics.setOnClickListener(this);
		
		mMyGridView = (MyGridView) findViewById(R.id.gridview);
		// 注册监听
		mMyGridView.registOnWordButtonClickListener(this);

		mViewWordsContainer = (LinearLayout) findViewById(R.id.word_select_container);

		mViewCurrentCoins = (TextView) findViewById(R.id.money);
		mViewCurrentCoins.setText(mCurrentCoins + "");

		mCurrentQuestionBG = (ImageView) findViewById(R.id.imgv_question_bg);
		mCurrentQuestionTip = (TextView) findViewById(R.id.tv_tip_for_answer);

		mFingMoreMessageBG = (ImageView) findViewById(R.id.more_message_bg);
		mFingMoreMessageTextView = (TextView) findViewById(R.id.more_messageview);

		// 删除错误答案事件
		mViewDelete = (ImageButton) findViewById(R.id.btn_delete_word);
		mViewDelete.setOnClickListener(this);
		// 答案提示事件
		mViewTip = (ImageButton) findViewById(R.id.btn_tip_answer);
		mViewTip.setOnClickListener(this);
		// 题目微信分享
		mViewShare = (ImageButton) findViewById(R.id.imgbtn_question_share);
		mViewShare.setOnClickListener(this);

		// 初始化游戏数据
		initCurrentStageData();

	}

	@Override
	public void onPause() {
		super.onPause();
		// 保存数据
		Util.saveData(MainActivity.this, mCurrentStageIndex - 1, mCurrentCoins);
	}

	/**
	 * 加载当前关数据
	 */
	private void initCurrentStageData() {

		// 读取当前关题目数据
		mCurrentQuestion = loadStageQuestionInfo(++mCurrentStageIndex);
		// 当前关题目+提示
		setQuestion(mCurrentQuestion);
		// 初始化已选择框
		mBtnSelectWords = initWordSelect();

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// 清空上一关答案
		mViewWordsContainer.removeAllViews();
		// 初始化本关答案文字框
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton,
					params);
		}

		// 获取当前关索引
		mCurrentStageView = (TextView) findViewById(R.id.tx_current_stage);
		if (mCurrentStageView != null) {
			mCurrentStageView.setText((mCurrentStageIndex + 1) + "");
		}
		// 获得数据
		mAllWords = initAllWord();
		// 更新数据- MyGridView
		mMyGridView.updateData(mAllWords);
	}

	/**
	 * 当前关题目显示+提示显示+提示信息图片+文字
	 * 
	 * @param mCurrentQuestion
	 */
	private void setQuestion(Question mCurrentQuestion) {
		if (mCurrentQuestionBG != null) {
			mCurrentQuestionBG
					.setImageBitmap(getCurrentQuestionBG(mCurrentQuestion));
		}

		if (mCurrentQuestionTip != null) {
			mCurrentQuestionTip.setText(mCurrentQuestion.getType());
		}


	}

	/**
	 * 得到当前关卡对应图片
	 * 
	 * @param mCurrentQuestion
	 * @return
	 */
	private Bitmap getCurrentQuestionBG(Question mCurrentQuestion) {
		AssetManager assets = getAssets();
		InputStream is = null;
		try {
			is = assets.open(mCurrentQuestion.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);//获取位图
		return bitmap;
	}
	/**
	 * 得到题目更多信息图片
	 * mFingMoreMessageBG
	 *
	 */
	private Bitmap getAnswerCurrentMessageBG(Question mCurrentQuestion) {
		AssetManager assets = getAssets();
		InputStream is = null;
		try {
			is = assets.open(mCurrentQuestion.getAnserMessag());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);//获取位图
		return bitmap;
	}
	/**
	 * 获得题目更多信息的图片加文字
	 *
	 */


	/**
	 * 获得当前关题目
	 * 
	 * @param stageIndex
	 * @return
	 */
	private Question loadStageQuestionInfo(int stageIndex) {
		Question question = new Question();

		String[] stage = Const.QUESTION_ANSWER[stageIndex];
		String[] stageF = Const.ANSWER_MESSAGE[stageIndex];
		question.setAnserMessage(stageF[Const.QUESTION_INDEX]);
		question.setAnswerMessageText(stageF[Const.ANSWER_INDEX]);
		question.setContent(stage[Const.QUESTION_INDEX]);
		question.setAnswer(stage[Const.ANSWER_INDEX]);
		question.setWrongAnswer(stage[Const.WRONG_INDEX]);
		question.setType(Const.ANSWER_TYPE[stageIndex]);
		question.setPassAnswerMessage(Const.PASSANSWERMESSAGE[stageIndex]);

		return question;
	}

	/**
	 * 初始化待选文字框
	 */
	private ArrayList<WordButton> initAllWord() {
		ArrayList<WordButton> data = new ArrayList<WordButton>();

		// 获得所有待选文字
		String[] words = generateWords();
		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			WordButton button = new WordButton();

			button.mWordString = words[i];

			data.add(button);
		}

		return data;
	}

	/**
	 * 初始化已选择文字框
	 * 
	 * @return
	 */
	private ArrayList<WordButton> initWordSelect() {
		ArrayList<WordButton> data = new ArrayList<WordButton>();

		for (int i = 0; i < mCurrentQuestion.getAnswerLength(); i++) {
			View view = Util.getView(MainActivity.this,
					R.layout.self_ui_gridview_item);

			final WordButton holder = new WordButton();

			holder.mViewButton = (Button) view.findViewById(R.id.item_btn);
			holder.mViewButton.setTextColor(Color.WHITE);
			holder.mViewButton.setText("");
			holder.mIsVisiable = false;

			holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
			holder.mViewButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					clearTheAnswer(holder);
					// 答案错误时，点击文字去掉文字之后设置文字为白色(checkResultIndex ==
					// STATUS_ANSWER_LACK)
					checkResult();
				}

			});

			data.add(holder);
		}
		return data;
	}

	// 待选文字框点击事件
	@Override
	public void onWordButtonClick(WordButton wordButton) {
		// 记录已有文字个数
		int mount = 0;
		// 已选文字框填充
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			if ("".equals(mBtnSelectWords.get(i).mWordString)) {
				mBtnSelectWords.get(i).mViewButton
						.setText(wordButton.mWordString);
				mBtnSelectWords.get(i).mIsVisiable = true;
				mBtnSelectWords.get(i).mWordString = wordButton.mWordString;
				mBtnSelectWords.get(i).mIndex = wordButton.mIndex;

				break;
			} else {
				mount++;
			}
		}

		if (mount < mBtnSelectWords.size()) {
			// 待选文字框设空
			/*
			 * wordButton.mViewButton.setVisibility(View.INVISIBLE);
			 * wordButton.mIsVisiable = false;
			 */
			setButtonVisiable(wordButton, View.INVISIBLE);
		}

		// 答案处理
		checkResult();

	}

	/**
	 * 答案状态检测及处理
	 * 
	 */
	private void checkResult() {
		// 获得答案状态
		int checkResultIndex = checkTheAnswer();

		// 状态处理
		if (checkResultIndex == STATUS_ANSWER_RIGHT) {
			// 过关+获得奖励
			handlePassEvent();
		} else if (checkResultIndex == STATUS_ANSWER_WRONG) {
			// 闪烁提示
			sparkTheWords();
		} else if (checkResultIndex == STATUS_ANSWER_LACK) {
			// 设置文字为白色
			for (int i = 0; i < mBtnSelectWords.size(); i++) {
				mBtnSelectWords.get(i).mViewButton.setTextColor(Color.WHITE);
			}
		}
	}

	/**
	 * 处理过关界面及事件
	 */
	private void handlePassEvent() {
		mPassView = (LinearLayout) this.findViewById(R.id.pass_view);
		mPassView.setVisibility(View.VISIBLE);
		mFloatButtons = (RelativeLayout) this.findViewById(R.id.float_button);
		mFloatButtons.setVisibility(View.GONE);
		// 在过关界面上设置关数+答案 显示
		mCurrentStagePassView = (TextView) findViewById(R.id.stage_index);
		if (mCurrentStagePassView != null) {
			mCurrentStagePassView.setText((mCurrentStageIndex + 1) + "");
		}
		mCurrentAnswerPassView = (TextView) findViewById(R.id.pass_answer);
		if (mCurrentAnswerPassView != null) {
			mCurrentAnswerPassView.setText(mCurrentQuestion.getPassAnswerMessage());
		}


		// 下一关按钮
		mViewNext = (ImageButton) findViewById(R.id.btn_next);
		mViewNext.setOnClickListener(this);
		// 微信分享按钮
		mViewSharedWeixin = (ImageButton) findViewById(R.id.btn_share);
		mViewSharedWeixin.setOnClickListener(this);

	}


    //查看更多信息界面
	private void handleFindMoreMessage(){
		mFindMoreMessage = (LinearLayout) this.findViewById(R.id.fing_more_message);
		mFindMoreMessage.setVisibility(View.VISIBLE);
		mFloatButtons = (RelativeLayout) this.findViewById(R.id.float_button);
		mFloatButtons.setVisibility(View.GONE);

		if(mFingMoreMessageBG != null){
			mFingMoreMessageBG.setImageBitmap(getAnswerCurrentMessageBG(mCurrentQuestion));
		}
		if(mFingMoreMessageTextView != null){
			mFingMoreMessageTextView.setText(mCurrentQuestion.getAnswerMessageText());
		}
		mFingMessageBack = (ImageButton) findViewById(R.id.btn_back);
		mFingMessageBack.setOnClickListener(this);

	}
	// 已选框点击事件---清除
	private void clearTheAnswer(WordButton wordButton) {
		wordButton.mViewButton.setText("");
		wordButton.mWordString = "";
		wordButton.mIsVisiable = false;

		// 待选框文字复原
		/*
		 * mAllWords.get(wordButton.mIndex).mViewButton
		 * .setVisibility(View.VISIBLE);
		 * mAllWords.get(wordButton.mIndex).mIsVisiable = true;
		 */
		setButtonVisiable(mAllWords.get(wordButton.mIndex), View.VISIBLE);
	}

	/**
	 * 设置待选文字框是否可见
	 * 
	 * @param button
	 * @param dbvisibiablity
	 */
	private void setButtonVisiable(WordButton button, int visibility) {
		button.mViewButton.setVisibility(visibility);
		button.mIsVisiable = (visibility == View.VISIBLE) ? true : false;
	}

	/**
	 * 生成所有待选文字
	 * 
	 * @return
	 */
	private String[] generateWords() {
		String[] words = new String[MyGridView.COUNTS_WORDS];

		Random random = new Random();

		int wordIndex = 0;
		// 存入答案
		for (int i = 0; i < mCurrentQuestion.getAnswerLength(); i++) {
			words[i] = mCurrentQuestion.getAnswerChars()[i] + "";
			wordIndex ++;
		}

		//获取混淆答案选项
		for (int i = 0; i < mCurrentQuestion.getWrongAnswer().length(); i++) {
			words[wordIndex] = mCurrentQuestion.getWrongAnswerChars()[i] + "";
			wordIndex ++;
		}
		// 获取随机文字
		for (int i = wordIndex; i < MyGridView.COUNTS_WORDS; i++) {
			words[i] = getRandomChar() + "";
		}

		// 打乱字符顺序
		// 从后面随机选一个字符与第一个字符互换位置，然后在剩下的字符中再选一个与第二个互换，...
		for (int i = MyGridView.COUNTS_WORDS - 1; i >= 0; i--) {
			int index = random.nextInt(i + 1);

			String buf = words[index];
			words[index] = words[i];
			words[i] = buf;
		}

		return words;
	}

	/**
	 * 生成一个随机汉字
	 * 
	 * @return
	 */
	private char getRandomChar() {
		String str = "";
		int highPos;
		int lowPos;

		Random random = new Random();

		highPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = (161 + Math.abs(random.nextInt(93)));

		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(highPos)).byteValue();
		b[1] = (Integer.valueOf(lowPos)).byteValue();

		try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str.charAt(0);
	}

	/**
	 * 答案检测
	 * 
	 * @return
	 */
	private int checkTheAnswer() {
		// 答案长度检测
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			// 如果有空字符，则答案还不完整
			if (mBtnSelectWords.get(i).mWordString.length() == 0) {
				return STATUS_ANSWER_LACK;
			}
		}

		// 答案正确性检测
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			sb.append(mBtnSelectWords.get(i).mWordString);
		}

		if (sb.toString().equals(mCurrentQuestion.getAnswer())) {
			return STATUS_ANSWER_RIGHT;
		} else {
			return STATUS_ANSWER_WRONG;
		}
	}

	/**
	 * 文字闪烁
	 */
	private void sparkTheWords() {
		// 定时器相关
		TimerTask timerTask = new TimerTask() {
			boolean mChange = false;
			int mSpardTimes = 0;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {
						if (++mSpardTimes > SPASH_TIMES) {
							return;
						}

						// 执行闪烁逻辑：交替显示红色白色
						for (int i = 0; i < mBtnSelectWords.size(); i++) {
							mBtnSelectWords.get(i).mViewButton
									.setTextColor(mChange ? Color.RED
											: Color.WHITE);
						}

						mChange = !mChange;
					}
				});
			}
		};

		Timer timer = new Timer();
		timer.schedule(timerTask, 1, 150);
	}

	// 提示按钮点击事件
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
		// 返回退出提示
		  case R.id.back:
			showConfirmDialog(ID_DIALOG_ALT_F4);
			break;
		//关于作者
		  case R.id.about_author:
			Util.StartActivity(this, AboutAuthorView.class);
			break;
		  case R.id.stage_index_pics:
			Util.StartActivity(this, MyPicsView.class);
			break;
		// 删除提示
		  case R.id.btn_delete_word:
			// deleteOneWord();
			showConfirmDialog(ID_DIALOG_DELETE_WORD);
			break;
		// 答案提示
		  case R.id.btn_tip_answer:
			// tipOneWord();
			showConfirmDialog(ID_DIALOG_TIP_ANSWER);
			break;
		// 题目微信分享
		  case R.id.imgbtn_question_share:
			Toast.makeText(getApplicationContext(), "sorry,app is not online.",
					Toast.LENGTH_SHORT).show();
			break;
		// 下一题
		  case R.id.btn_next:
			if (judegAppPassed()) {
				// 进入通关界面
				Util.StartActivity(MainActivity.this, AllPassView.class);
			} else {
				// 开始新一关
				mPassView.setVisibility(View.GONE);
				mFloatButtons.setVisibility(View.VISIBLE);
				// 加载关卡数据
				handleCoins(3);
				initCurrentStageData();
			}
			break;
		// 微信分享
		    case R.id.btn_share:
			Toast.makeText(getApplicationContext(), "sorry，app is not online.",
					Toast.LENGTH_SHORT).show();
			break;
		//查阅更多题目信息
			case R.id.fing_more:
				handleFindMoreMessage();
				break;
			case R.id.btn_back: {
				mFindMoreMessage.setVisibility(View.GONE);
				mFloatButtons.setVisibility(View.VISIBLE);
			}
				break;







		}
	}

	/**
	 * 显示相应提示对话框
	 * 
	 * @param id
	 */
	private void showConfirmDialog(int id) {
		switch (id) {
		case ID_DIALOG_DELETE_WORD:
			Util.showDialog(MainActivity.this, "确认花掉" + getDeleteWordsCoins()
					+ "个金币去掉一个错误答案？", mBtnOkDeleteWordListener);
			break;
		case ID_DIALOG_TIP_ANSWER:
			Util.showDialog(MainActivity.this, "确认花掉" + getTipAnswerCoins()
					+ "个金币获得一个文字提示？", mBtnOkTipAnswerListener);
			break;
		case ID_DIALOG_LACK_COINS:
			Util.showDialog(MainActivity.this, "金币不足，请自力更生！！！",
					mBtnOkLackCoinsListener);
			break;
		case ID_DIALOG_ALT_F4:
			Util.showDialog(MainActivity.this, "是否退出游戏？", mBtnOkAltF4Listener);
			break;
		}
	}

	// 自定义AlertDialog事件响应
	// 删除错误答案
	private IAlertDialogButtonListener mBtnOkDeleteWordListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			deleteOneWord();
		}
	};

	// 提示正确答案
	private IAlertDialogButtonListener mBtnOkTipAnswerListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			tipOneWord();
		}
	};

	// 金币不足
	private IAlertDialogButtonListener mBtnOkLackCoinsListener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub

		}
	};

	// 退出游戏
	private IAlertDialogButtonListener mBtnOkAltF4Listener = new IAlertDialogButtonListener() {

		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			MainActivity.this.finish();
		}
	};

	/**
	 * 删除文字
	 */
	private void deleteOneWord() {
		// 减少金币
		if (!handleCoins(-getDeleteWordsCoins())) {
			// 金币不够，显示提示对话框
			showConfirmDialog(ID_DIALOG_LACK_COINS);
			return;
		}

		// 将指定Wordbutton设置为不可见
		setButtonVisiable(findNotAnswerWord(), View.INVISIBLE);
	}

	/**
	 * 找到一个不是答案的文字，并且当前可见
	 * 
	 * @return
	 */
	private WordButton findNotAnswerWord() {
		Random random = new Random();
		WordButton buf = null;

		while (true) {
			int index = random.nextInt(MyGridView.COUNTS_WORDS);

			buf = mAllWords.get(index);

			if (buf.mIsVisiable && !isTheAnswerWord(buf)) {
				return buf;
			}
		}
	}

	/**
	 * 判断是否是答案中的文字
	 * 
	 * @param button
	 * @return true 是答案中的文字， false 不是
	 */
	private boolean isTheAnswerWord(WordButton word) {
		for (int i = 0; i < mCurrentQuestion.getAnswerLength(); i++) {
			if (word.mWordString.equals(mCurrentQuestion.getAnswerChars()[i] + "")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 提示答案
	 */
	private void tipOneWord() {
		// 减少金币
		if (!handleCoins(-getTipAnswerCoins())) {
			// 金币不够，显示提示对话框
			showConfirmDialog(ID_DIALOG_LACK_COINS);
			return;
		}

		boolean tipWord = false;
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			if (mBtnSelectWords.get(i).mWordString.length() == 0) {
				// 根据当前答案框状态选择相应的文字填入
				onWordButtonClick(findIsAnswerWord(i));
				// 如果当前空格的文字已经在已选框，把文字显示在正确的位置，并隐藏掉之前的文字
				if (!findIsAnswerWord(i).mIsVisiable) {
					for (int j = 0; j < mBtnSelectWords.size(); j++) {
						if (mBtnSelectWords.get(j).mWordString
								.equals(mCurrentQuestion.getAnswerChars()[i]
										+ "")) {
							mBtnSelectWords.get(j).mViewButton.setText("");
							mBtnSelectWords.get(j).mWordString = "";
							mBtnSelectWords.get(j).mIsVisiable = false;
							break;
						}
					}
				}

				tipWord = true;
				break;
			}
		}

		// 没有找到可以填充的位置
		if (!tipWord) {
			// 闪烁文字提示用户
			sparkTheWords();
			// 返回金币
			handleCoins(getTipAnswerCoins());
		}
	}

	/**
	 * 找到一个答案文字
	 * 
	 * @param index
	 *            当前需要填入答案框的索引
	 * @return
	 */
	private WordButton findIsAnswerWord(int index) {
		WordButton buf = null;

		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			buf = mAllWords.get(i);

			if (buf.mWordString.equals(mCurrentQuestion.getAnswerChars()[index]
					+ "")) {
				return buf;
			}
		}

		return null;
	}

	/**
	 * 增加或者减少指定数量的金币
	 * 
	 * @param data
	 * @return true 增加或减少成功 ， false 失败
	 */
	private boolean handleCoins(int data) {
		// 判断当前金币总数量是否可被减少
		if (mCurrentCoins + data >= 0) {
			mCurrentCoins += data;

			mViewCurrentCoins.setText(mCurrentCoins + "");

			return true;
		} else {
			// 金币不够
			return false;
		}
	}

	/**
	 * 从配置文件中获取删除文字操作所用金币
	 * 
	 * @return
	 */
	private int getDeleteWordsCoins() {
		return this.getResources().getInteger(R.integer.pay_delete_words);
	}

	/**
	 * 从配置文件中获取提示答案文字操作所用金币
	 * 
	 * @return
	 */
	private int getTipAnswerCoins() {
		return this.getResources().getInteger(R.integer.pay_tip_answer);
	}

	/**
	 * 判断是否通关
	 * 
	 * @return
	 */
	private boolean judegAppPassed() {
		return (mCurrentStageIndex == Const.QUESTION_ANSWER.length - 1);
	}

	/**
	 * 返回退出事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showConfirmDialog(ID_DIALOG_ALT_F4);
			return false;
		}
		return false;
	}
}
