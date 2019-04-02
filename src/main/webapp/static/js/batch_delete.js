/**
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
	var ids = [];
	var listPageUrl = window.location.href;
	var sels = document.getElementsByName(name);
	for (var i = 0; i < sels.length; i++) {
		if (sels[i].checked) {
			ids.push(sels[i].value);
		}
	}
	if (ids.length == 0) {
		alert("请至少选择一条记录");
		return;
	}
	confirm("确定删除所选的" + ids.length + "条数据吗？", function() {
		$.post(url, JSON.stringify(ids), function(result) {
			if (result.code == 200) {
				alert("删除成功", {icon: 1}, function() {					
					window.location.reload();
				});
			} else {
				alert(result.msg, {icon: 2});
			}
		}, "json");
	});
}