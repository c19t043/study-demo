/**
 * 字符串工具类
 */
var StringUtils = function () {

};

StringUtils.undefinedStr = 'undefined';
StringUtils.nullStr = 'null';

/**
 * 转换undefined,null为有效字符空串''
 * @param {String}str
 */
StringUtils.transformUndefinedAndNull2EmptyString = function (str) {
    if (str === this.undefinedStr ||
        str === undefined ||
        str === this.nullStr ||
        str === null) {
        str = "";
    }
    return str;
};

/**
 * 判断空
 */
StringUtils.isEmpty = function isEmpty(obj) {
    if (obj === this.undefinedStr ||
        obj === undefined ||
        obj === this.nullStr ||
        obj === null ||
        obj instanceof String && obj.trim().length === 0) {
        return true;
    } else {
        return false;
    }
};

StringUtils.trim = function trim(x) {
    return x.replace(/^\s+|\s+$/gm, '');
};

/**
 * 截取指定长度
 */

StringUtils.substring = function (content, length) {
    if (content && content.length > length) {
        content = content.substring(0, length);
    }
    return content;
};
