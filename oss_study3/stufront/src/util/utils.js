import Vue from 'vue';
import {
	Message,
	MessageBox
} from 'element-ui';
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
import printJS from 'print-js'
import constant from 'util/constant.js';
import caches from 'util/caches.js';

let utils = {
	//消息内容
	msg: {
		success(message, time, callBack) {
			this.base(message, "success", time, callBack);
		},
		warning(message, time, callBack) {
			this.base(message, "warning", time, callBack);
		},
		info(message, time, callBack) {
			this.base(message, "info", time, callBack);
		},
		exception(message, time, callBack) {
			this.base(message, "error", time, callBack);
		},
		base(message, type, time, callBack) {
			var duration = null;
			if (typeof(arguments[2]) == 'function') {
				callBack = arguments[2];
				duration = 1000;
			} else {
				duration = time == null ? 1000 : time;
			}
			Message({
				duration,
				message,
				type: type,
				onClose: callBack
			});
		}
	},
	//询问框
	confirm: {
		success(title, message, successCallback, cancelCallback) {
			this.base(title, message, "success", successCallback, cancelCallback);
		},
		warning(title, message, successCallback, cancelCallback) {
			this.base(title, message, "warning", successCallback, cancelCallback);
		},
		info(title, message, successCallback, cancelCallback) {
			this.base(title, message, "info", successCallback, cancelCallback);
		},
		exception(title, message, successCallback, cancelCallback) {
			this.base(title, message, "error", successCallback, cancelCallback);
		},
		base(title, message, type, successCallback, cancelCallback) {
			MessageBox.confirm(message, title, {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: type
				})
				.then(() => {
					if (successCallback) {
						successCallback();
					}
				})
				.catch(() => {
					if (cancelCallback) {
						cancelCallback
					}
				});
		}
	},
	// 合并对象
	merger(...sources) {
		return Object.assign({}, ...sources);
	},
	checkField(form, field, success, fail) {
		form.validateField(field, (valid) => {
			if (!valid) {
				success();
			} else {
				if (fail) {
					fail();
				}
				return false;
			}
		});
	},
	checkForm(form, success, fail) {
		form.validate((valid) => {
			if (valid) {
				success();
			} else {
				if (fail) {
					fail();
				}
				return false;
			}
		});
	},
	newRequest() {
		return new constant.requestObj();
	},
	newResponse() {
		return new constant.responseObj();
	},
	// convertSimpleTreeToStandardTree(items, simpleFormat, standardFormat) {
	// 	function getChild(pid, items) {
	// 		let result = [];
	// 		if (items && items.length > 0) {
	// 			for (let item of items) {
	// 				if (item[simpleFormat.pid] == pid) {
	// 					let obj = {};
	// 					obj[standardFormat.id] = item[simpleFormat.id];
	// 					obj[standardFormat.pid] = item[simpleFormat.pid];
	// 					obj[standardFormat.label] = item[simpleFormat.label];
	// 					let child = getChild(item[simpleFormat.id], items);
	// 					if (child && child.length > 0) {
	// 						obj[standardFormat.children] = child;
	// 					}
	// 					obj = utils.merger(item, obj);
	// 					result.push(obj);
	// 				}
	// 			}
	// 		}
	// 		return result;
	// 	}

	// 	function SimpleFormat() {
	// 		this.id = "id";
	// 		this.label = "label";
	// 		this.pid = "pid";
	// 		this.defaultParent = "";
	// 	}

	// 	function StandardFormat() {
	// 		this.id = "id";
	// 		this.label = "label";
	// 		this.pid = "pid";
	// 		this.children = "children";
	// 	}
	// 	let begin = Date.now();
	// 	let result = [];
	// 	simpleFormat = utils.merger(new SimpleFormat(), simpleFormat);
	// 	standardFormat = utils.merger(new StandardFormat(), standardFormat);
	// 	if (items && items.length > 0) {
	// 		for (let item of items) {
	// 			if (item[simpleFormat.pid] == simpleFormat.defaultParent) {
	// 				let obj = {};
	// 				obj[standardFormat.id] = item[simpleFormat.id];
	// 				obj[standardFormat.pid] = simpleFormat.defaultParent;
	// 				obj[standardFormat.label] = item[simpleFormat.label];
	// 				let child = getChild(item[simpleFormat.id], items);
	// 				if (child && child.length > 0) {
	// 					obj[standardFormat.children] = child;
	// 				}
	// 				obj = utils.merger(item, obj);
	// 				result.push(obj);
	// 			}
	// 		}
	// 	}
	// 	let end = Date.now();
	// 	console.log("convertSimpleTreeToStandardTree耗时：" + (end - begin))
	// 	return result;
	// },
	convertSimpleTreeToStandardTree(items, simpleFormat, standardFormat) {

		function SimpleFormat() {
			this.id = "id";
			this.label = "label";
			this.pid = "pid";
			this.defaultParent = "";
		}

		function StandardFormat() {
			this.id = "id";
			this.label = "label";
			this.pid = "pid";
			this.children = "children";
		}
		simpleFormat = utils.merger(new SimpleFormat(), simpleFormat);
		standardFormat = utils.merger(new StandardFormat(), standardFormat);
		let result = [];

		function arrayToTree(items) {
			const result = []; // 存放结果集
			const itemMap = {}; // 
			for (const item of items) {
				const id = item[simpleFormat.id];
				const pid = item[simpleFormat.pid];
				if (!itemMap[id]) {
					itemMap[id] = {}
					itemMap[id][standardFormat.children] = [];
				}
				let obj = {};
				obj[standardFormat.id] = item[simpleFormat.id];
				obj[standardFormat.pid] = item[simpleFormat.pid];
				obj[standardFormat.label] = item[simpleFormat.label];
				obj[standardFormat.children] = itemMap[id][standardFormat.children];
				itemMap[id] = utils.merger(item, obj);

				const treeItem = itemMap[id];

				if (pid == simpleFormat.defaultParent) {
					result.push(treeItem);
				} else {
					if (!itemMap[pid]) {
						itemMap[pid] = {};
						itemMap[pid][standardFormat.children] = [];
					}
					itemMap[pid][standardFormat.children].push(treeItem)
				}
			}
			return result;
		}
		result = arrayToTree(items);
		return result;
	},
	array: {
		//并集
		union(arr1, arr2) {
			return Array.from(new Set([...arr1, ...arr2]));
		},
		//交集
		intersection(arr1, arr2) {
			return arr1.filter(item => new Set(arr2).has(item));
		},
		//补集
		complement(arr1, arr2) {
			return [...arr1.filter(item => !new Set(arr2).has(item)), ...arr2.filter(item => !new Set(arr1).has(
				item))];
		},
		//差集
		diff(arr1, arr2) {
			return arr1.filter(item => !new Set(arr2).has(item));
		},
		//包含
		includes(arr1, arr2) {
			return arr2.some(val => arr1.includes(val));
		},
		//全包含
		includesAll(arr1, arr2) {
			return arr2.every(val => arr1.includes(val));
		}
	},
	file: {
		generateFile(out, fileName) {
			try {
				FileSaver.saveAs(new Blob([out], {
					type: 'application/octet-stream'
				}), fileName)
			} catch (e) {
				if (typeof console !== 'undefined') {
					console.log(e, out)
				}
			}
		},
		generateExcel(dom, excelProp) {
			var sheetname = excelProp.sheetname;
			var excelName = excelProp.excelName;
			var ws = XLSX.utils.table_to_sheet(dom);
			var Sheets = {};
			Sheets[sheetname] = ws;
			var workbook = {
				SheetNames: [sheetname],
				Sheets: Sheets
			};
			/* get binary string as output */
			var wbout = XLSX.write(workbook, {
				bookType: 'xlsx',
				bookSST: true,
				type: 'array'
			})
			this.generateFile(wbout, excelName + '.xlsx');
		}
	},
	print: {
		printHtml(domId, printProp) {
			var printTitle = printProp.printTitle;
			var printTitleStyle = printProp.printTitleStyle;
			var printStyle = printProp.printStyle;
			var callback = printProp.callback;
			printJS({
				printable: domId,
				type: 'html',
				scanStyles: false,
				documentTitle: printTitle,
				headerStyle: printTitleStyle,
				style: printStyle,
				onPrintDialogClose: callback()
			})
		},
	}
}

export default utils;
