
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;

import structures.AssociativeArray;
import structures.KeyNotFoundException;

public class AACMappings {
    /* Object fields */
    AACCategory topLevel;
    String curCategory;
    AssociativeArray<String, AACCategory> AACPairs;
    /* methods */

    public AACMappings(String Filename) {
        this.topLevel = new AACCategory();// an array of images paired with strings that represent the different
                                          // categories
        this.AACPairs = new AssociativeArray<String, AACCategory>();// an array of strings describing their category,
                                                                    // which is an
        // AACCategory array
        this.curCategory = "";// a string that describes the current category. starting with ""
    }

    /*
     * this function adds an imageLocation and Text to the AACMappings. if we are at
     * the top category, it adds it to the top AACCategory array as a new category
     */
    void add(String imageLoc, String text) {

        if (this.getCurrentCategory().equals("") == true) {
            this.topLevel.addItem(imageLoc, text);
            this.AACPairs.set(text, new AACCategory());
            this.curCategory = text;

        } else {
            boolean found = false;
            for (int i = 0; i < AACPairs.length() && found != true; i++) {
                if (AACPairs.KeyAt(i).equals(this.getCurrentCategory())) {
                    found = true;
                    AACPairs.valueAt(i).addItem(imageLoc, text);
                }
            }
        }
    }

    /*
     * return the string paired with the current category. if the current category
     * is the
     * home screen top level AACCategory, return a string saying "top level"
     */
    String getCurrentCategory() {
        return this.curCategory;
    }

    /*
     * GetImageLocs is a method that returns an array of strings representing the
     * imageLocs of the
     * images in the current category. If in the current category, it returns an
     * array of the images
     * representing the categories.
     * 
     * @pre: none
     * 
     * @post: returns an array of strings representing the imageLocs, or throws an
     * error
     */
    String[] getImageLocs() {
        String[] returnArr = null;
        if (this.getCurrentCategory().equals("")) {// if we are at the top level, default category
            returnArr = this.topLevel.getImages();// call the getImages method on the AACCategory top level object
        } else {
            int index = 0;
            while (index < this.AACPairs.length()) {
                if (this.AACPairs.isNull(index) != true) {
                    if (this.AACPairs.KeyAt(index).equals(this.getCurrentCategory())) {
                        returnArr = this.AACPairs.valueAt(index).getImages();
                    }
                }
                index++;
            }
        }
        if (returnArr == null) {
            throw new Error("The currentCategory string does not have a corresponding AACCategory\n");
        }
        return returnArr;
    }

    /*
     * this method takes an image location. if the image location is an image
     * location associate with one of the top level categories, is sets the current
     * category to be the index associated with that String. else, it returns the
     * text
     * associated with the imageLoc which is within the current category. else, it
     * looks
     * into the current category for the text associate with the imageLoc
     * 
     * @pre: imageLoc is a valis image String that exist in whatever category we're
     * in
     * 
     * @post: returns a valid string associated with the imageLoc
     */
    String getText(String imageLoc) {

        if (this.getCurrentCategory().equals("")) {// if we are at the top category AACCategory
            System.out.printf("the corresponding text is %s\n", this.topLevel.getText(imageLoc));
            this.curCategory = this.topLevel.getText(imageLoc);
            System.out.printf("the current category is now %s\n", this.getCurrentCategory());
            return this.topLevel.getText(imageLoc);
        } else {// if we are in a subcategory
            int index = 0;
            System.out.printf("we are in a subcategory\n");
            while (index < this.AACPairs.length()) {
                if (this.AACPairs.isNull(index) != true) {
                    if (this.getCurrentCategory().equals(this.AACPairs.KeyAt(index))) {// if we are at the correct
                                                                                       // subcategory in the AACPairs
                                                                                       // array

                        return this.AACPairs.valueAt(index).getText(imageLoc);// get the text at the imageLoc within the
                                                                              // current category
                    }
                }
                index++;
            }
        }
        throw new Error("the string associated with the imageLoc couldn't be found\n");// if we reach this code, throw
                                                                                       // an error saying that the text
                                                                                       // couldn't be found
    }

    /*
     * Checks if the top-level, default AACCategory has a
     * imageLoc that represents a category of the AACMappings.
     * 
     * @pre: imageLoc is a valid String representing a image
     * 
     * @Post, returns true or false
     */
    Boolean isCategory(String imageLoc) {
        return this.topLevel.hasImage(imageLoc);
    }

    /*
     * this method resets the AACMappings current
     * category to ""; that is; the top category
     * 
     * @pre: none
     * 
     * @post: none
     */
    void reset() {
        this.curCategory = "";
    }

    /*
     * writeToFile is a method that takes a filename string,
     * and prints the contents of the AACMappings to the
     * file
     * 
     * @Pre: filename is a valid String
     * 
     * @Post: returns nothing but creates a valid file
     * with AACMappings's contents
     */
    void writeToFile(String filename) {
        try {
            PrintWriter pen = new PrintWriter(new File(filename));
            for (int i = 0; i < this.AACPairs.size(); i++) {
                pen.println(this.AACPairs.KeyAt(i));
                pen.flush();
                String[] images = this.AACPairs.valueAt(i).getImages();
                for (int x = 0; x < images.length; x++) {
                    if (images[x] != null) {
                        pen.printf("> %s\n", images[x]);
                        pen.flush();
                    }
                }
                pen.println();
            }
        } catch (Exception e) {
            throw new Error("writeToFile: could not open the new File\n");
        }
    }

    public static void main(String[] args) {
        AACMappings obj = new AACMappings("Brooo");
        obj.add("img/food/icons8-french-fries-96.png", "junkfood");
        obj.add("img/food/icons8-watermelon-96.png", "watermelon");
        obj.add("img/food/icons8-apple-96.png", "apple");
        obj.reset();
        obj.add("img/food/icons8-grape-96.png", "grapeAndRound");
        obj.add("img/food/icons8-melon-96.png", "cantaloupe");
        obj.add("img/sports/icons8-basketball-96.png", "BasketBall");
        System.out.println(obj.getText("img/food/icons8-melon-96.png"));
        System.out.println(obj.getText("img/sports/icons8-basketball-96.png"));
        System.out.println(Arrays.toString(obj.getImageLocs()).toCharArray());
        System.out.println();
        for (int i = 0; i < obj.AACPairs.length(); i++) {
            if (obj.AACPairs.isNull(i) != true) {
                System.out.printf("%s:  ", obj.AACPairs.KeyAt(i));
                for (int x = 0; obj.AACPairs.isNull(x) != true; x++) {
                    if (obj.AACPairs.valueAt(i).isNullIndex(x) != true) {
                        System.out.printf(" ");
                        System.out.print(obj.AACPairs.valueAt(i).AssocArray.KeyAt(x));
                        System.out.printf(", ");
                        System.out.print(obj.AACPairs.valueAt(i).AssocArray.valueAt(x));
                        System.out.printf(" | ");
                    }
                }
                System.out.println();
            }
        }
        // obj.writeToFile("toFile");
    }
}
