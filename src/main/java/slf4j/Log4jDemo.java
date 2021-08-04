package slf4j;


import org.apache.log4j.Logger;

/**
 * log4j
 * 用于展示 日志文件各级别的输出
 *
 * @author 许鸿志
 * @since 2021/8/4
 */
public class Log4jDemo {
    private static Logger logger = Logger.getLogger(Log4jDemo.class);

    public static void main(String[] args) {
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
    }
}
