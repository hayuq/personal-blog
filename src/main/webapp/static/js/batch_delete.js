/**
 * 创建人：熊俊丞 
 * 创建时间：2016年8月8日 
 * 功能：实现全选及批量删除功能
 */
/** 全选 */
function selectAll(name, obj) {
	var chks = document.getElementsByName(name);
	var isCheckAll = obj.checked;
	for ( var i in chks) {
		chks[i].checked = isCheckAll;
	}
}

/** 批量删除复选框选中的数据 */
function deleteSelected(name, url) {
	var str = "", count = 0;
	var sel = document.getElementsByName(name);
	for (var i = 0; i < sel.length; i++)
		if (sel[i].checked) {
			count++;
			str += sel[i].value + ",";
		}
	str = str.substring(0, str.lastIndexOf(","));
	if (count == 0) {
		alert("请至少选择一条记录");
		return false;
	}
	if (confirm("确定删除所选数据吗？")) {
		window.location.href = url + "?ids=" + str;
	}
}