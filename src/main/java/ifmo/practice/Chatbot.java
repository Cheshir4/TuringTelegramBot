package ifmo.practice;

import java.io.File;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

@Log4j2
public class Chatbot {
    static boolean TRACE_MODE = false;
    static String botName = "super";
    static Chat chatSession;

    public Chatbot(){
        String resourcesPath = getResourcesPath();
        log.info(resourcesPath);
        MagicBooleans.trace_mode = TRACE_MODE;
        val bot = new Bot(botName, resourcesPath);
        chatSession = new Chat(bot);
        bot.brain.nodeStats();
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        log.info(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }

    public String getAnswer(String textLine){
        if ((textLine == null) || (textLine.length() < 1))
            textLine = MagicStrings.null_input;

        String request = textLine;
        if (MagicBooleans.trace_mode)
           log.info("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
        String response = chatSession.multisentenceRespond(request);
        while (response.contains("&lt;"))
            response = response.replace("&lt;", "<");
        while (response.contains("&gt;"))
            response = response.replace("&gt;", ">");

        return response;
    }
}
