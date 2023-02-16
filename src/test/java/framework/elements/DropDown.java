package framework.elements;

import org.openqa.selenium.By;

public class DropDown extends BaseElement{
    public DropDown(By by) {
        super(by);
    }

    public DropDown(By by, String name) {
        super(by, name);
    }

    @Override
    protected String getElementType() {
        return "Dropdown";
    }
}
