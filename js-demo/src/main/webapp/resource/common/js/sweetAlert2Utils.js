/**
 * Alert提示框对象属性说明
 * showCloseButton：是否显示关闭按钮
 * showCancelButton：是否显示取消按钮
 * showConfirmButton：是否显示确认按钮
 *
 * confirmButtonText：确认按钮显示文本
 * cancelButtonText：取消按钮显示文本
 *
 * type: 显示对应图标（success,error,info）
 * title：显示标题
 * timer: 弹出框存在时间
 */
var AlertUtilObject = function () {
    return {
        info: function (html) {
            Swal.fire({
                type: "info",
                title: '提示',
                html: html,
                showCloseButton: true,
                showCancelButton: true,
                reverseButtons: true,
                confirmButtonText: "删除",
                cancelButtonText: "取消"
            }).then((result) => {
                if(result.value) {
                    this.success();
                }
            });
        },
        success: function () {
            Swal.fire({
                type: 'success',
                title: '操作成功！',
                timer: 1500,
                showConfirmButton: false,
            })
        },
        error: function () {
            Swal.fire({
                type: 'error',
                title: '操作失败！',
                timer: 1500,
                showConfirmButton: false
            })
        }
    }
};

var AlertUtils = new AlertUtilObject();

