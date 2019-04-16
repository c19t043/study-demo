/**
 * 数据工具类
 */
var NumberUtils = function () {

};

/**
 * 金额：分转元，保留2位小数
 * @param number 金额(分)
 * @returns {string} 12 -> 0.12; -12 -> -0.12
 */
NumberUtils.retain2PositionDecimal = function (number, handledFlag) {
    // 如果为0，或false等条件
    if (number === 0 || !number) {
        return "0.00";
    }
    // 如果已经有小数点,不改变
    if (number.toString().indexOf(".") !== -1) {
        return number;
    }
    if (handledFlag && number !== 0) {
        return number + ".00";
    }
    if (!(number instanceof Number)) {
        number = parseInt(number);
    }
    var yuan = Math.floor(number / 100);
    var fen = number % 100;
    if (fen < 0) {
        fen = -fen;
    }
    if (fen < 10) {
        fen = "0" + fen;
    }
    if (fen === 0) {
        fen = "00";
    }
    return yuan + "." + fen;
};