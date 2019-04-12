/**
 * 字符串工具类
 */
var StringUtils = function () {

};

/**
 * 转换undefined,null为有效字符空串''
 */
StringUtils.transformUndefinedAndNull2EmptyString = function (str) {
    if (str === 'undefined' || str === undefined || str === 'null' || str === null) {
        str = "";
    }
    return str;
};


