package com.sonder.demo.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@Slf4j
public class ShellUtil {

    public static int execCommandVoid(String command) {
        int retCode = 0;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command}, null, null);
            retCode = process.waitFor();
            execVoid(process);
        } catch (Exception e) {
            retCode = -1;
        }
        return retCode;
    }

    private static boolean execVoid(Process process) throws Exception {
        if (process == null) {
            return false;
        } else {
//            InputStreamReader ir = new InputStreamReader(process.getInputStream());
//            LineNumberReader input = new LineNumberReader(ir);
//            String line;
//            String output = "";
//            int i = 1;
//            while ((line = input.readLine()) != null) {
//                System.out.println("第"+ i +"行:" + line);
//                output += line + "\n";
//                i++;
//            }
//            input.close();
//            ir.close();
//            if (output.length() > 0) {
//                System.out.println(output);
//            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            //echo输出多值，循环读取直到结束
            while ((line = stdInput.readLine()) != null) {
                stringBuffer.append(line).append("^");
            }
            String[] result = stringBuffer.toString().split("\\^");
            for (String s : result) {
                log.info("加密结果说明: {}", s);
            }
        }
        return true;
    }

    public static Map<String, String> execCommand(String command) {
        Map<String, String> result = Maps.newHashMap();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command}, null, null);
            process.waitFor();
            result = execOutput(process);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Map<String, String> execOutput(Process process) throws Exception {
        if (process == null) {
            return null;
        } else {
//            InputStreamReader ir = new InputStreamReader(process.getInputStream());
//            LineNumberReader input = new LineNumberReader(ir);
//            String line;
//            String output = "";
//            int i = 1;
//            while ((line = input.readLine()) != null) {
//                System.out.println("第"+ i +"行:" + line);
//                output += line + "\n";
//                i++;
//            }
//            input.close();
//            ir.close();
//            if (output.length() > 0) {
//                System.out.println(output);
//            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            //echo输出多值，循环读取直到结束
            while ((line = stdInput.readLine()) != null) {
                stringBuffer.append(line).append("^");
            }
            String[] result = stringBuffer.toString().split("\\^");
            for (String s : result) {
                log.info("加密结果说明: {}", s);
            }
            Map<String, String> map = Maps.newHashMap();
                String[] str2s = result[2].split("     ");
                for (String str2 : str2s) {
                    String[] str3 = str2.split(": ");
                    map.put(str3[0], str3[1]);
                }
            return map;
        }
    }
}
