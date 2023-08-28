package com.example.wangsz.data;

public class Const {
	//题目下标
	public static final int QUESTION_INDEX = 0;
	//答案下标
	public static final int ANSWER_INDEX = 1; 
	//混淆答案选项
	public static final int WRONG_INDEX = 2;
	//起始关卡
	public static final int BEGIN_STAGE = -1; 
	//起始金币
	public static final int TOTAL_COINT = 10; 
	//保存数据文件名
	public static final String FILE_NAME_SAVE_DATA = "data.dat";
	//保存的关卡下标
	public static final int INDEX_LOAD_DATA_STAGE = 0;
	//保存的金币下标
	public static final int INDEX_LOAD_DATA_COINS = 1;
	//是否第一次进入游戏的标志名称
	public static final String IS_FIRST_TIME_GAME = "is_first_time_game";
	//问题+答案
	public static final String[][] QUESTION_ANSWER = {
		{"chanqiao.png","蝉壳","知了"},
		{"duhuo.png","独活",""},
		{"guizhi.png","桂枝","木头"},
		{"huanglian.png","黄连",""},
		{"jinyinhua.png","金银花","水仙花"},
		{"niubangzi.png","牛蒡子","辛梓"},
		{"shengjiang.png","生姜","大土"},
		{"weilingxian.png","威灵仙","草木根"},
		{"xinyi.png","辛夷","花苞"},
		{"zhizi.png","栀子","花苞"}

	};
	
	//提示
	public static final String[] ANSWER_TYPE = {
            "夏天叫",
            "干药材",
            "你猜",
            "有点难",
            "是个花",
            "不常见",
            "厨房见多",
            "带仙的",
            "有难度",
            "花",
	};
	public static final String[][] ANSWER_MESSAGE = {
			{"chanqiao0.png","其全形似蝉而中空,体轻，中空，易碎。无臭，味淡。表面黄棕色，半透明，有光泽."},
			{"duhuo0.png","为伞形科植物。重齿毛当归的干燥根。春初苗刚发芽或秋末茎叶枯萎时采挖。"},
			{"guizhi0.png","樟科植物肉桂的干燥嫩枝。呈长圆柱形，多分枝。表面红棕色至棕色，断面皮部红棕色，木部黄白色至浅黄棕色，髓部略呈方形。"},
			{"huanglian0.png","多年生草本植物，叶基徨，野生或栽培于海拔1000-1900m的山谷凉湿荫蔽密林中。"},
			{"jinyinhua0.png","忍冬花初开为白色，后转为黄色。两条花蕊探在外，成双成对，形影不离。"},
			{"niubangzi0.png","菊科二年生草本植物。干燥成熟果实呈长倒卵形，略扁，微弯曲，长5～7mm，宽2～3mm。"},
			{"shengjiang0.png","姜科多年生草本植物。根茎肉质、肥厚、扁平，有芳香和辛辣味。花茎直立，被以覆瓦状疏离的鳞片"},
			{"weilingxian0.png","毛茛科，铁线莲属多年生木质藤本。茎、小枝近无毛或疏生短柔毛。"},
			{"xinyi0.png","木兰科，落叶乔木，高数丈，木有香气。色泽鲜艳，花蕾紧凑，鳞毛整齐，芳香浓郁。"},
			{"zhizi0.png","灌木，花芳香，通常单朵生于枝顶。花冠白色或乳黄色。果卵形、近球形、椭圆形或长圆形，黄色或橙红色"}

	};
	public static final String[] PASSANSWERMESSAGE = {
			"蝉蜕，中药名.功能：宣散风热、透疹利咽、退翳明目、祛风止痉。",
			"独活，中药名。味辛、苦，性微温。根入药。祛风除湿，痛痹止痛。用于风寒湿痹，要洗疼痛，少阴伏风头痛，风寒挟湿头痛。",
			"桂枝。有特异香气，味甜、微辛，皮部味较浓。补元阳、通血脉、暖脾胃，是主治里寒常用的温里药。",
			"黄连，有清热燥湿，泻火解毒之功效。其味入口极苦。清热燥湿，泻火解毒。用于湿热痞满，呕吐吞酸，泻痢，黄疸，高热神昏，心火亢盛，心烦不寐，血热吐衄，目赤，牙痛，消渴，痈肿疔疮；外治湿疹，湿疮，耳道流脓。酒黄连善清上焦火热。用于目赤，口疮。",
			"金银花，性甘寒气芳香，甘寒清热而不伤胃，芳香透达又可祛邪。金银花既能宣散风热，还善清解血毒，用于各种热性病，如身热、发疹、发斑、热毒疮痈、咽喉肿痛等",
			"牛蒡子，淡黄白色，富油性。无臭，味苦后微辛而稍麻舌。具有疏散风热，宣肺透疹，利咽散结，解毒消肿之功效。属于解表药中发散风热药。",
			"生姜，皮性辛凉，治皮肤浮肿，行皮水；生姜汁辛温，辛散胃寒力量强，多用于呕吐，具有很强的抗氧化和清除自由基作用；吃姜还能抗衰老",
			"威灵仙，根入药，能祛风湿、利尿、通经、镇痛，治风寒湿热、偏头疼、黄胆浮肿、鱼骨鲠喉、腰膝腿脚冷痛。",
			"辛夷，花蕾入药。有散风寒的功效，用于治鼻炎、降血压；又是一种名贵的香料和化工原料，且木香，亦是一种观赏绿化植物。",
			"栀子，果实清热，泻火，凉血。治热病虚烦不眠，黄疸，淋病，消渴，目赤，咽痛，吐血，衄血，血痢，尿血，热毒疮疡，扭伤肿痛",



	};

	
}
