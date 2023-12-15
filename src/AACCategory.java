
import java.util.*;

import structures.AssociativeArray;
import structures.KeyNotFoundException;

import static java.lang.reflect.Array.newInstance;

public class AACCategory {
    /*
     * /* Fields
     */
    AssociativeArray<String, String> AssocArray; // an associative array

    /* constructors */
    public AACCategory() {
        this.AssocArray = new AssociativeArray<String, String>();// the associative array
    }

    // /* Methods */

    public void addItem(String imageLoc, String text) {
        this.AssocArray.set(imageLoc, text);
        this.AssocArray.sizeIncrement();
    }

    public String[] getImages() {
        String[] Images = new String[this.AssocArray.size()];
        int imageIndex = 0;// index for the Images array
        for (int i = 0; i < this.AssocArray.length(); i++) {
            if (this.AssocArray.isNull(i) != true) {
                Images[imageIndex] = this.AssocArray.KeyAt(i);
                imageIndex++;
            }
        }
        return Images;
    }

    public String getText(String imageLoc) {
        try {
            return this.AssocArray.get(imageLoc);
        } catch (KeyNotFoundException k) {
            throw new Error("AACCategory: the text corresponding to the imageLoc does not exist\n");
        }
    }

    /* determines whether or not the AssociatedArray has the imageLoc key */
    public boolean hasImage(String imageLoc) {
        return this.AssocArray.hasKey(imageLoc);
    }

    /* returns the number of values in the Associated Array */
    public int length() {
        return this.AssocArray.length();
    }

    public Boolean isNullIndex(int index) {
        if (this.AssocArray.isNull(index) == true) {
            return true;
        } else {
            return false;
        }
    }
}
