package framework.elements;

import org.openqa.selenium.By;

public class Label extends BaseElement{
    public Label(By by) {
        super(by);
    }

    public Label(By by, String name) {
        super(by, name);
    }

    @Override
    protected String getElementType() {
        return "Label";
    }
}
