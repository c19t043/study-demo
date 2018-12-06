package cn.cjf.chat.utils;

import cn.cjf.chat.config.CommandEnum;

import java.util.Scanner;

import static cn.cjf.chat.config.MessagePattern.REQUEST_MESSAGE_PRE;

/**
 * @author CJF
 */
public class ConsoleUtil {

    /**
     * 控制台录入消息，并解析为录入消息
     *
     * @param hintInfo 控制台要提示的信息
     * @return [0]:命令，[1]：消息
     */
    public static String[] consoleInAndParse(String hintInfo) {
        System.out.println(hintInfo);
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        //判断输入内容，是消息，还是命令
        String command;
        String message = null;
        if (line.startsWith(REQUEST_MESSAGE_PRE)) {
            //命令
            command = line.substring(line.indexOf(REQUEST_MESSAGE_PRE) + 1);
        } else {
            //消息
            command = CommandEnum.CLIENT_SEND_MESSAGE.toString();
            message = line;
        }
        return new String[]{command, message};
    }
}
