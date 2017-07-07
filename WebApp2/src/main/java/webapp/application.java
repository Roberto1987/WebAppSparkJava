package webapp;

import webapp.businessLogic.Constant;
import webapp.businessLogic.Plot;
import webapp.businessLogic.Story;
import webapp.controllers.IndexController;
import webapp.utils.Path;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

/**
 * Created by Roberto Testa on 6/14/17.

 * Starting class of the framework
 */
public class application {
    public static void main(String[] args) {

        staticFiles.location("");
        //Initialization of Plot
        PlotInit();

        get(Path.Web.INDEX, IndexController.mainController);
        get("*",IndexController.checker);
        post(Path.Web.INDEX, IndexController.postController);

    }

    /**
     * Initialize the plot with a story, having
     * as main text a fixed staring sentence
     */
    private static void PlotInit() {
        Plot plot = Plot.getInstance();
        Story beginningStory = new Story(Constant.FIRST_TEXT);
        if(plot.isEmpty()){
            plot.addStory(beginningStory);
        }


    }

}
