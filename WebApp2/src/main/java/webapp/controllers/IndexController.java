package webapp.controllers;

import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;
import webapp.businessLogic.Constant;
import webapp.businessLogic.Plot;
import webapp.businessLogic.Story;
import webapp.utils.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roberto Testa on 6/14/17.
 *
 * Class containing all of the controllers
 */
public class IndexController {

    /**
     * Entry point
     *
     * On /index address, return the main story board
     */
    public static Route mainController = (Request request, Response response) -> {

        VelocityEngine ve = velEngInit();

        Map<String, Object> model = new HashMap<>();

        Plot.setActualMainText(Constant.FIRST_TEXT);

        model.put("story", Plot.getInstance().getStory(Constant.FIRST_TEXT));
        model.put("templatePath",Path.InnerPaths.START);
        model.put("indexPage",Path.Web.INDEX);

        return new VelocityTemplateEngine(ve).render(new ModelAndView(model, Path.InnerPaths.START));
    };

    /**
     * Method activated by a clicked link
     *
     * Input: url relative to a secondary text,
     *        encapsulated in the request object
     */
    public static Route checker = (Request request, Response response) -> {

         //Parsing the secondary text, capturing it from the url sent.
        String reqUrl = request.raw().getRequestURL().toString();
        String[] urlCheck = reqUrl.split("/");
        String piece = urlCheck[urlCheck.length-1];

        //Trimming the string to deleting web %20, in case of white spaces in the sentence
        piece = piece.replace("%20"," ");

        //check if exist a story with this mainText
        String retrieveKey = Plot.getInstance().getActualMainText();
        Story story = Plot.getInstance().getStory(retrieveKey);

        //Checking if the url has transported an inserted text.
        //this boolean flag will be used later in this method
        boolean check = false;

        for(int i=0;i<story.getBranches().length;i++){
            if(story.getBranches()[i].equals(piece)){
                check = true;
            }
        }

        VelocityEngine ve = velEngInit();

        Map<String, Object> model = new HashMap<>();

        model.put("indexPage",Path.Web.INDEX);

        Story actualStory;
        /*
         Check if the request is relative to the insertion
         of a new text: if not, it re-address the browser
         to a 404 page.
         */
        if(check){
            //if a story with this mainText already exists,
            // it recalls the story object associated to it
            if(Plot.getInstance().alreadyExists(piece)) {
                actualStory = Plot.getInstance().getStory(piece);

                Plot.setActualMainText(piece); // 17 june modification
            }
            //If not, it creates a new story object and
            //start a new story, adding the story to the
            // plot and changing the actual mainText to
            // this story once.
            else{
                actualStory = new Story(piece);

                Plot.getInstance().addStory(actualStory);
                Plot.setActualMainText(actualStory.getMainText());
                }
            model.put("story",actualStory);

            return new VelocityTemplateEngine(ve).render(new ModelAndView(model, Path.InnerPaths.START));
        }
        else{
            return new VelocityTemplateEngine(ve).render(new ModelAndView(model, Path.InnerPaths.NOT_EXISTS));
        }

    };

    /**
     * Controller assigned to POST actions receiving
     * Assigning the the story the pieces written by the user
     */
    public static Route postController = (Request request, Response response) -> {

        //Retrieving the string inserted in the submit inputs
        String topStory    = request.queryParams("topStory");
        String leftStory   = request.queryParams("leftStory");
        String rightStory  = request.queryParams("rightStory");
        String bottomStory = request.queryParams("bottomStory");

        VelocityEngine ve = velEngInit();

        Map<String, Object> model = new HashMap<>();

        //If a string is not empty, it memorize it as a piece of story.
        if(topStory!=null){
            Plot.getInstance().getStory(Plot.getActualMainText()).getBranches()[0] = topStory;
        }
        if(leftStory!=null){
            Plot.getInstance().getStory(Plot.getActualMainText()).getBranches()[1] = leftStory;
        }
        if(rightStory!=null){
            Plot.getInstance().getStory(Plot.getActualMainText()).getBranches()[2] = rightStory;
        }
        if(bottomStory!=null){
            Plot.getInstance().getStory(Plot.getActualMainText()).getBranches()[3] = bottomStory;
        }

        model.put("story", Plot.getInstance().getStory(Plot.getActualMainText()));
        model.put("indexPage",Path.Web.INDEX);

        return new VelocityTemplateEngine(ve).render(new ModelAndView(model, Path.InnerPaths.START));
    };

    /**
     * Creating and setting a new velocity engine
     *
     * @return
     */
    public static VelocityEngine velEngInit(){
        VelocityEngine ve =    new VelocityEngine();
       // ve.init();
        ve.setProperty("runtime.references.strict", true);
        ve.setProperty("resource.loader", "class");
        ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return ve;
    }
}

//