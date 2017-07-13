import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by jacobmenke on 7/2/17.
 */
public class Paper {
    String contents = "";

    public void addContent(String contents) {
        this.contents += contents;
    }

    public void addCharacter(Character character) {
        this.contents += character;
    }

    public void eraseAndSetContents(String contents) {
        this.contents = contents;
    }

    public String read() {
        return contents;
    }

    public String readLastSentence(){
        Integer indexOfLastPeriod = returnNthOccurenceIndex('.',-2);
        return contents.substring(indexOfLastPeriod+1).trim();
    }

    public static void main(String[] args) {
        String str = "Hello World.  Please take me home to the store.";
        Paper paper = new Paper();
        paper.eraseAndSetContents(str);
        System.out.println(paper.readLastSentence());
    }

    private Integer returnNthOccurenceIndex(Character character, Integer nthOccurence){
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < contents.toCharArray().length; i++) {
            if (contents.charAt(i) == character){
                indices.add(i);
            }
        }
        //allow negative indexing
        if (nthOccurence < 0){
            return indices.get(indices.size()+nthOccurence);
        }
        return indices.get(nthOccurence);
    }

    public String findIndexOfLastSentenceContaining(String searchTerm){
        Pattern.compile("" + searchTerm + "").matcher(contents);
        return "";
    }
}
