/**
 * Created by jacobmenke on 7/2/17.
 */
public class Pencil {

    Paper paper;

    public void setPaperToWriteTo(Paper paper) {
        this.paper = paper;
    }

    public void write(String content){
        paper.addContent(content);

    }
}
