package cn.cjf.util;

import cn.cjf.util.vo.RowVo;
import org.apache.commons.codec.Charsets;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;


/**
 * 表格文件工具类
 */
public class ExcelUtil {
    /**
     * 读取用户导入的Excel数据
     *
     * @param skipRow 跳过的行数
     */
    public static List<RowVo> readExcel(File file, int skipRow) throws Exception {
        Workbook wb = null;
        List<RowVo> list = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(file);
            String fileName = file.getName();
            if (fileName.endsWith("xls")) {
                // 解析xls格式
                wb = new HSSFWorkbook(inputStream);
            } else if (fileName.endsWith("xlsx")) {
                // 解析xlsx格式
                wb = new XSSFWorkbook(inputStream);
            }
            // 第一个工作表
            Sheet sheet = wb.getSheetAt(0);

            // 得到第一行的下标，此处加skipRow目的是为了避免读取活动首行
            int firstRowIndex = sheet.getFirstRowNum() + skipRow;
            // 得到最后一行的下标 ，他们之差代表总行数
            int lastRowIndex = sheet.getLastRowNum();

            // 循环读取数据
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {

                Row row = sheet.getRow(rIndex);

                if (row == null) {
                    continue;
                }

                //实例化一个RowVo
                RowVo vo = new RowVo();

                // 得到第一列
                int firstCellIndex = row.getFirstCellNum();
                // 得到最后一列，他们之差是总列数
                int lastCellIndex = row.getLastCellNum();
                //用于保存该行中所有列的值
                List<String> columnValues = new ArrayList<>();

                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                    Cell cell = row.getCell(cIndex);
                    if (cell != null) {
                        columnValues.add(cell.toString());
                    }
                }
                if (!columnValues.isEmpty()) {
                    vo.setColumnValues(columnValues);
                    //将该行数据添加至列表
                    list.add(vo);
                }
            }
            file.delete(); //将文件从服务器删除
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    public static void exportExcel(String fileName, Consumer<HSSFWorkbook> consumer, HttpServletResponse response) throws IOException {
        final String fileSuffix = ".xls";
        final String specialChar = ".";
        if (fileName.contains(specialChar)) {
            fileName += fileSuffix;
        }
        try (HSSFWorkbook workbook = new HSSFWorkbook();
             OutputStream os = response.getOutputStream()) {
            //1.创建工作簿
            consumer.accept(workbook);

            //清除response中的缓存
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String((fileName).getBytes(Charsets.UTF_8), Charsets.ISO_8859_1));
            response.setContentType("application/vnd.ms-excel;charset=utf-8");

            //5.输出
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 导出excel
     */
    public static void exportExcel(List<?> dataList, String fileName, HttpServletResponse response) throws IOException {
        ExcelUtil.exportExcel(fileName, (workbook) -> {

            // 创建工作表
            HSSFSheet sheet = createSheetAndDefaultHandle(workbook, "月结算报表");

            sheet.setColumnWidth(3,50);

            // 合并单元格
            // 行,列从0开始计算
            mergeCell(sheet, 0, 1, 0, 0);

            // 创建标题
            setSameRowDifferentColumnCellValueAndDefaultHandle(sheet.createRow(0),
                    createTitleCellStyle(workbook),
                    "月结算报表");

            // 创建头
            setSameRowDifferentColumnCellValueAndDefaultHandle(sheet.createRow(2),
                    createHeaderCellStyle(workbook),
                    "序号");

            // 将数据列表写入excel
            if (dataList != null) {
                for (int j = 0; j < dataList.size(); j++) {
                    //创建数据行,前面有两行,头标题行和列标题行
                    HSSFRow tempRow = sheet.createRow(j + 3);

                    // 设置各列单元格值
                    Object vo = dataList.get(j);
                    setSameRowDifferentColumnCellValueAndSpecialHandle(tempRow,
                            null,
                            createCommonCellStyle(workbook),
                            (position, value) -> {
                                if (position > 3) {
                                    Integer val = (Integer) value;
                                    value = CusStringUtils.retain2PositionDecimal(val);
                                }
                                return value;
                            },
                            j + 1
                    );
                }
            }
        }, response);
    }


    public static void mergeCell(HSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        //起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(callRangeAddress);
    }

    /**
     * 设置同行不同列的值
     *
     * @param tempRow          行对象
     * @param cellStyles       指定不他那个列的样式
     * @param defaultCellStyle 默认样式
     * @param function         特殊处理方法(当前列的索引，当前列的值) -> {return 处理后的值}
     * @param values           从0开始顺序每列的值
     */
    public static void setSameRowDifferentColumnCellValueAndSpecialHandle(HSSFRow tempRow,
                                                                          HSSFCellStyle[] cellStyles, HSSFCellStyle defaultCellStyle,
                                                                          BiFunction<Integer, Object, Object> function,
                                                                          Object... values) {
        if (values.length > 0) {
            AtomicInteger index = new AtomicInteger(0);
            Arrays.stream(values)
                    .forEach(value -> {
                        HSSFCell cell = tempRow.createCell(index.get());
                        if (function != null) {
                            // (当前列的索引，当前列的值) -> {return 处理后的值}
                            value = function.apply(index.get(), value);
                        }
                        if (value instanceof Date) {
                            cell.setCellValue((Date) value);
                        } else {
                            cell.setCellValue(String.valueOf(value));
                        }
                        if (cellStyles != null &&
                                cellStyles.length > index.get() &&
                                cellStyles[index.get()] != null) {
                            cell.setCellStyle(cellStyles[index.get()]);
                        } else {
                            cell.setCellStyle(defaultCellStyle);
                        }
                        index.getAndIncrement();
                    });
        }
    }

    public static void setSameRowDifferentColumnCellValueAndDefaultHandle(HSSFRow tempRow, HSSFCellStyle defaultCellStyle,
                                                                          Object... values) {
        setSameRowDifferentColumnCellValueAndSpecialHandle(tempRow, null, defaultCellStyle, null, values);
    }

    /**
     * 创建工作表
     */
    public static HSSFSheet createSheetAndDefaultHandle(HSSFWorkbook wb, String sheetName) {
        HSSFSheet sheet = wb.createSheet(sheetName);
        //设置默认列宽
        sheet.setDefaultColumnWidth(25);
        return sheet;
    }

    /**
     * 创建标题单元格样式
     */
    public static HSSFCellStyle createTitleCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = createCommonCellStyle(wb);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 17);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        return cellStyle;
    }

    /**
     * 创建头部单元格样式
     */
    public static HSSFCellStyle createHeaderCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = createCommonCellStyle(wb);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        return cellStyle;
    }

    /**
     * 创建通用单元格样式
     */
    public static HSSFCellStyle createCommonCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);

        // 设置边框样式
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        //设置自动换行
        cellStyle.setWrapText(true);
        // 居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return cellStyle;
    }
}

