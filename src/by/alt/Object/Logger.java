package by.alt.Object;
import org.apache.log4j.*;

public class Logger {
        static org.apache.log4j.Logger LOGGER  = LogManager.getLogger("TimeTableEditor");
        public void pushToScreenNlog(Exception ex, Class cl, String str) {
            LOGGER.error(str,ex);
            // System.err.println(str);
        }
        public void pushToScreenNlog(Exception ex, Class cl) {

            LOGGER.error("Error",ex);
        }
        public void pushToScreenNlog(String msg, Class cl) {
            org.apache.log4j.Logger LOGGER  = LogManager.getLogger("TimeTableEditor");
            msg = cl.getName()+": " + msg;
            LOGGER.info(msg);
        }

}
