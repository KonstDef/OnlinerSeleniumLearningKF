package framework.elements;

import org.openqa.selenium.By;

public class TextBox extends BaseElement{
    public TextBox(By by) {
        super(by);
    }

    public TextBox(By by, String name) {
        super(by, name);
    }

    @Override
    protected String getElementType() {
        return "TextBox";
    }
}
