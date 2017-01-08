//实例化编辑器
var editor = UE.getEditor('editor', {
	initialFrameWidth : 900, //初始化编辑器宽度,默认1000
	initialFrameHeight : 300, /* 初始化编辑器宽度,默认320 */
	elementPathEnabled : false, //是否启用元素路径，默认是显示
	autoHeightEnabled : false, //是否自动长高,默认true
	scaleEnabled : false, //是否可以拉伸长高,默认true
	allowDivTransToP : false
});