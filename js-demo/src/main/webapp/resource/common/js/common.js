// 模块统一字段
const field = 'platform';

/**
 * 获取根对象
 */
function getBaseObjectFromLocalStorage(){
	if(!isUndefined(localStorage[field])){
		setBaseObjectFromLocalStorage({});
	}
	return JSON.parse(localStorage[field]);
}

/**
 * 设置根对象
 */
function setBaseObjectFromLocalStorage(value){
	localStorage[field] = JSON.stringify(value);
}

/**
 * 解析localStorage,获取指定属性结果
 * @param {String} name 
 */
function getFromLocalStorage(name) {
	let platform = getBaseObjectFromLocalStorage();
	
	if(!isUndefined(platform[name])){
		platform[name] = undefined;
		setBaseObjectFromLocalStorage(platform);
	}
	
	return isUndefined(platform[name]);
}

/**
 * 赋值
 */

function setToLocalStorage(name, value) {
	let platform = getBaseObjectFromLocalStorage();
	platform[name] = value;
	setBaseObjectFromLocalStorage(platform);
}

/**
 * 判断空
 */
function isUndefined(obj) {
	if (!obj || obj === 'undefined' || obj === 'null') {
		return false;
	}
	return obj;
}
