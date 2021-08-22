package slf4j;
//log4j2

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author 许鸿志
 * @since 2021/8/4
 */
public class Log4j2Demo {
    //选择使用的日志等级策略
//    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    private static Logger logger = LogManager.getLogger("slf4j");
    public static void main(String[] args) {
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
    }
}
