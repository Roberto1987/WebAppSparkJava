package webapp.businessLogic;

import java.util.HashMap;

/**
 * Created by Roberto Testa on 6/15/17.
 *
 * Container which contain all pieces of the story,
 * recorded as Story objects
 *
 */
public class Plot {

    private static volatile Plot instance = null;

    private static String actualMainText = Constant.FIRST_TEXT;

    private HashMap<String,Story> plot;

    private Plot() {
        plot = new HashMap<>();
    }

    public static Plot getInstance() {
        if (instance == null) {
            synchronized(Plot.class) {
                if (instance == null) {
                    instance = new Plot();
                }
            }
        }
        return instance;
    }

    public static String getActualMainText() {
        return actualMainText;
    }

    public static void setActualMainText(String actualMainText) {
        Plot.actualMainText = actualMainText;
    }

    /**
     * Add a piece of the story in the plot
     * @param story
     */
    public void addStory(Story story){
        this.plot.put(story.getMainText(),story);
    }

    public Story getStory(String mainText){
        return this.plot.get(mainText);
    }

    public boolean isEmpty(){
        return this.plot.isEmpty();
    }

    public boolean alreadyExists(String key){
        return this.plot.get(key)!=null;
    }

}
