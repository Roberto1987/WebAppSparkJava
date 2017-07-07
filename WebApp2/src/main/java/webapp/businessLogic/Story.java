package webapp.businessLogic;

/**
 * Created by Roberto Testa on 6/15/17.
 *
 * Objects which encapsulate a piece of story with the
 * four related branches
 */
public class Story {

    private String mainText;
    private String[] branches;


    /**
     * Initialization of a single piece of story: mainText is fixed at the creation,
     * the other 4 pieces are left blank
     * @param mainText
     */
    public Story(String mainText) {
        this.mainText = mainText;
        this.branches = new String[4];
        for(int i=0;i<branches.length;i++){
              branches[i] = "";
        }
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String[] getBranches() {
        return branches;
    }

    public void setBranches(String[] branches) {
        this.branches = branches;
    }

}
