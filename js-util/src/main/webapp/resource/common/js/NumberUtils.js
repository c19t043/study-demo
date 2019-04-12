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
NumberUtils.retain2PositionDecimal = function (number) {
    if (number === 0 || !number) {
        return "0.00";
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
        fen = fen + "0";
    }
    if (fen === 0) {
        fen = "00";
    }
    return yuan + "." + fen;
};