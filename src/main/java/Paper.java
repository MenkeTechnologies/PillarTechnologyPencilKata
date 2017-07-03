/**
 * Created by jacobmenke on 7/2/17.
 */
public class Paper {

    String contents = "";

    public void addContent(String contents) {
        this.contents += contents;
    }
    public void addCharacter(Character character){
        this.contents += character;
    }

    public void eraseAndSetContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
