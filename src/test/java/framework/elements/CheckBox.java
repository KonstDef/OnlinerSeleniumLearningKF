package framework.elements;

import org.openqa.selenium.By;

public class CheckBox extends BaseElement{
    public CheckBox(By by) {
        super(by);
    }

    public CheckBox(By by, String name) {
        super(by, name);
    }

    @Override
    protected String getElementType() {
        return "Checkbox";
    }
}
